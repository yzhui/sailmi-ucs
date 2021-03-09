package com.sailmi.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sailmi.core.mp.base.BaseServiceImpl;
import com.sailmi.core.message.constant.BaseResultEnum;
import com.sailmi.core.message.dao.constant.RedisKeys;
import com.sailmi.core.message.dao.constant.SendStatus;
import com.sailmi.core.message.dao.constant.ValidateStatus;
import com.sailmi.core.message.dao.entity.MessageTask;
import com.sailmi.core.message.dao.entity.MessageLog;
import com.sailmi.core.message.dao.entity.MessageSetting;
import com.sailmi.core.message.dao.entity.MessageTemplate;
import com.sailmi.core.message.dao.mapper.MessageTaskMapper;
import com.sailmi.core.message.dao.mapper.MessageLogMapper;
import com.sailmi.core.message.dao.mapper.MessageSettingMapper;
import com.sailmi.core.message.dao.mapper.MessageTemplateMapper;
import com.sailmi.core.message.exception.BaseException;
import com.sailmi.core.message.exception.ChannelException;
import com.sailmi.core.message.health.SMSHealthIndicator;
import com.sailmi.core.message.model.dto.*;
import com.sailmi.core.message.model.vo.MessageLogVO;
import com.sailmi.core.message.service.ChannelSMSServices;
import com.sailmi.core.message.service.IChannelService;
import com.sailmi.core.message.service.IMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 发送消息主类
 * 依据MessageType，通过不同的Service实现，完成消息的发送。
 */
@Service
public class MessageServiceImpl  extends BaseServiceImpl<MessageLogMapper, MessageLog> implements IMessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	@Autowired
	private MessageSettingMapper messageSettingMapper;

	@Autowired
	private MessageTemplateMapper templateMapper;

	@Autowired
	private MessageLogMapper messageMapper;

	@Autowired
	private MessageTaskMapper batchMessageMapper;

	@Autowired
	private ChannelSMSServices channelSMSServices;

	@Autowired
	private SMSHealthIndicator smsHealthIndicator;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private static final int BATCH_MESSAGE_COUNT = 500;

	/**
	 *  消息发送方法：
	 *  每一个租户，拥有一套消息配置
	 *  系统需要发送消息的时候，需要指明发送何种类型的消息，发送类型可以是短信、邮件
	 *  及APP推送、桌面推送、系统站内消息。
	 *  具体站内消息，用户可以实现自己的模块
	 *
	 *  每一类消息通过一个Ｓｅｒｖｉｃｅ实现
	 *  每一个消息Ｓｅｒｖｉｃｅ，可以考虑多个ｐｒｏｖｉｄｅｒ提供。
	 */

	@Override
	public SendMessageResult send(MessageDTO messageDTO) {
		MessageTemplate template = templateMapper.selectById(Short.valueOf(messageDTO.getTemplateId()));
		if (template == null) {
			throw new BaseException(BaseResultEnum.TEMPLATE_NOT_EXIST);
		}

		//检查消息设置

		MessageSetting messageSetting = messageSettingMapper.selectById("");
		if (messageSetting == null) {
			throw new BaseException(BaseResultEnum.APP_NOT_EXIST);
		}
		Map<String, Object> params = messageDTO.getParams();
		String validateCodeKey = template.getValidateCodeKey();
		Integer validateCodeExpire = template.getValidateCodeExpire();
		String validateCode = null;
		if (!StringUtils.isEmpty(validateCodeKey)) {
			if (validateCodeExpire == null || validateCodeExpire < 0) {
				throw new BaseException(BaseResultEnum.VALIDATE_CODE_EXPIRE_ILLEGAL);
			}
			logger.debug("需要发送验证码");
			validateCode = String.valueOf(params.get(validateCodeKey));
			if (StringUtils.isEmpty(validateCode)) {
				logger.info("参数中包含验证码{},系统不生成新的验证码", validateCode);
			} else {
				validateCode = getValidateCode(messageSetting.getValidateCodeLength());
				logger.debug("系统生成的验证码为{}", validateCode);
				Map<String, Object> paramsWithValidateCode = new HashMap<>(params);
				paramsWithValidateCode.put(validateCodeKey, validateCode);
				messageDTO.setParams(paramsWithValidateCode);
			}
		}

		logger.info("开始发送短信:{}", template);
		Iterator<IChannelService> iterator = channelSMSServices.iterator();
		SendMessageResult result;
		byte retry = 0;
		String channel;
		do {
			IChannelService channelService =iterator.next();
			channel = channelService.getChannel();
			try {
				result = channelService.send(messageSetting, template, messageDTO);
				break;
			} catch (ChannelException e) {
				logger.error("渠道短信发送异常", e);
				result = new SendMessageResult(e.getMessage());
				if (iterator.hasNext()) {
					retry ++;
				}
			}
		} while (iterator.hasNext());

		try {
			MessageLog message = new MessageLog();
			message.setMobile(messageDTO.getMobile());
			message.setParams(new ObjectMapper().writeValueAsString(params));
			message.setTemplateId(template.getId());
			if (result.isSuccess()) {
				message.setSendStatus(Integer.valueOf(SendStatus.SENDING));
			} else {
				message.setSendStatus(Integer.valueOf(SendStatus.FAILURE));
				message.setFailCode(result.getFailCode());
			}
			if (retry > 0) {
				message.setRetry(Integer.valueOf(retry));
			}
			message.setBizId(result.getBizId());
			message.setChannel(channel);
			message.setValidateCode(validateCode);
			message.setValidateStatus(Integer.valueOf(ValidateStatus.NO));
			int rows = messageMapper.insert(message);
			logger.info("{}插入结果:{},生成的自增id为:{}", message, rows, message.getId());
			if (!StringUtils.isEmpty(validateCodeKey)) {
				String redisKey = RedisKeys.VALIDATE_CODE_MESSAGE.format(messageDTO.getMobile(), template.getId());
				redisTemplate.opsForValue().set(redisKey, message, validateCodeExpire, TimeUnit.SECONDS);
			}
			if (!result.isSuccess()) {
				smsHealthIndicator.addSample(message);
			}
		} catch (Exception e) {
			logger.error("插入Message数据异常" + messageDTO, e);
		}
		return result;
	}

	@Override
	public String getValidateCode(byte length) {
		int base = (int) Math.pow(10, length - 1);
		int result = new Random().nextInt(9 * base) + base;
		return String.valueOf(result);
	}

	@Override
	public QuerySendResult queryAndUpdateSendStatus(MessageLog message, IChannelService channelSMSService) {
		MessageTemplate template = templateMapper.selectById(message.getTemplateId());
		MessageSetting messageSetting = messageSettingMapper.selectById("");
		QuerySendResult querySendResult = channelSMSService.querySendStatus(messageSetting, message);
		if (querySendResult.isSuccess() && SendStatus.SENDING != querySendResult.getSendStatus()) {
			updateMessageSendStatus(message, querySendResult);
		}
		return querySendResult;
	}

	@Override
	public boolean check(ValidateCodeDTO validateDTO) {
		MessageTemplate template = templateMapper.selectById(Short.valueOf(validateDTO.getTemplateId()));
		if (template == null) {
			throw new BaseException(BaseResultEnum.TEMPLATE_NOT_EXIST);
		} else if (StringUtils.isEmpty(template.getValidateCodeKey())) {
			throw new BaseException(BaseResultEnum.NOT_VALIDATE_CODE_TEMPLATE);
		}
		MessageSetting messageSetting = messageSettingMapper.selectById("");
		if (messageSetting == null) {
			throw new BaseException(BaseResultEnum.APP_NOT_EXIST);
		}
		String messageRedisKey = RedisKeys.VALIDATE_CODE_MESSAGE.format(validateDTO.getMobile(), template.getId());
		String retryRedisKey = RedisKeys.VALIDATE_RETRY.format(validateDTO.getMobile(), template.getId());
		MessageLog message = (MessageLog) redisTemplate.opsForValue().get(messageRedisKey);
		if (message == null) {
			logger.debug("验证码已过期");
			return false;
		}
		String validateCode = message.getValidateCode();
		if (!validateCode.equals(validateDTO.getValidateCode())) {
			logger.debug("验证码错误");
			long retry = redisTemplate.opsForValue().increment(retryRedisKey, 1);
			redisTemplate.expire(retryRedisKey, 1, TimeUnit.MINUTES);
			if (retry > 3) {
				logger.info("验证码重试次数过多，强制失效");
				redisTemplate.delete(retryRedisKey);
				redisTemplate.delete(messageRedisKey);
			}
			return false;
		}
		try {
			int result = updateMessageValidateStatus(message);
			if (result > 0) {
				redisTemplate.delete(retryRedisKey);
				redisTemplate.delete(messageRedisKey);
				return true;
			} else {
				logger.warn("并发验证短信验证码");
				return false;
			}
		} catch (Exception e) {
			logger.error("更新Message数据异常", e);
			return false;
		}
	}

	@Override
	public int updateMessageValidateStatus(MessageLog message) {
		MessageLog updateMessage = new MessageLog();
		updateMessage.setValidateStatus(Integer.valueOf(ValidateStatus.YES));
		UpdateWrapper<MessageLog> wrapper = new UpdateWrapper();
		//Need to check logic  --yzh
		wrapper.lambda().set(MessageLog::getValidateStatus,message.getValidateStatus())
						.eq(MessageLog::getId,message.getId());
		int result = messageMapper.update(message,wrapper);
		logger.info("{}更新验证码状态结果{}", message, result);
		return result;
	}

	@Override
	public MessageLog queryLatestMessage(String mobile, MessageTemplate template) {
		Instant expire = Instant.now().minusSeconds(template.getValidateCodeExpire());
		//change to mybatis ++
		QueryWrapper<MessageLog> queryWrapper = new QueryWrapper<>();

		/*
		Example example = new Example.Builder(Message.class)
				.where(WeekendSqls.<Message>custom()
						.andEqualTo(Message::getMobile, mobile)
						.andEqualTo(Message::getTemplateId, template.getId())
						.andGreaterThan(Message::getSendStatus, Columns.SendStatus.FAILURE)
						.andGreaterThan(Message::getCreateDate, Date.from(expire)))
				.orderByDesc("id")//此处等同于create_date,但是具有更好的性能
				.build();
		PageHelper.startPage(1, 1, false);

		 */
		return messageMapper.selectOne(queryWrapper);
	}

	@Override
	public MessageLog queryMessage(String mobile, String bizId) {
		QueryWrapper<MessageLog> queryWrapper = new QueryWrapper<MessageLog>();
		MessageLog message = new MessageLog();
		message.setBizId(bizId);
		message.setMobile(mobile);
		queryWrapper.setEntity(message);
		return messageMapper.selectOne(queryWrapper);
	}

	@Override
	public int updateMessageSendStatus(MessageLog message, QuerySendResult querySendResult) {
		MessageLog updateMessage = new MessageLog();
		updateMessage.setId(message.getId());
		updateMessage.setSendStatus(Integer.valueOf(querySendResult.getSendStatus()));
		if (SendStatus.FAILURE == querySendResult.getSendStatus()) {
			updateMessage.setFailCode(querySendResult.getFailCode());
		} else if (SendStatus.SUCCESS == querySendResult.getSendStatus()) {
			updateMessage.setReceiveDate(querySendResult.getReceiveDate());
		}
		int result = messageMapper.updateById(updateMessage);
		logger.info("{}更新发送状态结果{}", updateMessage, result);
		smsHealthIndicator.addSample(updateMessage);
		return result;
	}

	@Override
	public List<MessageLog> querySendingMessages(Date fromDate, String channel) {
		QueryWrapper<MessageLog> queryWrapper = new QueryWrapper<>();
		/*
		Example example = new Example.Builder(Message.class)
				.where(WeekendSqls.<Message>custom()
						.andEqualTo(Message::getSendStatus, Columns.SendStatus.SENDING)
						.andEqualTo(Message::getChannel, channel)
						.andGreaterThan(Message::getCreateDate, fromDate))
				.orderByDesc("id")
				.build();
		PageHelper.startPage(1, 100, false);
		 */
		return messageMapper.selectList(queryWrapper);
	}

	@Override
	public void batchSend(MessageTaskDTO batchMessageDTO) {
		MessageSetting messageSetting = messageSettingMapper.selectById(batchMessageDTO.getAppId());
		if (messageSetting == null) {
			throw new BaseException(BaseResultEnum.APP_NOT_EXIST);
		}
		Set<String> mobile = batchMessageDTO.getMobile();
		logger.info("开始批量发送短信:{}", batchMessageDTO.getContent());
		List<String> mobileList = new ArrayList<>(mobile);
		int from = 0, to = 0;
		while (to < mobileList.size()) {
			to = to + BATCH_MESSAGE_COUNT > mobileList.size() ? mobileList.size() : to + BATCH_MESSAGE_COUNT;
			String[] mobileArray = mobileList.subList(from, to).toArray(new String[0]);
			from = to;
			SendMessageResult sendMessageResult;
			IChannelService channelSMSService = channelSMSServices.getBatchSendable();
			try {
				sendMessageResult = channelSMSService.batchSend(messageSetting, mobileArray, batchMessageDTO.getContent());
			} catch (ChannelException e) {
				sendMessageResult = new SendMessageResult(e.getMessage());
			}
			try {
				MessageTask batchMessage = new MessageTask();
				batchMessage.setMessageSettingId(messageSetting.getId());
				batchMessage.setContent(batchMessageDTO.getContent());
				batchMessage.setTotal((short) mobileArray.length);
				if (sendMessageResult.isSuccess()) {
					batchMessage.setSendStatus(SendStatus.SENDING);
				} else {
					batchMessage.setSendStatus(SendStatus.FAILURE);
					batchMessage.setFailCode(sendMessageResult.getFailCode());
					batchMessage.setFailure(batchMessage.getTotal());
				}
				batchMessage.setBizId(sendMessageResult.getBizId());
				int rows = batchMessageMapper.insert(batchMessage);
				logger.info("{}插入结果:{},生成的自增id为:{}", batchMessage, rows, batchMessage.getId());
				String redisKey;
				if (sendMessageResult.isSuccess()) {
					redisKey = RedisKeys.BATCH_MESSAGE_SENDING.format(batchMessage.getId());
				} else {
					redisKey = RedisKeys.BATCH_MESSAGE_FAILURE.format(batchMessage.getId());
				}
				long add = stringRedisTemplate.opsForSet().add(redisKey, mobileArray);
				logger.info("{}集合新增:{}", redisKey, add);
			} catch (Exception e) {
				logger.error("插入BatchMessage数据异常" + batchMessageDTO, e);
			}
		}
	}

	@Override
	public MessageTask queryBatchMessage(String bizId) {
		QueryWrapper<MessageTask> queryWrapper = new QueryWrapper<>();
		MessageTask batchMessage = new MessageTask();
		batchMessage.setBizId(bizId);
		queryWrapper.setEntity(batchMessage);
		return batchMessageMapper.selectOne(queryWrapper);
	}

	@Override
	public int updateBatchMessageCount(int id, short sending, short success, short failure) {
		MessageTask batchMessage = new MessageTask();
		batchMessage.setId(id);
		batchMessage.setSuccess(success);
		batchMessage.setFailure(failure);
		if (sending == 0) {
			if (failure == 0) {
				batchMessage.setSendStatus(SendStatus.SUCCESS);
			} else {
				batchMessage.setSendStatus(SendStatus.COMPLETE);
			}
		}
		UpdateWrapper updateWrapper = new UpdateWrapper(batchMessage);
		int result = batchMessageMapper.update(batchMessage,updateWrapper);
		logger.info("{}更新批量短信发送数量结果{}", batchMessage, result);
		return result;
	}

	@Override
	public IPage<MessageLogVO> selectMessagePage(IPage<MessageLogVO> page, MessageLogVO message) {
		return page.setRecords(baseMapper.selectMessagePage(page, message));
	}
}

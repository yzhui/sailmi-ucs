package com.sailmi.message.service.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.core.mp.base.BaseServiceImpl;
import com.sailmi.message.dao.mapper.MessageSettingMapper;
import com.sailmi.message.dao.model.MessageSetting;
import com.sailmi.message.exception.ChannelException;
import com.sailmi.message.model.vo.MessageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sailmi.message.constant.BaseResultEnum;
import com.sailmi.message.dao.constant.Columns;
import com.sailmi.message.dao.constant.RedisKeys;
import com.sailmi.message.dao.mapper.BatchMessageMapper;
import com.sailmi.message.dao.mapper.MessageMapper;
import com.sailmi.message.dao.mapper.TemplateMapper;
import com.sailmi.message.dao.model.BatchMessage;
import com.sailmi.message.dao.model.Message;
import com.sailmi.message.dao.model.Template;
import com.sailmi.message.health.SMSHealthIndicator;
import com.sailmi.message.model.dto.BatchMessageDTO;
import com.sailmi.message.model.dto.MessageDTO;
import com.sailmi.message.model.dto.QuerySendResult;
import com.sailmi.message.model.dto.SendMessageResult;
import com.sailmi.message.model.dto.ValidateCodeDTO;
import com.sailmi.message.exception.BaseException;
import com.sailmi.message.service.IChannelSMSService;
import com.sailmi.message.service.IMessageService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MessageServiceImpl  extends BaseServiceImpl<MessageMapper, Message> implements IMessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	@Autowired
	private MessageSettingMapper messageSettingMapper;

	@Autowired
	private TemplateMapper templateMapper;

	@Autowired
	private MessageMapper messageMapper;

	@Autowired
	private BatchMessageMapper batchMessageMapper;

	@Autowired
	private ChannelSMSServices channelSMSServices;

	@Autowired
	private SMSHealthIndicator smsHealthIndicator;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private static final int BATCH_MESSAGE_COUNT = 500;

	@Override
	public SendMessageResult send(MessageDTO messageDTO) {
		Template template = templateMapper.selectById(Short.valueOf(messageDTO.getTemplateId()));
		if (template == null) {
			throw new BaseException(BaseResultEnum.TEMPLATE_NOT_EXIST);
		}

		//check app
		MessageSetting messageSetting = messageSettingMapper.selectById(template.getMessageSettingId());
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
		Iterator<IChannelSMSService> iterator = channelSMSServices.iterator();
		SendMessageResult result;
		byte retry = 0;
		String channel;
		do {
			IChannelSMSService channelSMSService =iterator.next();
			channel = channelSMSService.getChannel();
			try {
				result = channelSMSService.send(messageSetting, template, messageDTO);
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
			Message message = new Message();
			message.setMobile(messageDTO.getMobile());
			message.setParams(new ObjectMapper().writeValueAsString(params));
			message.setTemplateId(template.getId());
			if (result.isSuccess()) {
				message.setSendStatus(Integer.valueOf(Columns.SendStatus.SENDING));
			} else {
				message.setSendStatus(Integer.valueOf(Columns.SendStatus.FAILURE));
				message.setFailCode(result.getFailCode());
			}
			if (retry > 0) {
				message.setRetry(Integer.valueOf(retry));
			}
			message.setBizId(result.getBizId());
			message.setChannel(channel);
			message.setValidateCode(validateCode);
			message.setValidateStatus(Integer.valueOf(Columns.ValidateStatus.NO));
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
	public QuerySendResult queryAndUpdateSendStatus(Message message, IChannelSMSService channelSMSService) {
		Template template = templateMapper.selectById(message.getTemplateId());
		MessageSetting messageSetting = messageSettingMapper.selectById(template.getMessageSettingId());
		QuerySendResult querySendResult = channelSMSService.querySendStatus(messageSetting, message);
		if (querySendResult.isSuccess() && Columns.SendStatus.SENDING != querySendResult.getSendStatus()) {
			updateMessageSendStatus(message, querySendResult);
		}
		return querySendResult;
	}

	@Override
	public boolean check(ValidateCodeDTO validateDTO) {
		Template template = templateMapper.selectById(Short.valueOf(validateDTO.getTemplateId()));
		if (template == null) {
			throw new BaseException(BaseResultEnum.TEMPLATE_NOT_EXIST);
		} else if (StringUtils.isEmpty(template.getValidateCodeKey())) {
			throw new BaseException(BaseResultEnum.NOT_VALIDATE_CODE_TEMPLATE);
		}
		MessageSetting messageSetting = messageSettingMapper.selectById(template.getMessageSettingId());
		if (messageSetting == null) {
			throw new BaseException(BaseResultEnum.APP_NOT_EXIST);
		}
		String messageRedisKey = RedisKeys.VALIDATE_CODE_MESSAGE.format(validateDTO.getMobile(), template.getId());
		String retryRedisKey = RedisKeys.VALIDATE_RETRY.format(validateDTO.getMobile(), template.getId());
		Message message = (Message) redisTemplate.opsForValue().get(messageRedisKey);
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
	public int updateMessageValidateStatus(Message message) {
		Message updateMessage = new Message();
		updateMessage.setValidateStatus(Integer.valueOf(Columns.ValidateStatus.YES));
		UpdateWrapper<Message> wrapper = new UpdateWrapper();
		//Need to check logic  --yzh
		wrapper.lambda().set(Message::getValidateStatus,message.getValidateStatus())
						.eq(Message::getId,message.getId());
		int result = messageMapper.update(message,wrapper);
		logger.info("{}更新验证码状态结果{}", message, result);
		return result;
	}

	@Override
	public Message queryLatestMessage(String mobile, Template template) {
		Instant expire = Instant.now().minusSeconds(template.getValidateCodeExpire());
		//change to mybatis ++
		QueryWrapper<Message> queryWrapper = new QueryWrapper<>();

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
	public Message queryMessage(String mobile, String bizId) {
		QueryWrapper<Message> queryWrapper = new QueryWrapper<Message>();
		Message message = new Message();
		message.setBizId(bizId);
		message.setMobile(mobile);
		queryWrapper.setEntity(message);
		return messageMapper.selectOne(queryWrapper);
	}

	@Override
	public int updateMessageSendStatus(Message message, QuerySendResult querySendResult) {
		Message updateMessage = new Message();
		updateMessage.setId(message.getId());
		updateMessage.setSendStatus(Integer.valueOf(querySendResult.getSendStatus()));
		if (Columns.SendStatus.FAILURE == querySendResult.getSendStatus()) {
			updateMessage.setFailCode(querySendResult.getFailCode());
		} else if (Columns.SendStatus.SUCCESS == querySendResult.getSendStatus()) {
			updateMessage.setReceiveDate(querySendResult.getReceiveDate());
		}
		int result = messageMapper.updateById(updateMessage);
		logger.info("{}更新发送状态结果{}", updateMessage, result);
		smsHealthIndicator.addSample(updateMessage);
		return result;
	}

	@Override
	public List<Message> querySendingMessages(Date fromDate, String channel) {
		QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
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
	public void batchSend(BatchMessageDTO batchMessageDTO) {
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
			IChannelSMSService channelSMSService = channelSMSServices.getBatchSendable();
			try {
				sendMessageResult = channelSMSService.batchSend(messageSetting, mobileArray, batchMessageDTO.getContent());
			} catch (ChannelException e) {
				sendMessageResult = new SendMessageResult(e.getMessage());
			}
			try {
				BatchMessage batchMessage = new BatchMessage();
				batchMessage.setMessageSettingId(messageSetting.getId());
				batchMessage.setContent(batchMessageDTO.getContent());
				batchMessage.setTotal((short) mobileArray.length);
				if (sendMessageResult.isSuccess()) {
					batchMessage.setSendStatus(Columns.SendStatus.SENDING);
				} else {
					batchMessage.setSendStatus(Columns.SendStatus.FAILURE);
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
	public BatchMessage queryBatchMessage(String bizId) {
		QueryWrapper<BatchMessage> queryWrapper = new QueryWrapper<>();
		BatchMessage batchMessage = new BatchMessage();
		batchMessage.setBizId(bizId);
		queryWrapper.setEntity(batchMessage);
		return batchMessageMapper.selectOne(queryWrapper);
	}

	@Override
	public int updateBatchMessageCount(int id, short sending, short success, short failure) {
		BatchMessage batchMessage = new BatchMessage();
		batchMessage.setId(id);
		batchMessage.setSuccess(success);
		batchMessage.setFailure(failure);
		if (sending == 0) {
			if (failure == 0) {
				batchMessage.setSendStatus(Columns.SendStatus.SUCCESS);
			} else {
				batchMessage.setSendStatus(Columns.SendStatus.COMPLETE);
			}
		}
		UpdateWrapper updateWrapper = new UpdateWrapper(batchMessage);
		int result = batchMessageMapper.update(batchMessage,updateWrapper);
		logger.info("{}更新批量短信发送数量结果{}", batchMessage, result);
		return result;
	}

	@Override
	public IPage<MessageVO> selectMessagePage(IPage<MessageVO> page, MessageVO message) {
		return page.setRecords(baseMapper.selectMessagePage(page, message));
	}
}

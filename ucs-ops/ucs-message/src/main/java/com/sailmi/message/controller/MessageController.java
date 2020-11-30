package com.sailmi.message.controller;

import java.util.Collections;

import javax.validation.Valid;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.core.mp.support.Condition;
import com.sailmi.core.mp.support.Query;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.message.core.dao.entity.Message;
import com.sailmi.message.core.model.dto.BatchMessageDTO;
import com.sailmi.message.core.model.dto.MessageDTO;
import com.sailmi.message.core.model.dto.SendMessageResult;
import com.sailmi.message.core.model.dto.ValidateCodeDTO;
import com.sailmi.message.core.model.vo.BaseResult;
import com.sailmi.message.core.model.vo.MessageVO;
import com.sailmi.message.core.service.IMessageService;
import com.sailmi.message.core.wrapper.MessageWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.sailmi.message.constant.BaseResultEnum;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.sailmi.core.tool.api.R;



@RestController
@AllArgsConstructor
@RequestMapping("/message")
@Api(value = "", tags = "接口")
public class MessageController extends BaseController {

	@Autowired
	private IMessageService messageService;

	/**
	 * 发送短信
	 *
	 * @param message
	 * @return
	 */
	@PostMapping("/send")
	public BaseResult send(@RequestBody @Valid MessageDTO message, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new BaseResult(BaseResultEnum.ILLEGAL_ARGUMENT, bindingResult.getFieldError().getDefaultMessage());
		}
		if (message.getParams() == null) {
			message.setParams(Collections.emptyMap());
		}
		SendMessageResult result = messageService.send(message);
		if (result.isSuccess()) {
			return new BaseResult(BaseResultEnum.SUCCESS, "发送成功");
		} else {
			return new BaseResult(BaseResultEnum.FAIL, result.getFailCode());
		}
	}

	/**
	 * 验证验证码
	 *
	 * @param validateCode
	 * @return
	 */
	@PostMapping("/check")
	public BaseResult check(@RequestBody @Valid ValidateCodeDTO validateCode, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new BaseResult(BaseResultEnum.ILLEGAL_ARGUMENT, bindingResult.getFieldError().getDefaultMessage());
		}
		boolean result = messageService.check(validateCode);
		if (result) {
			return new BaseResult(BaseResultEnum.SUCCESS, "验证通过");
		} else {
			return new BaseResult(BaseResultEnum.FAIL, "验证失败");
		}
	}

	/**
	 * 批量发送短信
	 *
	 * @param batchMessage
	 * @return
	 */
	@PostMapping("/batchSend")
	public BaseResult batchSend(@RequestBody @Valid BatchMessageDTO batchMessage, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new BaseResult(BaseResultEnum.ILLEGAL_ARGUMENT, bindingResult.getFieldError().getDefaultMessage());
		}
		messageService.batchSend(batchMessage);
		return new BaseResult(BaseResultEnum.SUCCESS, "发送成功");
	}

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入message")
	public R<MessageVO> detail(Message message) {
		Message detail = messageService.getOne(Condition.getQueryWrapper(message));
		return R.data(MessageWrapper.build().entityVO(detail));
	}

	/**
	 * 分页
	 */
	@GetMapping("/list")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入message")
	public R<IPage<MessageVO>> list(Message message, Query query) {
		IPage<Message> pages = messageService.page(Condition.getPage(query), Condition.getQueryWrapper(message));
		return R.data(MessageWrapper.build().pageVO(pages));
	}

	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入message")
	public R<IPage<MessageVO>> page(MessageVO message, Query query) {
		IPage<MessageVO> pages = messageService.selectMessagePage(Condition.getPage(query), message);
		return R.data(pages);
	}

	/**
	 * 新增
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入message")
	public R save(@Valid @RequestBody Message message) {
		return R.status(messageService.save(message));
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入message")
	public R update(@Valid @RequestBody Message message) {
		return R.status(messageService.updateById(message));
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入message")
	public R submit(@Valid @RequestBody Message message) {
		return R.status(messageService.saveOrUpdate(message));
	}


	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(messageService.deleteLogic(Func.toLongList(ids)));
	}
}

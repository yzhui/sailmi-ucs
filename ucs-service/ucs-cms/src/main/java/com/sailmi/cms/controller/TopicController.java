/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sailmi.cms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.sailmi.core.mp.support.Condition;
import com.sailmi.core.mp.support.Query;
import com.sailmi.core.tool.api.R;
import com.sailmi.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.cms.entity.Topic;
import com.sailmi.cms.vo.TopicVO;
import com.sailmi.cms.wrapper.TopicWrapper;
import com.sailmi.cms.service.ITopicService;
import com.sailmi.core.boot.ctrl.AppController;
import java.util.List;

/**
 * 专题表 控制器
 *
 * @author sailmi
 * @since 2020-12-16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/topic")
@Api(value = "专题表", tags = "专题表接口")
public class TopicController extends AppController {

	private ITopicService topicService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入topic")
	public R<TopicVO> detail(Topic topic) {
		Topic detail = topicService.getOne(Condition.getQueryWrapper(topic));
		return R.data(TopicWrapper.build().entityVO(detail));
	}

	/**
	* 分页 专题表
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入topic")
	public R<IPage<TopicVO>> list(Topic topic, Query query) {
		IPage<Topic> pages = topicService.page(Condition.getPage(query), Condition.getQueryWrapper(topic));
		return R.data(TopicWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 专题表
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入topic")
	public R<IPage<TopicVO>> page(TopicVO topic, Query query) {
		IPage<TopicVO> pages = topicService.selectTopicPage(Condition.getPage(query), topic);
		return R.data(pages);
	}

	/**
	* 新增 专题表
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入topic")
	public R save(@Valid @RequestBody Topic topic) {
		return R.status(topicService.save(topic));
	}

	/**
	* 修改 专题表
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入topic")
	public R update(@Valid @RequestBody Topic topic) {
		return R.status(topicService.updateById(topic));
	}

	/**
	* 新增或修改 专题表
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入topic")
	public R submit(@Valid @RequestBody Topic topic) {
		return R.status(topicService.saveOrUpdate(topic));
	}

	
	/**
	* 删除 专题表
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(topicService.deleteLogic(Func.toLongList(ids)));
	}

	
}

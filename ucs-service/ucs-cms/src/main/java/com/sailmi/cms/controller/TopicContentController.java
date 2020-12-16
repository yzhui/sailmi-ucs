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
import com.sailmi.cms.entity.TopicContent;
import com.sailmi.cms.vo.TopicContentVO;
import com.sailmi.cms.wrapper.TopicContentWrapper;
import com.sailmi.cms.service.ITopicContentService;
import com.sailmi.core.boot.ctrl.AppController;
import java.util.List;

/**
 * 专题内容关系 控制器
 *
 * @author sailmi
 * @since 2020-12-16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/topiccontent")
@Api(value = "专题内容关系", tags = "专题内容关系接口")
public class TopicContentController extends AppController {

	private ITopicContentService topicContentService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入topicContent")
	public R<TopicContentVO> detail(TopicContent topicContent) {
		TopicContent detail = topicContentService.getOne(Condition.getQueryWrapper(topicContent));
		return R.data(TopicContentWrapper.build().entityVO(detail));
	}

	/**
	* 分页 专题内容关系
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入topicContent")
	public R<IPage<TopicContentVO>> list(TopicContent topicContent, Query query) {
		IPage<TopicContent> pages = topicContentService.page(Condition.getPage(query), Condition.getQueryWrapper(topicContent));
		return R.data(TopicContentWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 专题内容关系
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入topicContent")
	public R<IPage<TopicContentVO>> page(TopicContentVO topicContent, Query query) {
		IPage<TopicContentVO> pages = topicContentService.selectTopicContentPage(Condition.getPage(query), topicContent);
		return R.data(pages);
	}

	/**
	* 新增 专题内容关系
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入topicContent")
	public R save(@Valid @RequestBody TopicContent topicContent) {
		return R.status(topicContentService.save(topicContent));
	}

	/**
	* 修改 专题内容关系
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入topicContent")
	public R update(@Valid @RequestBody TopicContent topicContent) {
		return R.status(topicContentService.updateById(topicContent));
	}

	/**
	* 新增或修改 专题内容关系
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入topicContent")
	public R submit(@Valid @RequestBody TopicContent topicContent) {
		return R.status(topicContentService.saveOrUpdate(topicContent));
	}

	
	/**
	* 删除 专题内容关系
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(topicContentService.deleteLogic(Func.toLongList(ids)));
	}

	
}

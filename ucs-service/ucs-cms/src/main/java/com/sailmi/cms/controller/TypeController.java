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

import com.sailmi.cms.utils.TreeEntity;
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
import com.sailmi.cms.entity.Type;
import com.sailmi.cms.vo.TypeVO;
import com.sailmi.cms.wrapper.TypeWrapper;
import com.sailmi.cms.service.ITypeService;
import com.sailmi.core.boot.ctrl.AppController;
import java.util.List;

/**
 * 内容分类 控制器
 *
 * @author sailmi
 * @since 2020-12-16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/type")
@Api(value = "内容分类", tags = "内容分类接口")
public class TypeController extends AppController {

	private ITypeService typeService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入type")
	public R<TypeVO> detail(Type type) {
		Type detail = typeService.getOne(Condition.getQueryWrapper(type));
		return R.data(TypeWrapper.build().entityVO(detail));
	}

	/**
	* 分页 内容分类
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入type")
	public R<IPage<TypeVO>> list(Type type, Query query) {
		IPage<Type> pages = typeService.page(Condition.getPage(query), Condition.getQueryWrapper(type));
		return R.data(TypeWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 内容分类
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入type")
	public R<IPage<TypeVO>> page(TypeVO type, Query query) {
		IPage<TypeVO> pages = typeService.selectTypePage(Condition.getPage(query), type);
		return R.data(pages);
	}

	/**
	* 新增 内容分类
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入type")
	public R save(@Valid @RequestBody Type type) {
		return R.status(typeService.save(type));
	}

	/**
	* 修改 内容分类
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入type")
	public R update(@Valid @RequestBody Type type) {
		return R.status(typeService.updateById(type));
	}

	/**
	* 新增或修改 内容分类
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入type")
	public R submit(@Valid @RequestBody Type type) {
		return R.status(typeService.saveOrUpdate(type));
	}


	/**
	* 删除 内容分类
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(typeService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * 主站下载中心 下载分类树
	 */
	@GetMapping("/downTypeTree")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "下载分类的树结构", notes = "不传参数")
	public R downTypeTree() {
		List<TreeEntity> downTree = typeService.downTypeTree();
		return R.data(downTree);
	}

	/**
	 *树
	 */
	@GetMapping("/tree")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "内容分类的树结构", notes = "不传参数")
	public R tree() {
		List<TreeEntity> downTree = typeService.tree();
		return R.data(downTree);
	}
}

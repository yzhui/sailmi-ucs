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
import com.sailmi.cms.entity.UcsCmsPertinent;
import com.sailmi.cms.vo.UcsCmsPertinentVO;
import com.sailmi.cms.wrapper.UcsCmsPertinentWrapper;
import com.sailmi.cms.service.IUcsCmsPertinentService;
import com.sailmi.core.boot.ctrl.AppController;
import java.util.List;

/**
 * 相关内容表 控制器
 *
 * @author sailmi
 * @since 2020-12-16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ucscmspertinent")
@Api(value = "相关内容表", tags = "相关内容表接口")
public class UcsCmsPertinentController extends AppController {

	private IUcsCmsPertinentService ucsCmsPertinentService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入ucsCmsPertinent")
	public R<UcsCmsPertinentVO> detail(UcsCmsPertinent ucsCmsPertinent) {
		UcsCmsPertinent detail = ucsCmsPertinentService.getOne(Condition.getQueryWrapper(ucsCmsPertinent));
		return R.data(UcsCmsPertinentWrapper.build().entityVO(detail));
	}

	/**
	* 分页 相关内容表
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入ucsCmsPertinent")
	public R<IPage<UcsCmsPertinentVO>> list(UcsCmsPertinent ucsCmsPertinent, Query query) {
		IPage<UcsCmsPertinent> pages = ucsCmsPertinentService.page(Condition.getPage(query), Condition.getQueryWrapper(ucsCmsPertinent));
		return R.data(UcsCmsPertinentWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 相关内容表
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入ucsCmsPertinent")
	public R<IPage<UcsCmsPertinentVO>> page(UcsCmsPertinentVO ucsCmsPertinent, Query query) {
		IPage<UcsCmsPertinentVO> pages = ucsCmsPertinentService.selectUcsCmsPertinentPage(Condition.getPage(query), ucsCmsPertinent);
		return R.data(pages);
	}

	/**
	* 新增 相关内容表
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入ucsCmsPertinent")
	public R save(@Valid @RequestBody UcsCmsPertinent ucsCmsPertinent) {
		return R.status(ucsCmsPertinentService.save(ucsCmsPertinent));
	}

	/**
	* 修改 相关内容表
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入ucsCmsPertinent")
	public R update(@Valid @RequestBody UcsCmsPertinent ucsCmsPertinent) {
		return R.status(ucsCmsPertinentService.updateById(ucsCmsPertinent));
	}

	/**
	* 新增或修改 相关内容表
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入ucsCmsPertinent")
	public R submit(@Valid @RequestBody UcsCmsPertinent ucsCmsPertinent) {
		return R.status(ucsCmsPertinentService.saveOrUpdate(ucsCmsPertinent));
	}

	
	/**
	* 删除 相关内容表
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(ucsCmsPertinentService.deleteLogic(Func.toLongList(ids)));
	}

	
}

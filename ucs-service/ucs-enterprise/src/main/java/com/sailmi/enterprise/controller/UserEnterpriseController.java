/**
 * Copyright (c) 2018-2028.
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
package com.sailmi.enterprise.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.enterprise.service.IUserEnterpriseService;
import com.sailmi.enterprise.wrapper.UserEnterpriseWrapper;
import com.sailmi.system.entity.UserEnterprise;
import com.sailmi.system.vo.UserEnterpriseVO;
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
import com.sailmi.core.boot.ctrl.AppController;
import java.util.List;

/**
 * 用户-企业关系表 控制器
 *
 * @author sailmi
 * @since 2020-10-16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/userenterprise")
@Api(value = "用户-企业关系表", tags = "用户-企业关系表接口")
public class UserEnterpriseController extends AppController {

	private IUserEnterpriseService userEnterpriseService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入userEnterprise")
	public R<UserEnterpriseVO> detail(UserEnterprise userEnterprise) {
		UserEnterprise detail = userEnterpriseService.getOne(Condition.getQueryWrapper(userEnterprise));
		return R.data(UserEnterpriseWrapper.build().entityVO(detail));
	}

	/**
	* 分页 用户-企业关系表
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入userEnterprise")
	public R<IPage<UserEnterpriseVO>> list(UserEnterprise userEnterprise, Query query) {
		IPage<UserEnterprise> pages = userEnterpriseService.page(Condition.getPage(query), Condition.getQueryWrapper(userEnterprise));
		return R.data(UserEnterpriseWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 用户-企业关系表
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入userEnterprise")
	public R<IPage<UserEnterpriseVO>> page(UserEnterpriseVO userEnterprise, Query query) {
		IPage<UserEnterpriseVO> pages = userEnterpriseService.selectUserEnterprisePage(Condition.getPage(query), userEnterprise);
		return R.data(pages);
	}

	/**
	* 新增 用户-企业关系表
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入userEnterprise")
	public R save(@Valid @RequestBody UserEnterprise userEnterprise) {
		return R.status(userEnterpriseService.save(userEnterprise));
	}

	/**
	* 修改 用户-企业关系表
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入userEnterprise")
	public R update(@Valid @RequestBody UserEnterprise userEnterprise) {
		return R.status(userEnterpriseService.updateById(userEnterprise));
	}

	/**
	* 新增或修改 用户-企业关系表
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入userEnterprise")
	public R submit(@Valid @RequestBody UserEnterprise userEnterprise) {
		return R.status(userEnterpriseService.saveOrUpdate(userEnterprise));
	}


	/**
	* 删除 用户-企业关系表
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(userEnterpriseService.removeByIds(Func.toLongList(ids)));
	}


}

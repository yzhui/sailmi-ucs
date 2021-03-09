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
import com.sailmi.enterprise.service.IServiceEnterpriseService;
import com.sailmi.enterprise.wrapper.ServiceEnterpriseWrapper;
import com.sailmi.system.entity.ServiceEnterprise;
import com.sailmi.system.vo.ServiceEnterpriseVO;
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
 * 服务企业授权表，表示哪个企业具有此服务的权限，此版本暂不考虑个人用户的服务授权 控制器
 *
 * @author sailmi
 * @since 2020-10-14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/serviceenterprise")
@Api(value = "服务企业授权表，表示哪个企业具有此服务的权限，此版本暂不考虑个人用户的服务授权", tags = "服务企业授权表，表示哪个企业具有此服务的权限，此版本暂不考虑个人用户的服务授权接口")
public class ServiceEnterpriseController extends AppController {

	private IServiceEnterpriseService serviceEnterpriseService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入serviceEnterprise")
	public R<ServiceEnterpriseVO> detail(ServiceEnterprise serviceEnterprise) {
		ServiceEnterprise detail = serviceEnterpriseService.getOne(Condition.getQueryWrapper(serviceEnterprise));
		return R.data(ServiceEnterpriseWrapper.build().entityVO(detail));
	}

	/**
	* 分页 服务企业授权表，表示哪个企业具有此服务的权限，此版本暂不考虑个人用户的服务授权
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入serviceEnterprise")
	public R<IPage<ServiceEnterpriseVO>> list(ServiceEnterprise serviceEnterprise, Query query) {
		IPage<ServiceEnterprise> pages = serviceEnterpriseService.page(Condition.getPage(query), Condition.getQueryWrapper(serviceEnterprise));
		return R.data(ServiceEnterpriseWrapper.build().pageVO(pages));
	}



	/**
	* 自定义分页 服务企业授权表，表示哪个企业具有此服务的权限，此版本暂不考虑个人用户的服务授权
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入serviceEnterprise")
	public R<IPage<ServiceEnterpriseVO>> page(ServiceEnterpriseVO serviceEnterprise, Query query) {
		IPage<ServiceEnterpriseVO> pages = serviceEnterpriseService.selectServiceEnterprisePage(Condition.getPage(query), serviceEnterprise);
		return R.data(pages);
	}

	/**
	* 新增 服务企业授权表，表示哪个企业具有此服务的权限，此版本暂不考虑个人用户的服务授权
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入serviceEnterprise")
	public R save(@Valid @RequestBody ServiceEnterprise serviceEnterprise) {
		return R.status(serviceEnterpriseService.save(serviceEnterprise));
	}

	/**
	* 修改 服务企业授权表，表示哪个企业具有此服务的权限，此版本暂不考虑个人用户的服务授权
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入serviceEnterprise")
	public R update(@Valid @RequestBody ServiceEnterprise serviceEnterprise) {
		return R.status(serviceEnterpriseService.updateById(serviceEnterprise));
	}

	/**
	* 新增或修改 服务企业授权表，表示哪个企业具有此服务的权限，此版本暂不考虑个人用户的服务授权
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入serviceEnterprise")
	public R submit(@Valid @RequestBody ServiceEnterprise serviceEnterprise) {
		return R.status(serviceEnterpriseService.saveOrUpdate(serviceEnterprise));
	}


	/**
	* 删除 服务企业授权表，表示哪个企业具有此服务的权限，此版本暂不考虑个人用户的服务授权
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(serviceEnterpriseService.removeByIds(Func.toLongList(ids)));
	}


}

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
package com.sailmi.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.sailmi.core.secure.AuthUser;
import com.sailmi.system.entity.SystemEntity;
import com.sailmi.system.entity.Tenant;
import com.sailmi.system.service.ISystemService;
import com.sailmi.system.service.ITenantService;
import com.sailmi.system.vo.AuthClientVo;
import com.sailmi.system.wrapper.AuthClientWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import com.sailmi.core.boot.ctrl.AppController;
import com.sailmi.core.mp.support.Condition;
import com.sailmi.core.mp.support.Query;
import com.sailmi.core.secure.annotation.PreAuth;
import com.sailmi.core.tool.api.R;
import com.sailmi.core.tool.constant.RoleConstant;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.system.entity.AuthClient;
import com.sailmi.system.service.IAuthClientService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *  应用管理控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@ApiIgnore
@Api(value = "应用管理", tags = "应用管理接口")
@PreAuth(RoleConstant.HAS_ROLE_TENANT_ADMIN)
public class AuthClientController extends AppController {

	private IAuthClientService clientService;
	private ISystemService systemService;
	private ITenantService tenantService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入client")
	public R<AuthClient> detail(AuthClient authClient) {
		AuthClient detail = clientService.getOne(Condition.getQueryWrapper(authClient));
		return R.data(detail);
	}

	/**
	* 分页
	*/
	@GetMapping("/list")
	@PreAuth(RoleConstant.HAS_ROLE_TENANT_ADMIN)
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入client")
	public R<IPage<AuthClientVo>> list(AuthUser user, AuthClient authClient, Query query) {
		IPage<AuthClient> pages=null;
		IPage<AuthClientVo> serviceVOIPage =null;
		if(user!=null && user.getEnterpriseId()!=null) {
			//需要查询租户表，获取此用户企业所管理的租户，然后查询此租户下所有的系统与client
			QueryWrapper<Tenant> tenantQueryWrapper = new QueryWrapper<>();
			tenantQueryWrapper.eq("enterprise_id",user.getEnterpriseId());
			List<Tenant> tenalist = tenantService.list(tenantQueryWrapper);
			if(tenalist!=null && tenalist.size()>0){
				ArrayList<String> strings = new ArrayList<>();
				tenalist.stream().forEach(tenant -> {
					strings.add(tenant.getTenantId());
				});
				if(strings.size()>0) {
					QueryWrapper<SystemEntity> systemEntityQueryWrapper = new QueryWrapper<>();
					systemEntityQueryWrapper.in("tenant_id", strings);

					List<SystemEntity> list = systemService.list(systemEntityQueryWrapper);
					if(list!=null && list.size()>0){
						ArrayList<Long> systemIds = new ArrayList<>();
						list.stream().forEach(sysEntity->{
							systemIds.add(sysEntity.getId());
						});
						QueryWrapper<AuthClient> queryWrapper = Condition.getQueryWrapper(authClient);
						if(systemIds!=null && systemIds.size()>0){
							queryWrapper.in("system_id",systemIds);
							 pages = clientService.page(Condition.getPage(query), queryWrapper);
						}
					}
				}
			}
		}
		if(pages!=null && pages.getTotal()>0) {
			 serviceVOIPage = AuthClientWrapper.build().pageVO(pages);
			if (serviceVOIPage != null && serviceVOIPage.getTotal() > 0) {
				serviceVOIPage.getRecords().stream().forEach(clientVo -> {
					clientVo.setSystemName(systemService.getById(clientVo.getSystemId()).getSystemName());
				});
			}
		}
		return R.data(serviceVOIPage);
	}

	/**
	* 新增
	*/
	@PostMapping("/save")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "新增", notes = "传入client")
	public R save(@Valid @RequestBody AuthClient authClient) {
		return R.status(clientService.save(authClient));
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "修改", notes = "传入client")
	public R update(@Valid @RequestBody AuthClient authClient) {
		return R.status(clientService.updateById(authClient));
	}

	/**
	* 新增或修改
	*/
	@PostMapping("/submit")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "新增或修改", notes = "传入client")
	public R submit(@Valid @RequestBody AuthClient authClient) {
		return R.status(clientService.saveOrUpdate(authClient));
	}


	/**
	* 删除
	*/
	@PostMapping("/remove")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(clientService.deleteLogic(Func.toLongList(ids)));
	}


}

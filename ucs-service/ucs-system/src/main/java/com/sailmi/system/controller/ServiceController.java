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
package com.sailmi.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sailmi.core.secure.AuthUser;
import com.sailmi.system.entity.ServiceEntity;
import com.sailmi.system.entity.SystemEntity;
import com.sailmi.system.entity.Tenant;
import com.sailmi.system.service.IServiceService;
import com.sailmi.system.service.ISystemService;
import com.sailmi.system.service.ITenantService;
import com.sailmi.system.user.entity.UserInfo;
import com.sailmi.system.user.feign.IUserClient;
import com.sailmi.system.vo.ServiceVO;
import com.sailmi.system.wrapper.ServiceWrapper;
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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.core.boot.ctrl.AppController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 可提供的服务清单，企业可以通过服务清单 控制器
 *
 * @author sailmi
 * @since 2020-09-09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/service")
@Api(value = "可提供的服务清单，企业可以通过服务清单", tags = "可提供的服务清单，企业可以通过服务清单接口")
public class ServiceController extends AppController {

	private IServiceService serviceService;
	private ISystemService systemService;
	private IUserClient userClient;
	private ITenantService tenantService;


	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入service")
	public R<ServiceVO> detail(ServiceEntity service) {
		ServiceEntity detail = serviceService.getOne(Condition.getQueryWrapper(service));
		return R.data(ServiceWrapper.build().entityVO(detail));
	}

	/**
	* 分页 可提供的服务清单，企业可以通过服务清单
	 * h后台管理员可以管理所有service，否则只是本公司的服务清单
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入service")
	public R<IPage<ServiceVO>> list(AuthUser user,ServiceEntity service, Query query) {
		//获取该tenantId下的所有system，根据system查询所有service
		IPage<ServiceEntity> pages=null;
		IPage<ServiceVO> serviceVOIPage =null;
		if(user!=null && user.getEnterpriseId()!=null) {
			QueryWrapper<Tenant> tenantQueryWrapper = new QueryWrapper<>();
			tenantQueryWrapper.eq("enterprise_id",user.getEnterpriseId());//查询改企业管理的所有的租户
			List<Tenant> tenantList = tenantService.list(tenantQueryWrapper);
			if(tenantList!=null && tenantList.size()>0) {
				ArrayList<String> longs = new ArrayList<>();
				tenantList.stream().forEach(teant -> {
					longs.add(teant.getTenantId());
				});
				QueryWrapper<SystemEntity> systemEntityQueryWrapper = new QueryWrapper<>();
				if (longs.size() > 0) {
					systemEntityQueryWrapper.in("tenant_id", longs);
				}
				List<SystemEntity> list = systemService.list(systemEntityQueryWrapper);
				if (list != null && list.size() > 0) {
					ArrayList<Long> systemIds = new ArrayList<>();
					list.stream().forEach(sysEntity -> {
						systemIds.add(sysEntity.getId());
					});

					final QueryWrapper<ServiceEntity> queryWrapper = Condition.getQueryWrapper(service);
					if (systemIds != null && systemIds.size() > 0) {
						queryWrapper.in("system_id", systemIds);
						pages = serviceService.page(Condition.getPage(query), queryWrapper);
					}

				}
			}
		}
		if(pages!=null && pages.getTotal()>0) {
			 serviceVOIPage = ServiceWrapper.build().pageVO(pages);
			if (serviceVOIPage != null && serviceVOIPage.getTotal() > 0) {
				serviceVOIPage.getRecords().stream().forEach(serviceVO -> {
					SystemEntity sysInfo = systemService.getById(serviceVO.getSystemId());
					if (sysInfo != null && sysInfo.getSystemName() != null) {
						serviceVO.setSystemName(sysInfo.getSystemName());
					}
					if (serviceVO.getServiceType() == 0) {
						serviceVO.setServiceTypeName("公共授权");
					} else {
						serviceVO.setServiceTypeName("收费授权");
					}
					R<UserInfo> userInfoR = userClient.userInfo(serviceVO.getCreateUser());
					if (userInfoR != null && userInfoR.getData() != null && userInfoR.getData().getUser() != null && userInfoR.getData().getUser().getRealName() != null) {
						serviceVO.setCreateName(userInfoR.getData().getUser().getRealName());
					}
					R<UserInfo> userInfoU = userClient.userInfo(serviceVO.getUpdateUser());
					if (userInfoU != null && userInfoU.getData() != null && userInfoU.getData().getUser() != null && userInfoU.getData().getUser().getRealName() != null) {
						serviceVO.setUpdateName(userInfoU.getData().getUser().getRealName());
					}
				});
			}
		}
		return R.data(serviceVOIPage);
	}


	/**
	 * 获取服务包树形结构
	 * AuthUser user
	 */
	@GetMapping("/service-tree")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "服务包树形结构", notes = "服务报树形结构")
	public R<List<ServiceVO>> grantTree() {
		return R.data(serviceService.grantTree());
	}



	/**
	* 自定义分页 可提供的服务清单，企业可以通过服务清单
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入service")
	public R<IPage<ServiceVO>> page(ServiceVO service, Query query) {
		IPage<ServiceVO> pages = serviceService.selectServicePage(Condition.getPage(query), service);
		return R.data(pages);
	}

	/**
	 * 获取服务包的菜单的分配树形结构
	 */
	@GetMapping("/service-tree-keys")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "角色所分配的树", notes = "角色所分配的树")
	public R<List<String>> serviceTreeKeys(String serviceId) {
		return R.data(serviceService.serviceTreeKeys(serviceId));
	}
	/**
	 * 设置菜单权限
	 *
	 * @param serviceId
	 * @param menuIds
	 * @return
	 */
	@PostMapping("/grant")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "服务包菜单设置", notes = "传入serviceId以及menuId集合")
	public R grantService(@ApiParam(value = "roleId集合", required = true) @RequestParam String serviceId,
				   @ApiParam(value = "menuId集合", required = true) @RequestParam String menuIds) {
		boolean temp = serviceService.grant(serviceId, Func.toLongList(menuIds));
		return R.status(temp);
	}



	/**
	* 新增 可提供的服务清单，企业可以通过服务清单
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入service")
	public R save(@Valid @RequestBody ServiceEntity service) {
		return R.status(serviceService.save(service));
	}

	/**
	* 修改 可提供的服务清单，企业可以通过服务清单
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入service")
	public R update(@Valid @RequestBody ServiceEntity service) {
		return R.status(serviceService.updateById(service));
	}

	/**
	* 新增或修改 可提供的服务清单，企业可以通过服务清单
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入service")
	public R submit(@Valid @RequestBody ServiceEntity service) {
		return R.status(serviceService.saveOrUpdate(service));
	}


	/**
	* 删除 可提供的服务清单，企业可以通过服务清单
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(serviceService.removeByIds(Func.toLongList(ids)));
	}


}

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
import com.sailmi.system.entity.Enterprise;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.core.secure.annotation.PreAuth;
import com.sailmi.core.tool.constant.RoleConstant;
import com.sailmi.system.feign.IEnterpriseFeign;
import com.sailmi.system.vo.EnterpriseVO;
import com.sailmi.system.entity.Role;
import com.sailmi.system.service.IRoleService;
import com.sailmi.system.vo.TenantVo;
import com.sailmi.system.wrapper.TenantWrapper;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import com.sailmi.core.boot.ctrl.AppController;
import com.sailmi.core.mp.support.Condition;
import com.sailmi.core.mp.support.Query;
import com.sailmi.core.secure.AuthUser;
import com.sailmi.core.tool.api.R;
import com.sailmi.core.tool.constant.AppConstant;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.system.entity.Tenant;
import com.sailmi.system.service.ITenantService;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
@Api(value = "租户管理", tags = "租户管理接口")
public class TenantController extends AppController {
	private ITenantService tenantService;
	private IEnterpriseFeign enterpriseFeign;
	/**
	 * 详情
	 */
	@GetMapping("/detail")
	@ApiOperation(value = "租户详情", notes = "传入tenant")
	public R<Tenant> detail(Tenant tenant) {
		Tenant detail = tenantService.getOne(Condition.getQueryWrapper(tenant));
		return R.data(detail);
	}

	/**
	 * 分页
	 */
	@ApiIgnore
	@ApiOperation(value = "租户列表", notes = "传入tenant")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "tenantId", value = "参数名称", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "tenantName", value = "角色别名", paramType = "query", dataType = "string"),
		@ApiImplicitParam(name = "contactNumber", value = "联系电话", paramType = "query", dataType = "string")
	})
	@PreAuth(RoleConstant.HAS_ROLE_SAILPLAT_ADMIN)
	@GetMapping("/list")
	public R<IPage<TenantVo>> list(AuthUser user,@ApiIgnore @RequestParam Map<String, Object> tenant, Query query) {
		QueryWrapper<Tenant> queryWrapper = Condition.getQueryWrapper(tenant, Tenant.class);
		if(user!=null && user.getTenantId()!=null){
			if(user.getTenantId().equals("000000")) {//平台管理员

			}else{//租户管理员
				queryWrapper.eq("tenant_id", user.getTenantId());
			}
		}
		IPage<Tenant> pages = tenantService.page(Condition.getPage(query),queryWrapper);
		IPage<TenantVo> tenantVOIPage = TenantWrapper.build().pageVO(pages);
		if(tenantVOIPage!=null && tenantVOIPage.getTotal()>0){
			tenantVOIPage.getRecords().stream().forEach(tenantVo->{
				Enterprise enterprise = new Enterprise();
				if(tenantVo.getEnterpriseId()!=null && tenantVo.getEnterpriseId().length()>0){
					enterprise.setId(Long.valueOf(tenantVo.getEnterpriseId()));
					R<EnterpriseVO> detail = enterpriseFeign.detailInfo(enterprise);
					if (detail != null && detail.getData() != null && detail.getData().getEnterpriseName() != null) {
						tenantVo.setEnterpriseName(detail.getData().getEnterpriseName());
					}
				}
			});
		}
		return R.data(tenantVOIPage);
	}

	/**
	 * 下拉数据源
	 */
	@ApiOperation(value = "租户下拉数据源", notes = "传入tenant")
	@GetMapping("/select")
	public R<List<Tenant>> select(Tenant tenant, AuthUser authUser) {
		System.out.println("............Now Select Tenant!"+tenant);
		QueryWrapper<Tenant> queryWrapper = Condition.getQueryWrapper(tenant);
		List<Tenant> list = tenantService.list((!authUser.getTenantId().equals(AppConstant.ADMIN_TENANT_ID)) ? queryWrapper.lambda().eq(Tenant::getTenantId, authUser.getTenantId()) : queryWrapper);
		return R.data(list);
	}

	/**
	 * 自定义分页
	 */
	@GetMapping("/page")
	@ApiOperation(value = "租户分页", notes = "传入tenant")
	@ApiIgnore
	public R<IPage<Tenant>> page(Tenant tenant, Query query) {
		IPage<Tenant> pages = tenantService.selectTenantPage(Condition.getPage(query), tenant);
		return R.data(pages);
	}

	/**
	 * 新增或修改
	 */
	@PostMapping("/submit")
	@ApiOperation(value = "租户新增或修改", notes = "传入tenant")
	public R submit(AuthUser authUser,@Valid @RequestBody Tenant tenant) {
		return R.status(tenantService.saveTenant(tenant));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperation(value = "租户逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(tenantService.deleteLogic(Func.toLongList(ids)));
	}

}

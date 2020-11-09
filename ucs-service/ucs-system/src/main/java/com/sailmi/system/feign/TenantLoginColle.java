package com.sailmi.system.feign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sailmi.core.tool.api.R;
import com.sailmi.system.entity.Tenant;
import com.sailmi.system.service.ITenantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@AllArgsConstructor
public class TenantLoginColle implements  TenantFeign{

	private ITenantService tenantService;

	@Override
	@PostMapping(API_PREFIX + "/tenatinfo-list")
	public R<List<Tenant>> queryLoginUserTeants(@RequestParam(value = "enterpriseId",required = true) String enterpriseId) {
		QueryWrapper<Tenant> tenantQueryWrapper = new QueryWrapper<>();
		tenantQueryWrapper.eq("enterprise_id",enterpriseId);
		List<Tenant> list = tenantService.list(tenantQueryWrapper);
		return R.data(list);
	}
}

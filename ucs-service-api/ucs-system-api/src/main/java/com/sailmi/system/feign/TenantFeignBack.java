package com.sailmi.system.feign;

import com.sailmi.core.tool.api.R;
import com.sailmi.system.entity.Tenant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TenantFeignBack implements  TenantFeign{

	@Override
	public R<List<Tenant>> queryLoginUserTeants(String enterpriseId) {
		return R.fail("查询失败");
	}
}

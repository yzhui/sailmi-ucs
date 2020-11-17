package com.sailmi.system.feign;

import com.sailmi.core.tool.api.R;
import com.sailmi.system.entity.Tenant;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
public class TenantFeignBack implements  TenantFeign{

	@Override
	public R<List<Tenant>> queryLoginUserTeants(@RequestParam(value = "enterpriseId",required = true) String enterpriseId) {
		return R.data(null);
	}
}

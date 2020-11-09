package com.sailmi.system.feign;

import com.sailmi.core.launch.constant.LaunchConstant;
import com.sailmi.core.tool.api.R;
import com.sailmi.system.entity.Tenant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
	value = LaunchConstant.APPLICATION_SYSTEM_NAME,
	fallback = TenantFeignBack.class
)
public interface TenantFeign {

	String API_PREFIX = "/tenant";

	@PostMapping(API_PREFIX + "/tenatinfo-list")
	R<List<Tenant>> queryLoginUserTeants(@RequestParam(value = "enterpriseId",required = true) String enterpriseId);

}

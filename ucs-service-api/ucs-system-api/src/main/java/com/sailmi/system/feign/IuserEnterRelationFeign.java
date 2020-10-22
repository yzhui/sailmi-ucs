package com.sailmi.system.feign;

import com.sailmi.core.tool.api.R;
import com.sailmi.system.entity.UserEnterprise;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
	value = "sailmi-enterprise",
	fallback = UserEnterpriseFallBack.class
)
public interface IuserEnterRelationFeign {
	String API_PREFIX = "/userenterprise";

	@GetMapping(API_PREFIX + "/userenterlist")
	R<List<UserEnterprise>> detailInfo(String enterpriseId,Boolean flag);


}

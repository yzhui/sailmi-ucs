package com.sailmi.system.feign;


import com.sailmi.core.tool.api.R;
import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.vo.EnterpriseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
	value = "sailmi-enterprise",
	fallback = EnterpriseFallBack.class
)
public interface IEnterpriseFeign {

	String API_PREFIX = "/enterprise";

	@GetMapping(API_PREFIX + "/enter-detail")
	R<EnterpriseVO> detailInfo(Enterprise enterprise);

}

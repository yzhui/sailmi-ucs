package com.sailmi.system.feign;


import com.sailmi.core.tool.api.R;
import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.entity.ServiceEnterprise;
import com.sailmi.system.entity.UserEnterprise;
import com.sailmi.system.vo.EnterpriseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(
	value = "sailmi-enterprise",
	fallback = EnterpriseFallBack.class
)
public interface IEnterpriseFeign {

	String API_PREFIX = "/enterprise";

	String API_UEPREFIX = "/serviceenterprise";

	@GetMapping(API_PREFIX + "/enter-detail")
	R<EnterpriseVO> detailInfo(Enterprise enterprise);

	@PostMapping(API_PREFIX+"/enter-update")
	public R update(@Valid @RequestBody Enterprise enterprise);

	@PostMapping(API_PREFIX+"/userenter-detail")
	public R<UserEnterprise> queryUserEnterpriseInfo(String enterpriseId);

	@GetMapping(API_UEPREFIX+"/serviceenterlist")
	public R<List<ServiceEnterprise>> servicelist(ServiceEnterprise serviceEnterprise);

}

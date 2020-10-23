package com.sailmi.system.feign;

import com.sailmi.core.launch.constant.LaunchConstant;
import com.sailmi.system.entity.UserRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(
	value = LaunchConstant.APPLICATION_SYSTEM_NAME,
	fallback = IUserRoleFeignBack.class
)
public interface IUserRoleFeign {
	String API_PREFIX = "/userrole";

	@GetMapping(API_PREFIX + "/user-role-relation")
	Boolean insertRoleUserRealtion(@RequestBody UserRole userRole);

}

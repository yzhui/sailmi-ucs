package com.sailmi.system.feign;

import com.sailmi.system.entity.UserRole;
import com.sailmi.system.service.IUserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RoleUserRelation  implements  IUserRoleFeign{

	private IUserRoleService userRoleService;

	@Override
	@GetMapping(API_PREFIX + "/user-role-relation")
	public Boolean insertRoleUserRealtion(UserRole userRole) {
		return userRoleService.saveOrUpdate(userRole);
	}
}

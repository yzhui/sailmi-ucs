package com.sailmi.system.feign;

import com.sailmi.system.entity.UserRole;
import org.springframework.stereotype.Component;

@Component
public class IUserRoleFeignBack implements  IUserRoleFeign{

	@Override
	public Boolean insertRoleUserRealtion(UserRole userRole) {
		return false;
	}
}

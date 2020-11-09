package com.sailmi.system.feign;

import com.sailmi.core.tool.api.R;
import com.sailmi.system.entity.UserEnterprise;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Component
public class UserEnterpriseFallBack implements IuserEnterRelationFeign{
	@Override
	public R<List<UserEnterprise>> detailInfo( String enterpriseId) {
		return R.fail("未获取到企业用户信息");
	}
}

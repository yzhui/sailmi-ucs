package com.sailmi.system.feign;

import com.sailmi.core.tool.api.R;

import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.entity.ServiceEnterprise;
import com.sailmi.system.entity.UserEnterprise;
import com.sailmi.system.vo.EnterpriseVO;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
public class EnterpriseFallBack implements IEnterpriseFeign{

	@Override
	public R<EnterpriseVO> detailInfo(Enterprise enterprise) {
		return R.fail("未获取到企业信息");
	}

	@Override
	public R update(@Valid Enterprise enterprise) {
		return R.status(false);
	}

	@Override
	public R<UserEnterprise> queryUserEnterpriseInfo(String enterpriseId) {
		return R.fail("未获取到企业和用户信息");
	}

	@Override
	public R<List<ServiceEnterprise>> servicelist(ServiceEnterprise serviceEnterprise) {
		return R.data(null);
	}
}

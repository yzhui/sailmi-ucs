package com.sailmi.system.feign;

import com.sailmi.core.tool.api.R;

import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.vo.EnterpriseVO;
import org.springframework.stereotype.Component;

@Component
public class EnterpriseFallBack implements IEnterpriseFeign{

	@Override
	public R<EnterpriseVO> detailInfo(Enterprise enterprise) {
		return R.fail("未获取到企业信息");
	}
}

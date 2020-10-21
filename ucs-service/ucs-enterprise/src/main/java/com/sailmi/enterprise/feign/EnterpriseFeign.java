package com.sailmi.enterprise.feign;

import com.sailmi.core.tool.api.R;
import com.sailmi.system.entity.Enterprise;
import com.sailmi.enterprise.service.IEnterpriseService;
import com.sailmi.system.vo.EnterpriseVO;
import com.sailmi.enterprise.wrapper.EnterpriseWrapper;
import com.sailmi.system.feign.IEnterpriseFeign;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EnterpriseFeign implements IEnterpriseFeign {

	private IEnterpriseService enterpriseService;

	@Override
	@GetMapping(API_PREFIX + "/enter-detail")
	public R<EnterpriseVO> detailInfo( Enterprise enterprise) {
		Enterprise byId = enterpriseService.getById(enterprise.getId());
		return R.data(
			EnterpriseWrapper.build().entityVO(byId)
		);
	}
}

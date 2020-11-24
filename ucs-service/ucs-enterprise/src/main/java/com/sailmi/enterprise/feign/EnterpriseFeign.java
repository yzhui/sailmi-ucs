package com.sailmi.enterprise.feign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sailmi.core.mp.support.Condition;
import com.sailmi.core.tool.api.R;
import com.sailmi.enterprise.service.IServiceEnterpriseService;
import com.sailmi.enterprise.service.IUserEnterpriseService;
import com.sailmi.system.entity.Enterprise;
import com.sailmi.enterprise.service.IEnterpriseService;
import com.sailmi.system.entity.ServiceEnterprise;
import com.sailmi.system.entity.UserEnterprise;
import com.sailmi.system.vo.EnterpriseVO;
import com.sailmi.enterprise.wrapper.EnterpriseWrapper;
import com.sailmi.system.feign.IEnterpriseFeign;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class EnterpriseFeign implements IEnterpriseFeign {

	private IEnterpriseService enterpriseService;
	private IUserEnterpriseService userEnterpriseService;
	private IServiceEnterpriseService iServiceEnterpriseService;

	@Override
	@GetMapping(API_PREFIX + "/enter-detail")
	public R<EnterpriseVO> detailInfo( Enterprise enterprise) {
		Enterprise byId = enterpriseService.getById(enterprise.getId());
		return R.data(
			EnterpriseWrapper.build().entityVO(byId)
		);
	}

	@Override
	@PostMapping(API_PREFIX+"/enter-update")
	public R update(@Valid Enterprise enterprise) {
		boolean b = enterpriseService.updateById(enterprise);
		return R.status(b);
	}

	@Override
	@PostMapping(API_PREFIX+"/userenter-detail")
	public R<UserEnterprise> queryUserEnterpriseInfo(String enterpriseId) {
		QueryWrapper<UserEnterprise> userEnterpriseQueryWrapper = new QueryWrapper<>();
		userEnterpriseQueryWrapper.eq("enterprise_id",enterpriseId).eq("status",1);
		UserEnterprise one = userEnterpriseService.getOne(userEnterpriseQueryWrapper);
		return R.data(one);
	}

	@Override
	@GetMapping(API_UEPREFIX+"/serviceenterlist")
	public R<List<ServiceEnterprise>> servicelist(ServiceEnterprise serviceEnterprise) {
		List<ServiceEnterprise> list = iServiceEnterpriseService.list(Condition.getQueryWrapper(serviceEnterprise));
		return R.data(list);
	}
}

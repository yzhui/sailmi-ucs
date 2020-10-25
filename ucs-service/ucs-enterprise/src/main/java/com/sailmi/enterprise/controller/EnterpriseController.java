/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sailmi.enterprise.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sailmi.core.secure.AuthUser;
import com.sailmi.core.tool.utils.DigestUtil;
import com.sailmi.enterprise.service.IEnterpriseDetailsService;
import com.sailmi.enterprise.service.IEnterpriseFinanceService;
import com.sailmi.system.entity.EnterpriseDetails;
import com.sailmi.system.entity.EnterpriseFinance;
import com.sailmi.system.user.entity.User;
import com.sailmi.system.user.feign.IUserClient;
import com.sailmi.system.vo.MenuVO;
import com.sailmi.system.vo.ServiceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import com.sailmi.core.mp.support.Condition;
import com.sailmi.core.mp.support.Query;
import com.sailmi.core.tool.api.R;
import com.sailmi.core.tool.utils.Func;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.vo.EnterpriseVO;
import com.sailmi.enterprise.wrapper.EnterpriseWrapper;
import com.sailmi.enterprise.service.IEnterpriseService;
import com.sailmi.core.boot.ctrl.AppController;

import java.util.Date;
import java.util.List;

/**
 *  控制器
 *
 * @author sailmi
 * @since 2020-09-02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/enterprise")
@Api(value = "", tags = "接口")
public class EnterpriseController extends AppController {

	private IEnterpriseService enterpriseService;
	private IUserClient iUserClient;
	private IEnterpriseDetailsService iEnterpriseDetailsService; //企业详细信息syt
	private IEnterpriseFinanceService iEnterpriseFinanceService;//企业财务信息syt
	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入enterprise")
	public R<EnterpriseVO> detail(Enterprise enterprise) {
		Enterprise detail = enterpriseService.getOne(Condition.getQueryWrapper(enterprise));
		return R.data(EnterpriseWrapper.build().entityVO(detail));
	}


	/**
	 * 获取企业分配的服务包的分配树形结构
	 */
	@GetMapping("/enterprise-tree-keys")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "角色所分配的树", notes = "角色所分配的树")
	public R<List<String>> enterpriseServiceTreeKeys(String enterpriseId) {
		return R.data(enterpriseService.enterpriServiceTreeKeys(enterpriseId));
	}



	/**
	 * 设置企业服务包
	 *
	 * @param enterpriseId
	 * @param serviceIds
	 * @RequestBody(required=false) AuthUser user
	 * @return
	 */
	@PostMapping("/servicerant")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "服务包菜单设置", notes = "传入enterpriseId和serviceIds")
	public R grantService(AuthUser user,@ApiParam(value = "企业ID", required = true) @RequestParam String enterpriseId,
						  @ApiParam(value = "serviceId集合", required = true) @RequestParam String serviceIds) {
		boolean temp = enterpriseService.grantservices(enterpriseId, Func.toLongList(serviceIds),user);
		return R.status(temp);
	}
	/**
	* 分页
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入enterprise")
	public R<IPage<EnterpriseVO>> list(AuthUser user,Enterprise enterprise, Query query) {
		QueryWrapper<Enterprise> queryWrapper = Condition.getQueryWrapper(enterprise);
		if(user!=null && user.getTenantId()!=null){
			if(user.getTenantId().equals("000000")) {//平台管理员

			}else{
				queryWrapper.eq("tenant_id", user.getTenantId());
			}
		}
		IPage<Enterprise> pages = enterpriseService.page(Condition.getPage(query), queryWrapper);
		return R.data(EnterpriseWrapper.build().pageVO(pages));
	}


	@GetMapping("/enterpriselist")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入enterprise")
	public R<List<Enterprise>> list() {
		QueryWrapper<Enterprise> enterpriseQueryWrapper = new QueryWrapper<>();
		enterpriseQueryWrapper.eq("tenant_id","000000");
		List<Enterprise> enterLists = enterpriseService.list(enterpriseQueryWrapper);
		return R.data(enterLists);
	}
	/**
	* 自定义分页
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入enterprise")
	public R<IPage<EnterpriseVO>> page(EnterpriseVO enterprise, Query query) {
		IPage<EnterpriseVO> pages = enterpriseService.selectEnterprisePage(Condition.getPage(query), enterprise);
		return R.data(pages);
	}

	/**
	* 新增
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入enterprise")
	public R save(AuthUser authUser,@Valid @RequestBody Enterprise enterprise) {
		if(enterprise!=null){
			if(StringUtils.isEmpty(enterprise.getTenantId())){
				enterprise.setTenantId("000000");
			}
		}
			//平台管理员或租户建立企业
		enterprise.setIsDeleted(0);
		enterprise.setCreateTime(new Date());
		enterprise.setCreateUser(authUser.getUserId());
		Enterprise save = enterpriseService.saveEnterpriseInfo(enterprise);
		//创建默认user
		User user = new User();
		user.setId(null);
		user.setAccount("admin");
		//这里的租户应该是创建者的租户,说明创建的这个用户属于创建人的租户
		user.setTenantId(authUser.getTenantId());
		user.setPassword(DigestUtil.encrypt("123456"));//默认密码
		user.setDefaultEnterpriseId(save.getId());
		user.setCreateTime(new Date());
		user.setCreateUser(authUser.getUserId());
		user.setIsDeleted(0);
		User r = iUserClient.submitUserInfo(user);

		//绑定用户 角色（-2，-3）


		//



		return R.status(true);
	}

	/**
	* 修改
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入enterprise")
	public R update(@Valid @RequestBody Enterprise enterprise) {
		if(enterprise!=null){
			if(StringUtils.isEmpty(enterprise.getTenantId())){
				enterprise.setTenantId("000000");
			}
		}
		return R.status(enterpriseService.updateById(enterprise));
	}

	/**
	* 新增或修改
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入enterprise")
	public R submit(@Valid @RequestBody Enterprise enterprise) {
		if(enterprise!=null){
			if(StringUtils.isEmpty(enterprise.getTenantId())){
				enterprise.setTenantId("000000");
			}
		}
		return R.status(enterpriseService.saveOrUpdate(enterprise));
	}


	/**
	* 删除
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(enterpriseService.deleteLogic(Func.toLongList(ids)));
	}

	/**
	 * <p>Description: 云测用户创建企业</p>
	 *
	 * @return: com.sailmi.core.tool.api.R
	 * @Author: syt
	 * @Date: 2020/10/23/0023 10:41
	 */
	@PostMapping("/create")
	@Transactional
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "创建企业", notes = "传入企业信息,详细信息,以及财务信息")
	public R create(AuthUser authUser, /*@Valid @RequestBody*/ Enterprise enterprise, EnterpriseDetails enterpriseDetails, EnterpriseFinance enterpriseFinance){
//		enterprise.setTenantId(authUser.getTenantId());//设置租户ID
		enterprise.setTenantId("123321");//设置租户ID
		Enterprise enterprise1 = enterpriseService.saveEnterpriseInfo(enterprise); //首先插入企业基本信息
		Long id = enterprise1.getId();
		//企业基本信息插入后插入企业详细信息
		if (id != 0 && id != null) {//企业基本信息插入成功后插入企业详细信息
			enterpriseDetails.setEnterpriseId(id);//企业详细信息关联企业ID
			EnterpriseDetails saveDetail = iEnterpriseDetailsService.saveDetail(enterpriseDetails);
			Long deId = saveDetail.getId();
			if (deId != 0 && deId != null) {//详细信息插入成功后插入财务信息
				enterpriseFinance.setEnterpriseId(id);
				int finace = iEnterpriseFinanceService.saveFiance(enterpriseFinance);
				if (finace > 0) {
					return R.success("创建企业成功");
				} else {
					return R.fail("失败:企业财务信息保存失败");
				}
			} else {
				return R.fail("失败:企业详细信息保存失败");
			}
		} else {
			return R.fail("失败:企业基本信息保存失败");
		}
	}

	@PostMapping("/verifySocial")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "校验社会统一验证码唯一性", notes = "传入企业社会统一验证码")
	public R VerifySocialCaptcha(@ApiParam(value = "社会统一验证码", required = true) @Valid String social){
		String re = enterpriseService.verifySocial(social);
		return R.success(re);
	}
}

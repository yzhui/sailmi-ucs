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

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sailmi.core.secure.AuthUser;
import com.sailmi.core.tool.utils.DigestUtil;
import com.sailmi.enterprise.service.IEnterpriseDetailsService;
import com.sailmi.enterprise.service.IEnterpriseFinanceService;
import com.sailmi.enterprise.service.IUserEnterpriseService;
import com.sailmi.system.entity.*;
import com.sailmi.system.feign.IUserRoleFeign;
import com.sailmi.system.feign.TenantFeign;
import com.sailmi.system.user.entity.User;
import com.sailmi.system.user.feign.IUserClient;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.system.vo.EnterpriseVO;
import com.sailmi.enterprise.wrapper.EnterpriseWrapper;
import com.sailmi.enterprise.service.IEnterpriseService;
import com.sailmi.core.boot.ctrl.AppController;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
	private IUserClient userClient;
	private IEnterpriseDetailsService iEnterpriseDetailsService; //企业详细信息syt
	private IEnterpriseFinanceService iEnterpriseFinanceService;//企业财务信息syt
	private IUserRoleFeign iUserRoleFeign;
	private IUserEnterpriseService userEnterpriseService;
	private TenantFeign tenantFeign;

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
		//查询该企业下的租户管理的所有企业列表
		if(user!=null && user.getEnterpriseId()!=null){
			//查询该企业下的所有租户
			R<List<Tenant>> tList = tenantFeign.queryLoginUserTeants(user.getEnterpriseId());
			ArrayList<String> strings = new ArrayList<>();
			if(tList!=null && tList.getData()!=null && tList.getData().size()>0){
				tList.getData().stream().forEach(tenant -> {
					strings.add(tenant.getTenantId());
				});
			}
			//查询租户下的所有企业
			if(strings.size()>0) {
				queryWrapper.in("tenant_id", strings);
			}
		}
		IPage<Enterprise> pages = enterpriseService.page(Condition.getPage(query), queryWrapper);
		return R.data(EnterpriseWrapper.build().pageVO(pages));
	}


	@GetMapping("/enterpriselist")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入enterprise")
	public R<List<Enterprise>> list(AuthUser authUser) {
		//查询该企业管理的租户下的企业列表
		QueryWrapper<Enterprise> enterpriseQueryWrapper = new QueryWrapper<>();
		if(authUser.getEnterpriseId()!=null){
			//查询该企业下的所有租户
			R<List<Tenant>> tList = tenantFeign.queryLoginUserTeants(authUser.getEnterpriseId());
			ArrayList<String> strings = new ArrayList<>();
			if(tList!=null && tList.getData()!=null && tList.getData().size()>0){
				tList.getData().stream().forEach(tenant -> {
					strings.add(tenant.getTenantId());
				});
			}
			if(strings.size()>0) {
				enterpriseQueryWrapper.in("tenant_id", strings);
			}
		}
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
		return R.status(enterpriseService.save(enterprise));
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
	 * @Valid @RequestBody
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入enterprise")
	@Transactional
	public R submit(AuthUser authUser,@Valid @RequestBody Enterprise enterprise) {
		ReturnEntity returnEntity = new ReturnEntity();
		try{
			//查询当前登陆人的企业的tenantID
			Enterprise byId = enterpriseService.getById(authUser.getEnterpriseId());
			//平台管理员或租户建立企业
			if(!StringUtils.isEmpty(byId.getTenantId())){
				enterprise.setTenantId(byId.getTenantId());
			}
			enterprise.setIsDeleted(0);
			enterprise.setCreateTime(new Date());
			enterprise.setCreateUser(authUser.getUserId());
			enterpriseService.saveEnterpriseInfo(enterprise);
			//创建默认user
			User user = new User();
			user.setAccount("admin"+enterprise.getId());
			//这里的租户应该是创建者的租户,说明创建的这个用户属于创建人的租户
			if(!StringUtils.isEmpty(byId.getTenantId())) {
				user.setTenantId(byId.getTenantId());
			}
			user.setPassword(DigestUtil.encrypt("123456"));//默认密码
			user.setDefaultEnterpriseId(enterprise.getId());
			user.setCreateUser(authUser.getUserId());
			user.setIsInitialpass(0);
			user.setIsDeleted(0);
			Long i = userClient.submitUserInfo(user);

			//用户企业关系表信息插入
			UserEnterprise userEnterprise = new UserEnterprise();
			userEnterprise.setEnterpriseId(enterprise.getId());
			userEnterprise.setUserId(i);
			userEnterprise.setStatus(1);
			userEnterprise.setIsDeleted(0);
			userEnterpriseService.save(userEnterprise);

			//绑定用户 角色（-2，-3）
			UserRole userRole1 = new UserRole();
			userRole1.setUserId(i);
			userRole1.setRoleId(-2l);
			userRole1.setStatus(0);
			userRole1.setIsDeleted(0);
			iUserRoleFeign.insertRoleUserRealtion(userRole1);
			UserRole userRole2 = new UserRole();
			userRole2.setUserId(i);
			userRole2.setRoleId(-3l);
			userRole2.setStatus(0);
			userRole2.setIsDeleted(0);
			iUserRoleFeign.insertRoleUserRealtion(userRole2);


			returnEntity.setAccount("admin"+enterprise.getId());
			returnEntity.setPassword("123456");
			if(!StringUtils.isEmpty(byId.getTenantId())) {
				returnEntity.setTenantId(byId.getTenantId());
			}
		}catch(Exception e){
			e.printStackTrace();
			return R.data(500,returnEntity,"添加失败");
		}
		return R.data(200,returnEntity,"添加成功");
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

	@PostMapping("/resetpass")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "重置密码", notes = "传入id")
	public R resetPass(@ApiParam(value = "主键", required = true) @RequestParam String id) {
		//查询该企业管理员的userId
		QueryWrapper<UserEnterprise> userEnterpriseQueryWrapper = new QueryWrapper<>();
		userEnterpriseQueryWrapper.eq("enterprise_id",id);
		userEnterpriseQueryWrapper.eq("status",1);
		UserEnterprise one = userEnterpriseService.getOne(userEnterpriseQueryWrapper);
		Boolean flag=false;
		if(one !=null && one.getUserId()!=null ){
			flag=userClient.resetUserPass(one.getUserId().toString());
		}
		return R.status(flag);
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
		enterprise.setTenantId(authUser.getTenantId());//设置租户ID
//		enterprise.setTenantId("123321");//设置租户ID
		enterpriseService.saveEnterpriseInfo(enterprise); //首先插入企业基本信息
		Long id = enterprise.getId();
		//企业基本信息插入后插入企业详细信息
		if (id != 0 && id != null) {//企业基本信息插入成功后插入企业详细信息
			enterpriseDetails.setEnterpriseId(id);//企业详细信息关联企业ID
			iEnterpriseDetailsService.saveDetail(enterpriseDetails);
			Long deId = enterpriseDetails.getId();
			if (deId != 0 && deId != null) {//详细信息插入成功后插入财务信息
				enterpriseFinance.setEnterpriseId(id);
				int finace = iEnterpriseFinanceService.saveFiance(enterpriseFinance);
				if (finace > 0) {
					return R.data(enterprise);
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

	/**
	 * 企业名称模糊查找
	 *
	 * @return Result
	 */
	@PostMapping(value = "enterpriseNameFuzzySearch")
	public Result enterpriseNameFuzzySearch() {
		Result result = new Result();
		List<Object> resu = new ArrayList<Object>();
		try {
			List<String> enterpriseName = enterpriseService.enterpriseNameFuzzySearch();
			if (enterpriseName.size() > 0) {
				for (int j = 0; j < enterpriseName.size(); j++) {
					HashMap<String, Object> put = new HashMap<String, Object>();
					put.put("value", enterpriseName.get(j));
					resu.add(put);
					put = null;
					// 清除map中元素,用于下次使用
//                    for (Iterator<String> iterator = put.keySet().iterator(); iterator.hasNext(); ) {
//                        String key = iterator.next();
//                        if (key != null || key != "") {
//                            iterator.remove();
//                        }
//                    }
				}
				result.setCode(ResponseMessage.SUCCESS);
				result.setMsg("查询成功");
				result.setData(resu);
				return result;
			}
			result.setCode(ResponseMessage.SUCCESS);
			result.setMsg("查询成功");
			result.setData(enterpriseName);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseMessage.FAILE);
			result.setMsg("系统异常");
			return result;
		}
	}

	/**
	 * 加入企业:验证要加入的企业是否存在,当企业存在时用户加入企业,用户已存在于该企业时不可重复加入
	 *
	 * @param enterpriseName 企业名称
	 * @param userId         用户ID
	 * @return
	 *         <p>
	 *         { "msg": "返回信息", "status": 状态码 }
	 *         </p>
	 */
	@PostMapping(value = "/checkEnterExist")
	public String checkEnterExist(AuthUser authUser, String enterpriseName, Long userId) {
		HashMap<String, Object> hashMap = new HashMap<>();
		if (enterpriseName == null) {
			hashMap.put("status", 0);
			hashMap.put("msg", "企业名称为空,重新输入");
			return JSON.toJSONString(hashMap);
		}
		if (userId == null) {
			hashMap.put("status", 0);
			hashMap.put("msg", "用户ID为空,重新输入");
			return JSON.toJSONString(hashMap);
		}
//		int checkSta = accountUserService.checkEnterExist(enterpriseName,userId);
		int checkSta = enterpriseService.joinEnterprise(enterpriseName, userId);
		if (checkSta == 1) {
			hashMap.put("status", 1);
			hashMap.put("msg", "该企业存在,加入成功");
			return JSON.toJSONString(hashMap);
		}
		if (checkSta == 2) {
			hashMap.put("status", 2);
			hashMap.put("msg", "已在该企业中,不可重复加入");
			return JSON.toJSONString(hashMap);
		}
		hashMap.put("status", 0);
		hashMap.put("msg", "该企业不存在");

		return JSON.toJSONString(hashMap);
	}

}

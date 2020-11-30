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
package com.sailmi.enterprise.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sailmi.core.secure.AuthUser;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.system.entity.*;
import com.sailmi.system.user.entity.AccountUserEntity;
import com.sailmi.system.vo.EnterpriseVO;
import com.sailmi.enterprise.mapper.EnterpriseMapper;
import com.sailmi.enterprise.service.IEnterpriseService;
import com.sailmi.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  服务实现类
 *
 * @author sailmi
 * @since 2020-09-02
 */
@Service
public class EnterpriseServiceImpl extends BaseServiceImpl<EnterpriseMapper, Enterprise> implements IEnterpriseService {

	@Autowired
	private  ServiceEnterpriseServiceImpl serviceEnterpriseServiceImpl;

	@Override
	public IPage<EnterpriseVO> selectEnterprisePage(IPage<EnterpriseVO> page, EnterpriseVO enterprise) {
		return page.setRecords(baseMapper.selectEnterprisePage(page, enterprise));
	}

	@Override
	public List<String> enterpriServiceTreeKeys(String enterpriseId) {
		QueryWrapper<ServiceEnterprise> serviceEnterpriseQueryWrapper = new QueryWrapper<>();
		serviceEnterpriseQueryWrapper.eq("enterprise_id",enterpriseId);
		List<ServiceEnterprise> list = serviceEnterpriseServiceImpl.list(serviceEnterpriseQueryWrapper);
		return list.stream().map(servicebao->Func.toStr(servicebao.getServiceId())).collect(Collectors.toList());
	}

	@Override
	public boolean grantservices(String enterpriseId, List<Long> serviceIds,AuthUser user) {
		//删除配置的服务包集合
		QueryWrapper<ServiceEnterprise> serviceEnterpriseQueryWrapper = new QueryWrapper<>();
		serviceEnterpriseQueryWrapper.eq("enterprise_id",enterpriseId);
		boolean remove = serviceEnterpriseServiceImpl.remove(serviceEnterpriseQueryWrapper);
		//批量插入企业的服务包
		ArrayList<ServiceEnterprise> serviceEnterprises = new ArrayList<>();
		serviceIds.stream().forEach(serviceId->{
			ServiceEnterprise serviceEnterprise = new ServiceEnterprise();
			serviceEnterprise.setServiceId(serviceId);  //服务包ID
			serviceEnterprise.setEnterpriseId(Long.valueOf(enterpriseId));//企业ID
			serviceEnterprise.setIsDeleted(0);//删除状态
			serviceEnterprise.setStatus(0);//状态
			if(user!=null && user.getUserId()!=null) {
				serviceEnterprise.setCreateUser(user.getUserId());//创建人
				serviceEnterprise.setUpdateUser(user.getUserId());//修改人
			}


			serviceEnterprise.setCreateTime(new Date());
			serviceEnterprise.setUpdateTime(new Date());
			serviceEnterprises.add(serviceEnterprise);
		});
		return serviceEnterpriseServiceImpl.saveBatch(serviceEnterprises);
	}

	@Override
	public int saveEnterpriseInfo(Enterprise enterprise) {
		enterprise.setCreateTime(new Date());//创建时间
		enterprise.setIsDeleted(0);//删除状态
		return baseMapper.insertEnterpriseInfo(enterprise);
	}

	/**
	 * <p>Description:社会统一验证码 </p>
	 *
	 * @param social:
	 * @return: java.lang.String
	 * @Author: syt
	 * @Date: 2020/10/25/0025 20:49
	 */
	@Override
	public String verifySocial(String social) {
		List<String> all = baseMapper.getAll();
		boolean con = all.contains(social);
		if (con) {
			return "1";//验证码可用
		}
		return "2";//已存在不可用
	}

	/**
	 * <p>Description: 企业模糊查找</p>
	 *
	 * @return: java.util.List<java.lang.String>
	 * @Author: syt
	 * @Date: 2020/10/30/0030 17:00
	 */
	@Override
	public List<String> enterpriseNameFuzzySearch() {
		return baseMapper.enterpriseNameFuzzySearch();
	}

	@Override
	public int joinEnterprise(String enterpriseName, Long userId) {
		Integer id = baseMapper.getId(enterpriseName);
		//用户是否已在该企业中
		int ex = baseMapper.exist(id, userId);
		if (ex > 0){
			return 2;
		}
		//用户加入企业
		int r = baseMapper.joinEnterprise(Long.valueOf(id.toString()),userId,this.timeStamp2Date(), 2);/*用户加入企业默认是普通用户2标识*/
		//更改用户的企业认证状态:暂时为用户加入之后默认已通过
		System.out.println("加入企业: " + userId);
		baseMapper.updateEnterpriseStatus(userId, 1);
		if(r > 0){
			return 1;
		}
		return 0;
	}

	private String timeStamp2Date() {
		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//要转换的时间格式
		Date date;
		try {
			date = sdf.parse(sdf.format(time));
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <p>Description: 用户企业列表</p>
	 *
	 * @param userId :
	 * @return: java.util.List<com.sailmi.system.entity.Enterprise>
	 * @Author: syt
	 * @Date: 2020/11/1/0002 15:32
	 */
	@Override
	public List<Enterprise> enterpriseList(Long userId) {
		return baseMapper.enterpriseList(userId);
	}

	public Enterprise selectEnterprise(BigInteger userId, BigInteger enterpriseId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("enterpriseId", enterpriseId);
		// 查询企业基本信息
		Enterprise enterprise = baseMapper.selectEnterprise(map);
		return enterprise;
	}

	/**
	 * <p>Description: 企业用户关系</p>
	 *
	 * @param id:
	 * @param userId:
	 * @return: void
	 * @Author: syt
	 * @Date: 2020/11/4/0004 14:37
	 */
	@Override
	public void saveUserEnterprise(Long id, Long userId) {
		int i = baseMapper.insertUserEnterprise(id, userId, timeStamp2Date(), 1);/*走创建企业接口的默认都是企业管理员用1标识*/
		//更改默认的企业id
		HashMap<Object, Object> hashMap = new HashMap<Object,Object>();
		hashMap.put("userId", userId);
		hashMap.put("lastEnterpriseId", id);
		baseMapper.updatelastEnterprise(hashMap);
		if (i > 0) {
			baseMapper.updateEnterpriseStatus(userId, 2);/*2审核中*/
		}
	}

	@Override
	public List<UserEnterpriseDepartment> getUserEnterpriseByUserId(BigInteger userId) {
		// 根据用户ID查询用户下的企业信息
		List<UserEnterpriseDepartment> userEnterpriseDepartmentList = baseMapper
			.getUserEnterpriseByUserId(userId);
		return userEnterpriseDepartmentList;
	}

	/**
	 * <p>Description: 审核</p>
	 *
	 * @param id:企业id
	 * @return: boolean
	 * @Author: syt
	 * @Date: 2020/11/10/0010 14:46
	 */
	@Override
	@Transactional
	public boolean check(String id) {
		//审核后企业状态更改
		int c = baseMapper.check(id);
		//企业状态更改完同时更改该企业创建者的企业认证状态
		List<Long> aui = baseMapper.getAdminUserIds(id);
		for (int i = 0; i < aui.size(); i++) {
			baseMapper.updateEnterpriseStatus(aui.get(i), 1);/*1已审核*/
		}
		if (c > 0) {
			return true;
		}
		return false;
	}

	@Override
	public int updateUserEnterpriseStatus(BigInteger userId, BigInteger enterpriseId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("enterpriseId", enterpriseId);
		// 用户退出企业
		int count = baseMapper.updateUserEnterpriseStatus(map);
		//用户若退出当前企业后,更改上次操作企业ID为该用户的其他企业.若用户只加入一个企业要退出时,则将上次操作企业ID更改为NULL
		List<BigInteger> allEnterprise = baseMapper.queryAllEnterprise(userId);
		if(allEnterprise.size() < 1) {
			//只加入一个企业要退出时,则将上次操作企业ID更改为0,因为涉及到ucs查询角色使用所以要改为0
			baseMapper.lastEnterpriseIsNull(userId);
			//只加入一个企业要退出时将用户的企业认证状态同时改为0未认证
			baseMapper.updateEnterpriseStatus(Long.parseLong(userId.toString()), 0);
		}else {
			//用户若退出当前企业后,更改上次操作企业ID为该用户的其他企业
			HashMap<Object, Object> hashMap = new HashMap<Object,Object>();
			hashMap.put("userId", userId);
			hashMap.put("lastEnterpriseId", allEnterprise.get(0));
			baseMapper.updatelastEnterprise(hashMap);
		}
		//企业名称
//		String enterpriseName = enterpriseMapper.getEnterpriseNameById(enterpriseId);
		//查询该企业的管理员
//		BigInteger adminId = enterpriseMapper.getAdminIdByEnterpriseId(enterpriseId);
		//用户信息
//		AccountUserEntity accUser = enterpriseMapper.getUserInfo(userId);
//		//定义发送消息的数据集
//		HashMap<Object,Object> message = new HashMap<Object,Object>();
//		//用户ID
//		message.put("userId", userId);
//		//登录名称
//		message.put("userName", accUser.getLoginName());
//		//用户手机
//		message.put("userPhone", accUser.getUserPhone());
//		//用户邮箱
//		message.put("userEmaile", accUser.getUserEmail());
//		//加入时间
//		message.put("registerDate", accUser.getRegisterDate());
//		//企业ID
//		message.put("enterpriseId", enterpriseId);
//		//管理员ID
//		message.put("adminId", adminId);
//		//企业名称
//		message.put("enterpriseName", enterpriseName);
//		createEnterpriseServiceFeign.userQuitEnterprise(JSON.toJSONString(message));
//		createEnterpriseServiceFeign.userQuitEnterprise("{\"userId\":\"" +userId+ "\",\"enterpriseId\":\"" +enterpriseId+ "\",\"enterpriseName\":\"" +enterprise.getEnterpriseName()+ "\"}");
		return count;
	}
}

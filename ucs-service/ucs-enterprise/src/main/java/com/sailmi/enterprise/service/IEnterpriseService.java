/**
 * Copyright (c) 2018-2028.
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
package com.sailmi.enterprise.service;

import com.sailmi.core.secure.AuthUser;
import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.entity.UserEnterpriseDepartment;
import com.sailmi.system.vo.EnterpriseVO;
import com.sailmi.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.math.BigInteger;
import java.util.List;

/**
 *  服务类
 *
 * @author sailmi
 * @since 2020-09-02
 */
public interface IEnterpriseService extends BaseService<Enterprise> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param enterprise
	 * @return
	 */
	IPage<EnterpriseVO> selectEnterprisePage(IPage<EnterpriseVO> page, EnterpriseVO enterprise);


	List<String> enterpriServiceTreeKeys(String enterpriseId);

	boolean grantservices(String enterpriseId, List<Long> serviceIds, AuthUser user);

	int saveEnterpriseInfo(Enterprise enterprise);

	/**
	 * <p>Description:社会统一验证码 </p>
	 *
	 * @param social:
	 * @return: java.lang.String
	 * @Author: syt
	 * @Date: 2020/10/25/0025 20:22
	 */
	String verifySocial(String social);

	/**
	 * <p>Description: 企业名称模糊查找</p>
	 *
	 * @return: java.util.List<java.lang.String>
	 * @Author: syt
	 * @Date: 2020/10/30/0030 14:08
	 */
	List<String> enterpriseNameFuzzySearch();

	/**
	 * <p>Description: 加入企业</p>
	 *
	 * @param enterpriseName:
	 * @param userId:
	 * @return: int
	 * @Author: syt
	 * @Date: 2020/10/30/0030 16:53
	 */
	int joinEnterprise(String enterpriseName, Long userId);

	/**
	 * <p>Description: 用户企业列表</p>
	 *
	 * @param userId:
	 * @return: java.util.List<com.sailmi.system.entity.Enterprise>
	 * @Author: syt
	 * @Date: 2020/11/1/0002 15:32
	 */
	List<Enterprise> enterpriseList(Long userId);

	Enterprise selectEnterprise(BigInteger userId, BigInteger enterpriseId);

	void saveUserEnterprise(Long id, Long userId);

	/**
	 * 查询用户下的企业信息
	 *
	 * @return
	 */
	List<UserEnterpriseDepartment> getUserEnterpriseByUserId(BigInteger userId);


	boolean check(String id);

	/**
	 * 用户退出企业
	 *
	 * @param enterpriseId
	 * @param userId
	 * @return
	 */
	int updateUserEnterpriseStatus(BigInteger userId, BigInteger enterpriseId);

}

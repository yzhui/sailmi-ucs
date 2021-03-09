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
package com.sailmi.enterprise.mapper;

import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.entity.UserEnterpriseDepartment;
import com.sailmi.system.vo.EnterpriseVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Mapper 接口
 *
 * @author sailmi
 * @since 2020-09-02
 */
public interface EnterpriseMapper extends BaseMapper<Enterprise> {


	 int insertEnterpriseInfo(Enterprise enterprise);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param enterprise
	 * @return
	 */
	List<EnterpriseVO> selectEnterprisePage(IPage page, EnterpriseVO enterprise);

	/**
	 * <p>Description: 社会统一验证码</p>
	 *
	 * @return: java.util.List<java.lang.String>
	 * @Author: syt
	 * @Date: 2020/10/25/0025 21:39
	 */
	List<String> getAll();
	/**
	 * <p>Description: 企业名称模糊查找</p>
	 *
	 * @return: java.util.List<java.lang.String>
	 * @Author: syt
	 * @Date: 2020/10/30/0030 14:11
	 */
	List<String> enterpriseNameFuzzySearch();


	/**
	 * <p>Description: 获取企业id</p>
	 *
	 * @param enterpriseName:
	 * @return: java.math.BigInteger
	 * @Author: syt
	 * @Date: 2020/10/30/0030 17:11
	 */
	Integer getId(String enterpriseName);

	/**
	 * <p>Description: 加入企业 </p>
	 *
	 * @param id:
	 * @param userId:
	 * @return: int
	 * @Author: syt
	 * @Date: 2020/10/30/0030 17:41
	 */
	int joinEnterprise(@Param("id") Long id, @Param("userId") Long userId,@Param("time") String time,@Param("status") int status);

	/**
	 * <p>Description: 用户的企业列表</p>
	 *
	 * @Author: syt
	 * @Date: 2020/11/1/0002 15:52
	 */
	List<Enterprise> enterpriseList(Long userId);

	/**
	 * <p>Description: 是否存在</p>
	 *
	 * @param id:
	 * @param userId:
	 * @return: int
	 * @Author: syt
	 * @Date: 2020/11/4/0004 9:15
	 */
	int exist(@Param("id") Integer id, @Param("userId") Long userId);

	/**
	 * 根据用户ID查询企业基本信息
	 *
	 * @param map
	 * @return
	 */
	Enterprise selectEnterprise(Map<String, Object> map);

	/**
	 * <p>Description:企业用户关系 </p>
	 *
	 * @param id:
	 * @param userId:
	 * @return: int
	 * @Author: syt
	 * @Date: 2020/11/4/0004 14:39
	 */
	int insertUserEnterprise(@Param("id") Long id, @Param("userId") Long userId,@Param("time") String time,@Param("status") int status);

	/**
	 * 根据用户ID查询用户下的企业信息
	 *
	 * @param userId
	 * @return
	 */
	List<UserEnterpriseDepartment> getUserEnterpriseByUserId(BigInteger userId);

	/**
	 *审核
	 */
	int check(String id);

	/**
	 * 用户创建企业后修改企业认证状态
	 *
	 * @Author: syt
	 * @Date: 2020/11/17/0017 10:25
	 */
	void updateEnterpriseStatus(@Param("userId") Long userId,@Param("entStatus") int entStatus);

	/**
	 * <p>Description: 查找企业中的管理员</p>
	 *
	 * @param id:
	 * @return: java.util.List<java.lang.Long>
	 * @Author: syt
	 * @Date: 2020/11/17/0017 17:12
	 */
	List<Long> getAdminUserIds(String id);

	/**
	 * 用户退出企业
	 *
	 * @param map
	 * @return
	 */
	int updateUserEnterpriseStatus(Map<String, Object> map);

	/**
	 * 用户若退出当前企业后,更改上次操作企业ID为该用户的其他企业.若用户只加入一个企业,则将上次操作企业ID更改为NULL
	 * 查询用户的企业
	 * @param userId 用户ID
	 * @return
	 */
	List<BigInteger> queryAllEnterprise(BigInteger userId);

	/**
	 * 只加入一个企业要退出时,则将上次操作企业ID更改为NULL
	 * @param userId
	 */
	void lastEnterpriseIsNull(BigInteger userId);

	/**
	 * 用户若退出当前企业后,更改上次操作企业ID为该用户的其他企业
	 * @param hashMap
	 */
	void updatelastEnterprise(HashMap<Object, Object> hashMap);

}

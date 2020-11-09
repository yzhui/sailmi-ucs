package com.sailmi.enterprise.service;

import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.entity.EnterpriseDetails;
import com.sailmi.system.entity.EnterpriseFinance;
import com.sailmi.system.entity.UserEnterpriseDepartment;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 企业管理接口类
 *
 * @author hdd
 * @date 2019/07/25
 */
public interface EnterpriseService {

	/**
	 * 添加企业信息
	 *
	 * @param enterprise        企业基本信息表
	 * @param enterpriseFinance 企业详细信息表
	 * @param enterpriseDetails 企业财务信息表
	 * @return
	 */
	Map<String, Object> insertEnterprise(BigInteger userId, Enterprise enterprise, EnterpriseDetails enterpriseDetails,
										 EnterpriseFinance enterpriseFinance);

	/**
	 * 根据用户ID查询企业名称
	 *
	 * @param enterpriseId
	 *
	 * @return
	 */
	Enterprise selectEnterprise(BigInteger userId, BigInteger enterpriseId);

	/**
	 * 根据用户ID查询企业信息
	 *
	 * @return
	 */
	List<Object> selectEnterpriseMsg(BigInteger userId, BigInteger enterpriseId);

	/**
	 * 根据企业ID查询企业财务信息
	 *
	 * @param enterpriseId
	 * @return
	 */
	EnterpriseFinance selectEnterpriseFinanceByEnterpriseId(BigInteger enterpriseId);

	/**
	 * 根据企业ID修改企业基本信息
	 *
	 * @param enterprise
	 * @return
	 */
	int updateEnterpriseByEnterpriseId(Enterprise enterprise);

	/**
	 * 根据企业ID修改企业详细信息
	 *
	 * @param enterpriseDetails
	 * @return
	 */
	int updateEnterpriseDetailsByEnterpriseId(EnterpriseDetails enterpriseDetails);

	/**
	 * 根据企业ID修改企业财务信息
	 *
	 * @param enterpriseFinance
	 * @return
	 */
	int updateEnterpriseFinanceByEnterpriseId(EnterpriseFinance enterpriseFinance);

	/**
	 * 查询用户下的企业信息
	 *
	 * @return
	 */
	List<UserEnterpriseDepartment> getUserEnterpriseByUserId(BigInteger userId);

	/**
	 * 校验企业社会信用统一代码
	 *
	 * @return
	 */
	int checkEnterpriseCreditCode(String checkEnterpriseCreditCode);

	/**
	 * 用户退出企业
	 *
	 * @param enterpriseId
	 * @param userId
	 * @return
	 */
	int updateUserEnterpriseStatus(BigInteger userId, BigInteger enterpriseId);

	/**
	 * 查询企业数量
	 *
	 * @return
	 */
	int getEquipmentCount();

}

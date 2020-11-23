package com.sailmi.system.user.service;

import com.sailmi.core.secure.AuthUser;
import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.user.entity.AccountUserEntity;
import com.sailmi.system.user.entity.LastEnterpriseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

/**
 * 用户信息接口
 */
public interface AccountUserService {

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    public AccountUserEntity get(BigInteger id);

    /**
     * 获取用户信息列表
     *
     * @param accountUser
     * @return
     */
//    public List<AccountUserEntity> query(AccountUserEntity accountUser);

    /**
     * 增加修改用户信息
     *
     * @param accountUser
     * @return status
     */
    public int upsert(AccountUserEntity accountUser) ;

    /**
     * 删除用户信息
     *
     * @param accountUser
     * @return status
     */
//    public int delete(AccountUserEntity accountUser);

    /**
     * 验证手机唯一
     *
     * @param phoneNum
     * @return status
     */
    public int phone(String phoneNum);

	/**
	 * 用户编辑
	 *
	 */
	String userUpdate(AuthUser authUser, AccountUserEntity accountUser);

	/**
     * 企业
     *
     * @return
     */
    public List<Enterprise> getEnterpriseName(BigInteger accountUser);

//    /**
//     * 用户在对应企业中的启用状态
//     */
//    public int isOpen(BigInteger userId, BigInteger enterpriseId, String isOpen);
//
//    /**
//     * 分页
//     *
//     * @param accountUser
//     * @return
//     */
//    public Page<AccountUserEntity> page(int num, int size, AccountUserEntity accountUser);
//
//    /**
//     * 验证成员名称唯一
//     *
//     * @param loginName
//     */
//    public int checkLoginName(String loginName);
//
//    /**
//     * 测试平台用数据
//     *
//     * @param enterId
//     * @return
//     */
//    public String treeData(BigInteger enterId)  throws Exception;
//
//    /**
//     * 统计用户总数
//     *
//     * @return
//     */
//    public int countUser();
//
//    /**
//     * 将相应用户信息添加到测试平台
//     *
//     * @param userId
//     * @return
//     */
//    public List<UserAddToCloudPlatEntity> toCloud(String userId, BigInteger enterpriseId);
//
//    /**
//     * 记录上次操作企业ID
//     *
//     * @param enterpriseId
//     */
    public int lastEnterpriseId(BigInteger enterpriseId, BigInteger userId);
//
//    /**
//     * 查询上次操作企业信息
//     *
//     * @param id 用户ID
//     * @return List<LastEnterpriseEntity> 上次操作企业信息
//     */
    public List<LastEnterpriseEntity> getLastEnterprise(BigInteger id);
//
//    /**
//     * 判断企业是否存在
//     *
//     * @param enterpriseName 企业名称
//     * @return
//     */
//    public int checkEnterExist(String enterpriseName, BigInteger userId);
//
//    public List<String> queryUserRoleType(BigInteger userId, BigInteger companyId);
//
//    /**
//     * 用户在测试平台被删除后状态置为未被添加
//     *
//     * @param id
//     */
//    public void exitsOff(BigInteger id, BigInteger enterpriseId);
//
//    /**
//     * 用户加入企业的消息
//     *
//     * @param enterpriseName
//     * @return
//     */
//    public int joinEnterpriseMsgToAdmin(String enterpriseName, BigInteger userId);
//
//    /**
//     * 管理员审核用户加入企业
//     *
//     * @param userId
//     * @param enterpriseId
//     * @return
//     */
//    public Result adminCheck(BigInteger userId, BigInteger enterpriseId, Integer adminRes, String adminId);
//
//    /**
//     * 管理员批量删除用户
//     *
//     * @param userIds
//     * @param enterpriseId
//     * @return
//     */
//    public Result adminDelUser(String userIds, BigInteger enterpriseId, String adminId);
//
//    /**
//     * 管理员批量导入成员
//     *
//     * @param
//     * @return
//     */
//    public int insertBatch(MultipartFile file, BigInteger enterpriseId);
//
//    /**
//     * 企业名称牧户查找
//     *
//     * @param
//     * @return <p>List<String></p>
//     */
//    public List<String> enterpriseNameFuzzySearch();
//	/**
//	 * 查询企业列表
//	 * @param userId
//	 */
//	public List<AccountUserEntity> queryApproveList(String userId);

}

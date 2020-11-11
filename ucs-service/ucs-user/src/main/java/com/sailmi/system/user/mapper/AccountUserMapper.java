package com.sailmi.system.user.mapper;

//import com.linose.accountuser.entity.TreeEntityPOJO;
//import com.linose.common.entity.*;
import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.user.entity.AccountUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Mapper
public interface AccountUserMapper {

    /**
     * 查询用户信息列表
     */
    AccountUserEntity get(BigInteger id);

    /**
     * 查询用户信息列表
     */
//    List<AccountUserEntity> query(AccountUserEntity userEntity);

    /**
     * 修改用户信息
     *
     * @param acUser
     * @return
     */
    int update(AccountUserEntity acUser);

    /**
     * 插入用户信息
     *
     * @param acUser
     * @return
     */
    int insert(AccountUserEntity acUser);

    /**
     * 删除用户信息
     *
     * @param acUser
     * @return
     */
//    int delete(AccountUserEntity acUser);

    /**
     * 验证手机唯一
     *
     * @return
     */
    List<String> selectAllPhone();

    /**
     * 返回企业
     *
     * @return
     */
//    List<Enterprise> getEnterpriseName(BigInteger id);

    /**
     * 用户在对应企业中的启用状态
     */
//    int updateIsOpen(@Param("userId") BigInteger userId, @Param("enterpriseId") BigInteger enterpriseId, @Param("isOpen") String isOpen);

    /**
     * 插入用户企业中间表
     *
     * @param enterpriseId
     * @param userId
     */
    int insertRelation(@Param("enterpriseId") BigInteger enterpriseId, @Param("userId") BigInteger userId, @Param("createTime") String createTime, @Param("departmentId") BigInteger departmentId);

    /**
     * 验证手机唯一
     *
     * @return
     */
//    List<String> selectAllLoginName();

    /**
     * 获取用户所在部门名称
     *
     * @param userId
     * @return
     */
//    String selDepName(@Param("userId") BigInteger userId, @Param("enterpriseId") BigInteger enterpriseId);

    /**
     * 用户状态
     */
//    String getUseIsOpen(@Param("userId") BigInteger userId, @Param("enterpriseId") BigInteger enterpriseId);

    /**
     * 删除用户企业关系表
     *
     * @param acUser 用户实体类
     * @return
     */
//    int deleteRelation(AccountUserEntity acUser);

    /**
     * 修改用户信息完整状态
     *
     * @param id 用户ID
     */
    int updateSta(BigInteger id);

    /**
     * 企业部门人员树
     *
     * @param enterId 企业ID
     * @return 返回给测试平台tree结构
     */
//    List<TreeEntityPOJO> getEnterInfo(BigInteger enterId);

    //部门
//    List<TreeEntityPOJO> queryDepartList(BigInteger id);

//    人员
//    List<TreeEntityPOJO> queryUserList(BigInteger id);

    /**
     * 总用户
     *
     * @return
     */
//    int getCount();

    /**
     * 相应用户添加到测试平台
     */
//    List<UserAddToCloudPlatEntity> toCloud(@Param("list") List<BigInteger> list, @Param("enterpriseId") BigInteger enterpriseId);

    /**
     * 用户在该企业的测试中心是否添加
     *
     * @param list
     * @param enterpriseId
     * @return
     */
//    void exits(@Param("list") List<BigInteger> list, @Param("enterpriseId") BigInteger enterpriseId);


    /**
     * 编辑用户信息同时修改关系表数据
     *
     * @param id           用户ID
     * @param enterpriseId 企业ID
     * @param departmentId 部门ID
     */
    int updateRealtion(@Param("id") BigInteger id, @Param("enterpriseId") BigInteger enterpriseId, @Param("departmentId") BigInteger departmentId);

    /**
     * 统计部门人数
     *
     * @param enterpriseId 企业ID
     * @param departmentId 部门ID
     * @return 部门下总人数
     */
    int countDepMemberNum(@Param("enterpriseId") BigInteger enterpriseId, @Param("departmentId") BigInteger departmentId);

    /**
     * 修改部门人数
     *
     * @param memberNumber 部门总人数
     * @param id           部门ID
     */
    int updateDepMemberNum(@Param("memberNumber") int memberNumber, @Param("id") BigInteger id);

    /**
     * 部门ID
     *
     * @param id           用户ID
     * @param enterpriseId 企业ID
     * @return
     */
//    BigInteger getDepId(@Param("id") BigInteger id, @Param("enterpriseId") BigInteger enterpriseId);

    /**
     * 记录上次操作企业ID
     *
     * @param enterpriseId 企业ID
     */
//    int lastEnterpriseId(@Param("enterpriseId") BigInteger enterpriseId, @Param("userId") BigInteger userId);

    /**
     * 查询上次操作企业信息
     *
     * @param id 用户ID
     * @return List<LastEnterpriseEntity> 上次操作企业信息
     */
//    List<LastEnterpriseEntity> getLastEnter(@Param("id") BigInteger id);

    /**
     * 判断企业是否存在
     *
     * @param enterpriseName 企业名称
     * @return
     */
//    String checkEnterExist(String enterpriseName);

    /**
     * 用户加入企业,默认状态为已审核
     *
     * @param enterpriseId 企业ID
     * @param userId       用户ID
     * @param createTime   加入时间
     * @return
     */
//    int personJoinEnterprise(@Param("enterpriseId") BigInteger enterpriseId, @Param("createTime") String createTime, @Param("userId") BigInteger userId);

    /**
     * 查找企业ID
     *
     * @param enterpriseName
     * @return
     */
//    BigInteger getEnterpriseId(String enterpriseName);

    /**
     * 验证用户是否已经存在于该企业
     *
     * @param enterpriseId
     * @param userId
     * @return
     */
//    int checkUserIsExistEnterprise(@Param("enterpriseId") BigInteger enterpriseId, @Param("userId") BigInteger userId);

    /**
     * 更改用户角色
     *
     * @param enterpriseId
     * @param userId
     */
//    void updateUserRole(@Param("enterpriseId") BigInteger enterpriseId, @Param("userId") BigInteger userId);

//    Integer queryEnterUser(String userId);

    /**
     * 用户角色
     *
     * @param enterpriseId
     * @param userId
     */
//    void insertUserRole(@Param("enterpriseId") BigInteger enterpriseId, @Param("userId") BigInteger userId);

//    List<String> queryUserRoleList(@Param("userId") BigInteger userId, @Param("companyId") BigInteger companyId);

    /**
     * 用户在测试平台被删除后状态置为未被添加
     *
     * @param id
     */
//    void exitsOff(@Param("id") BigInteger id, @Param("enterpriseId") BigInteger enterpriseId);

    /**
     * 验证企业是否存在(结合消息系统)
     *
     * @param enterpriseName
     * @return
     */
//    List<Enterprise> checkEnteriseExist(String enterpriseName);

    /**
     * 用户是否存在该企业内
     *
     * @param hashMap
     * @return
     */
    int userExitsEnterprise(HashMap<Object, Object> hashMap);

    /**
     * 查看用户在该企业状态是否冻结
     *
     * @param hashMap
     * @return
     */
//    String freeze(HashMap<Object, Object> hashMap);

    /**
     * 解冻被冻结的用户
     *
     * @param hashMap
     */
//    void unFreeze(HashMap<Object, Object> hashMap);

    /**
     * 新用户加入企业
     *
     * @param hashMap
     */
//    void adminCheckJoin(HashMap<Object, Object> hashMap);

    /**
     * 企业名称
     *
     * @param enterpriseId
     * @return
     */
    String getEnterpriseNameById(BigInteger enterpriseId);

    /**
     * 管理员批量删除用户
     *
     * @param list
     * @param enterpriseId
     */
//    void adminDelUser(@Param("list") ArrayList<Integer> list, @Param("enterpriseId") BigInteger enterpriseId);

    /**
     * 多管理员管理员IDs
     *
     * @param enterpriseId
     * @return
     */
//    List<BigInteger> getAdminByEnterpriseId(BigInteger enterpriseId);

    /**
     * 用户最后操作企业ID置为空
     *
     * @param id
     */
//    void lastEnterpriseIdSetZero(BigInteger id);


    /**
     * 管理员批量导入成员
     *
     * @param accountUser
     */
//    void insertBatch(AccountUserEntity accountUser);

    /**
     * 用户上次操作企业是否为空
     *
     * @param userId
     * @return
     */
//    BigInteger lastEnterpriseIdIsEmpty(BigInteger userId);

    /**
     * 企业名称牧户查找
     * 企业名称关键字
     *
     * @return <p>List<String></p>
     */
//    List<String> enterpriseNameFuzzySearch();

	/**
	 * 验证邮箱唯一
	 * @param email
	 * @return
	 */
	int selectAllEmail(@Param("email") String email);

	/**
	 * 通过手机或者邮箱查找用户ID
	 * @param userPhoneOrEmail
	 * @return id 用户ID
	 */
	BigInteger getIdByUserPhoneOrEmail(@Param("userPhoneOrEmail") String userPhoneOrEmail);

	/**
	 * <p>Description: 用户编辑</p>
	 *
	 * @param accountUser:
	 * @return: int
	 * @Author: syt
	 * @Date: 2020/11/9/0009 17:42
	 */
	int userUpdate(AccountUserEntity accountUser);

	/**
	 * 管理员同意用户加入企业时防止重复添加
	 * @param userId		用户ID
//	 * @param enterpriseId	企业ID
	 * @return				Int
	 */
//	int userIsExitEnterpriseByUserIdAndEnterpriseId(@Param("userId") BigInteger userId, @Param("enterpriseId") BigInteger enterpriseId);

//	String queryEnterByUserId(String userId);

//	List<AccountUserEntity> queryApprovedList(String enterpriseId);

	/**
	 * 待审核用户信息插入待审核表
	 * @param enterpriseId	企业ID
	 * @param userId		申请人ID
	 * @param date			时间
	 */
//	void insertAuthstr(@Param("enterpriseId") BigInteger enterpriseId, @Param("userId") BigInteger userId, @Param("date") String date);

	/**
	 * 管理员审核用户是否加入企业,审核完毕删除该条记录
	 * @param userId		申请人ID
	 * @param enterpriseId	企业ID
	 */
//	void delApplyInfo(@Param("userId") BigInteger userId, @Param("enterpriseId") BigInteger enterpriseId);
}

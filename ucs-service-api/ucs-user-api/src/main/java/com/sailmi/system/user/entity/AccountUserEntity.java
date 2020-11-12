package com.sailmi.system.user.entity;

import com.sailmi.core.mp.base.BaseEntity;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Date;

/**
 * 统一账户管理用户实体类
 *
 * @author syt
 */
public class AccountUserEntity extends BaseEntity {

	/**
	 * 用户ID
	 */
	private BigInteger id;

	/**
	 * 用户昵称
	 */
	private String nickName;

	private String username="";

	/**
	 * 登录名称
	 */
	private String loginName="";

	/**
	 * 用户密码
	 */
	private String password="";

	/**
	 * 用户手机
	 */
	private String userPhone="";


	/**
	 * 用户邮箱
	 */
	private String userEmail="";

	/**
	 * 用户性别:0保密，1男，2女
	 */
	private String userSex="";

	/**
	 * 用户生日
	 */
	private Date userBrithday;

	/**
	 * 实名认证状态:1已认证，0未认证
	 */
	private String realStatus;

	/**
	 * 注册(添加)时间
	 */
	private String registerDate;

	/**
	 * 企业认证状态:1已认证，0未认证
	 */
	private String enterpriseStatus;

	/**
	 * 描述
	 */
	private String remarks="";

	/**
	 * 手机绑定状态
	 */
	private String phoneSta;

	/**
	 * 邮箱绑定状态
	 */
	private String emailSta;

	/**
	 * 账户信息绑定状态
	 */
	private String infoSta;

	/**
	 * 姓名
	 */
	private String realName="";
	/**
	 *企业ID
	 * @return
	 */
	@NotNull(message = "企业ID不可为空")
	private BigInteger enterpriseId;

	/**
	 * 最后登录时间
	 * @return
	 */
	private String lastLogin="";

	/**
	 * 部门ID
	 * @return
	 */
	private BigInteger departmentId;

	/**
	 * 部门名称
	 * @return
	 */
	private String departmentName="";

	/**
	 * 启用
	 * @return
	 */
	private String isOpen="";

	/**
	 * 模糊搜索
	 * @return
	 */
	private String search;

	/**
	 * 安全凭证
	 */
	private String safeCode;

	/**
	 * 最后操作企业的ID
	 */
	private BigInteger lastEnterpriseId;

	/**
	 * 通知方式:1短信通知,2邮件通知,3邮件+短信通知
	 */
	private int inform;

	/**
	 * 用户头像
	 */
	private String headImgUrl;

	/**
	 * 申请时间
	 */
	private String applyTime;



	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public BigInteger getLastEnterpriseId() {
		return lastEnterpriseId;
	}

	public void setLastEnterpriseId(BigInteger lastEnterpriseId) {
		this.lastEnterpriseId = lastEnterpriseId;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public Date getUserBrithday() {
		return userBrithday;
	}

	public void setUserBrithday(Date userBrithday) {
		this.userBrithday = userBrithday;
	}

	public String getRealStatus() {
		return realStatus;
	}

	public void setRealStatus(String realStatus) {
		this.realStatus = realStatus;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getEnterpriseStatus() {
		return enterpriseStatus;
	}

	public void setEnterpriseStatus(String enterpriseStatus) {
		this.enterpriseStatus = enterpriseStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPhoneSta() {
		return phoneSta;
	}

	public void setPhoneSta(String phoneSta) {
		this.phoneSta = phoneSta;
	}

	public String getEmailSta() {
		return emailSta;
	}

	public void setEmailSta(String emailSta) {
		this.emailSta = emailSta;
	}

	public String getInfoSta() {
		return infoSta;
	}

	public void setInfoSta(String infoSta) {
		this.infoSta = infoSta;
	}


	public BigInteger getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(BigInteger enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public BigInteger getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(BigInteger departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getSafeCode() {
		return safeCode;
	}

	public void setSafeCode(String safeCode) {
		this.safeCode = safeCode;
	}

	public int getInform() {
		return inform;
	}

	public void setInform(int inform) {
		this.inform = inform;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}

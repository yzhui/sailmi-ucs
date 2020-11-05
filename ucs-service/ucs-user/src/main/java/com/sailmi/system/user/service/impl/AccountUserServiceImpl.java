package com.sailmi.system.user.service.impl;

import com.alibaba.fastjson.JSON;
//import com.linose.accountuser.entity.TreeEntityPOJO;
//import com.linose.accountuser.feign.JoinEnterpriseServiceFeign;
//import com.linose.accountuser.mapper.AccountUserMapper;
//import com.linose.accountuser.sendmail.MailService;
//import com.linose.accountuser.service.AccountUserService;
//import com.linose.accountuser.util.PhoneCodeUtil;
//import com.linose.common.entity.*;
//import com.linose.common.util.MD5Util;
//import com.linose.common.util.Page;
//import com.linose.common.util.ResponseMessage;
//import com.linose.common.util.Result;
import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.user.entity.AccountUserEntity;
import com.sailmi.system.user.mapper.AccountUserMapper;
import com.sailmi.system.user.service.AccountUserService;
import io.netty.util.internal.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
//import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 用户管理实现类
 *
 * @author syt
 */
@Service
public class AccountUserServiceImpl implements AccountUserService {

    private static final Log LOG = LogFactory.getLog(AccountUserServiceImpl.class);

    /**
     * 注入feign
     */
//    @Autowired
//    private JoinEnterpriseServiceFeign joinEnterpriseServiceFeign;

    /**
     * 注入MAPPER
     */
//    @Autowired(required = false)
//	@Resource
//    private AccountUserMapper accountUserMapper;

    /**
     * 邮件发送
     */
//    @Autowired
//    private MailService mailService;

    /**
     * 短信通知
     */
//    @Resource
//    private PhoneCodeUtil PhoneCodeUtil;

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return 根据ID获取用户信息
     */
//    @Override
//    public AccountUserEntity get(BigInteger id) {
//    	AccountUserEntity aue = accountUserMapper.get(id);
//    	//判断手机与邮箱的认证状态:0未认证,1已认证
//    	if(!StringUtil.isNullOrEmpty(aue.getUserPhone())) {
//    		aue.setPhoneSta("1");
//    	}
//    	if(!StringUtil.isNullOrEmpty(aue.getUserEmail())) {
//    		aue.setEmailSta("1");
//    	}
//    	//注册时间
//    	String regDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(aue.getRegisterDate())));
//    	aue.setRegisterDate(regDate);
//        return aue;
//    }

    /**
     * 查询用户信息列表
     *
     * @param userEntity 输入参数：用户实体类
     * @return
     */
//    @Override
//    public List<AccountUserEntity> query(AccountUserEntity userEntity) {
//        List<AccountUserEntity> query = accountUserMapper.query(userEntity);
//        // 部门名称
//        for (int i = 0; i < query.size(); i++) {
//            query.get(i).setDepartmentName(
//                    accountUserMapper.selDepName(query.get(i).getId(), userEntity.getEnterpriseId()));
//        }
//        // 用户在该企业下的启用状态
//        for (int i = 0; i < query.size(); i++) {
//            String useIsOpen = accountUserMapper.getUseIsOpen(query.get(i).getId(), userEntity.getEnterpriseId());
//            query.get(i).setIsOpen(useIsOpen);
//        }
//        return query;
//    }
//
//    /**
//     * 修改、增加用户信息
//     *
//     * @param acUser 输入参数：用户信息实体类
//     * @return 返回值：1成功，0失败
//     */
//    @Override
//    public int upsert(AccountUserEntity acUser) {
//        int status = 0, insertRelation = 0, depTotalMemberNum = 0, phoneExis = -1,emailExis = -1, iExit = 0;
//        //当前时间
//        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime());
//        //企业名称
//        String enterName = accountUserMapper.getEnterpriseNameById(acUser.getEnterpriseId());
//        //密码
//        String password = acUser.getPassword();
//        // 修改用户信息或部门
//        if (acUser.getId() != null) {
//            status = accountUserMapper.update(acUser);
//            if (status == 1) {
//                boolean info = (acUser.getLoginName()) != null;
//                if (info) {
//                    // 将用户信息状态修改为已完善
//                    accountUserMapper.updateSta(acUser.getId());
//                }
//                if (acUser.getEnterpriseId() != null & acUser.getDepartmentId() != null) {
//                    // 修改用户部门
//                    accountUserMapper.updateRealtion(acUser.getId(), acUser.getEnterpriseId(),
//                            acUser.getDepartmentId());
//                    // 更新部门人数
//                    depTotalMemberNum = accountUserMapper.countDepMemberNum(acUser.getEnterpriseId(),
//                            acUser.getDepartmentId());
//                    accountUserMapper.updateDepMemberNum(depTotalMemberNum, acUser.getDepartmentId());
//
//                }
//                return 1;
//            }
//            // 修改失败
//            return 0;
//        }
//        // 新加入用户
//        try {
//        	BigInteger userId = null;
//        	//判断账号是否存在:验证手机号和邮箱,账号存在直接加入企业
//        	if(acUser.getUserPhone() != null) {
//        		phoneExis = this.phone(acUser.getUserPhone());
//        		//获取用户ID
//        		userId = accountUserMapper.getIdByUserPhoneOrEmail(acUser.getUserPhone());
//        	}else if(acUser.getUserEmail() != null) {
//        		emailExis = this.emailOnlyOne(acUser.getUserEmail());
//        		//获取用户ID
//        		userId = accountUserMapper.getIdByUserPhoneOrEmail(acUser.getUserEmail());
//        	}
//        	//账号存在:直接将用户加入企业,0存在
//        	if(phoneExis == 0 || emailExis == 0) {
//        		//判断该用户是否已经存在该企业中
//        		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
//                hashMap.put("userId", userId);
//                hashMap.put("enterpriseId", acUser.getEnterpriseId());
//                iExit = accountUserMapper.userExitsEnterprise(hashMap);
//        		if(iExit > 0) {
//        			//用户已经加入过该企业
//        			return 2;
//        		}
//        		// 插入用户企业关系表
//                insertRelation = accountUserMapper.insertRelation(acUser.getEnterpriseId(), userId, createTime,
//                        (acUser.getDepartmentId() == null) ? null : acUser.getDepartmentId());
//                // 更新部门人数
//                depTotalMemberNum = accountUserMapper.countDepMemberNum(acUser.getEnterpriseId(), acUser.getDepartmentId());
//                accountUserMapper.updateDepMemberNum(depTotalMemberNum, acUser.getDepartmentId());
//        		//写入用户角色
//        		accountUserMapper.insertUserRole(acUser.getEnterpriseId(), userId);
//        		//给用户发短信通知
//        		 try {
//                     if (acUser.getUserPhone() != null || acUser.getUserPhone() != "") {
//                         PhoneCodeUtil.sendCodeMsg(acUser.getUserPhone(), enterName, acUser.getLoginName(), password);
//                     }
//                 } catch (Exception e) {
//                     LOG.error("发送短信通知异常:" + e, e);
//                 }
//        		//给用户发送邮件通知
//        		 try {
//        			 if (acUser.getUserEmail() != null || acUser.getUserEmail() != "") {
//        				 this.sendEmail(acUser.getUserEmail(), enterName, acUser.getLoginName(), password);
//        			 }
//        		 }catch(Exception e) {
//        			 LOG.error("发送邮件异常:" + e, e);
//        		 }
//        		 if (insertRelation == 1) {
//                     return 1;
//                 }
//        		 return 0;
//        	}
//
//
//
//            // 注册时间
//            acUser.setRegisterDate(String.valueOf(new Date().getTime()));
//            // 安全凭证
//            acUser.setSafeCode(MD5Util.MD5Code(acUser.getPassword() + acUser.getLoginName()));
//            // 密码加密
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            String hashPass = passwordEncoder.encode(acUser.getPassword());
//            // 加密后存入
//            acUser.setPassword(hashPass);
//            // 管理员添加成员后更改该成员的最后操作企业ID记录
//            BigInteger lastEnterpriseId = acUser.getEnterpriseId();
//            acUser.setLastEnterpriseId(lastEnterpriseId);
//            // 管理员添加成员后更改该成员的企业认证状态
//            String enterSta = null;
//            if (acUser.getEnterpriseId() != null) {
//            	enterSta = "1";
//                acUser.setEnterpriseStatus(enterSta);
//            } else {
//                enterSta = "0";
//                acUser.setEnterpriseStatus(enterSta);
//            }
//
//            //企业名称
//            if (acUser.getInform() == 1 || acUser.getInform() == 3) {
//                //判断loginName是否为空,为空则用手机号作为longinName
//                if (acUser.getLoginName() == null || acUser.getLoginName() == "") {
//                    acUser.setLoginName(acUser.getUserPhone());
//                }
//                try {
//                    //给用户发短信通知
//                    if (acUser.getUserPhone() != null || acUser.getUserPhone() != "") {
//                        PhoneCodeUtil.sendCodeMsg(acUser.getUserPhone(), enterName, acUser.getLoginName(), password);
//                    }
//                } catch (Exception e) {
//                    LOG.error("发送短信通知异常,e", e);
//                }
//            }
//            if (acUser.getInform() == 2 || acUser.getInform() == 3) {
//                //判断loginName是否为空,为空则用邮箱作为longinName
//                if (acUser.getLoginName() == null || acUser.getLoginName() == "") {
//                    acUser.setLoginName(acUser.getUserEmail());
//                }
//                //给用户发送邮件通知
//                if (acUser.getUserEmail() != null || acUser.getUserEmail() != "") {
//                    this.sendEmail(acUser.getUserEmail(), enterName, acUser.getLoginName(), password);
//                }
//            }
//            if (acUser.getRealName() == null || acUser.getRealName().trim() == "") {
//                acUser.setRealName("未填写真实姓名");
//            }
//            // 信息入库
//            status = accountUserMapper.insert(acUser);
//            //用户角色
//            accountUserMapper.insertUserRole(acUser.getEnterpriseId(), acUser.getId());
//
//            // 插入用户企业关系表
//            insertRelation = accountUserMapper.insertRelation(acUser.getEnterpriseId(), acUser.getId(), createTime,
//                    (acUser.getDepartmentId() == null) ? null : acUser.getDepartmentId());
//            // 更新部门人数
//            depTotalMemberNum = accountUserMapper.countDepMemberNum(acUser.getEnterpriseId(), acUser.getDepartmentId());
//            accountUserMapper.updateDepMemberNum(depTotalMemberNum, acUser.getDepartmentId());
//
//            if (status == 1 & insertRelation == 1) {
//                return 1;
//            }
//            return 0;
//        } catch (Exception e) {
//            LOG.error("用户插入失败", e);
//            return 0;
//        }
//    }
//
//    /**
//     * 删除用户信息
//     *
//     * @param acUser 输入参数：用户实体类
//     * @return 返回值：1成功，0失败
//     */
//    @Override
//    public int delete(AccountUserEntity acUser) {
//        int delReSta;
//        //删除用户
//        delReSta = accountUserMapper.deleteRelation(acUser);
//        //将用户最后操作的企业ID置为空
//        accountUserMapper.lastEnterpriseIdSetZero(acUser.getId());
//        if (delReSta == 1) {
//            try {
//                //企业名称
//                String enterpriseName = accountUserMapper.getEnterpriseNameById(acUser.getEnterpriseId());
//                //定义发送消息数据集
//                HashMap<Object, Object> message = new HashMap<Object, Object>();
//                message.put("userIds", acUser.getId());
//                message.put("enterpriseId", acUser.getEnterpriseId());
//                message.put("enterpriseName", enterpriseName);
//                //给用户发送被删除通知
//                joinEnterpriseServiceFeign.adminDel(JSON.toJSONString(message));
//            } catch (Exception e) {
//                LOG.error("给用户发送被删除出企业消息失败", e);
//            }
//            return 1;
//        }
//        return 0;
//    }
//
//    /**
//     * 验证手机是否唯一
//     * @param phoneNum 手机号
//     * @return 1不存在,0存在
//     */
//    @Override
//    public int phone(String phoneNum) {
//        List<String> allPhone = accountUserMapper.selectAllPhone();
//        if (allPhone.size() > 0) {
//            boolean contains = allPhone.contains(phoneNum);
//            if (contains) {
//                return 0;
//            }
//            return 1;
//        }
//        return 1;
//    }
//
//    /**
//     * 验证邮箱是否唯一
//     * @param Email 邮箱
//     * @return 1不存在,0存在
//     */
//    public int emailOnlyOne(String Email) {
//    	int onlyOne = accountUserMapper.selectAllEmail(Email);
//    	if(onlyOne == 1) {
//    		//不存在
//    		return 1;
//    	}
//    	//存在
//		return 0;
//    }
    /**
     * 返回企业
     */
//    @Override
//    public List<Enterprise> getEnterpriseName(BigInteger id) {
//        return accountUserMapper.getEnterpriseName(id);
//    }

    /**
     * 用户在对应企业中的启用状态
     */
//    @Override
//    public int isOpen(BigInteger userId, BigInteger enterpriseId, String isOpen) {
//        int updateIsOpen = accountUserMapper.updateIsOpen(userId, enterpriseId, isOpen);
//        if (updateIsOpen == 1) {
//            return 1;
//        }
//        return 0;
//
//    }
//
//    /**
//     * 分页
//     */
//    @Override
//    public Page<AccountUserEntity> page(int num, int size, AccountUserEntity accountUser) {
//    	List<AccountUserEntity> query = null ;
//    	Page<AccountUserEntity> page = null;
//    	//企业ID不为空进行查询
////    	if(accountUser.getEnterpriseId() != null) {
//	        query = accountUserMapper.query(accountUser);
//	        // 部门名称
//	        for (int i = 0; i < query.size(); i++) {
//	            query.get(i).setDepartmentName(
//	                    accountUserMapper.selDepName(query.get(i).getId(), accountUser.getEnterpriseId()));
//	            query.get(i)
//	                    .setDepartmentId(accountUserMapper.getDepId(query.get(i).getId(), accountUser.getEnterpriseId()));
//	        }
//	        // 用户在该企业下的启用状态
//	        for (int i = 0; i < query.size(); i++) {
//	            String useIsOpen = accountUserMapper.getUseIsOpen(query.get(i).getId(), accountUser.getEnterpriseId());
//	            query.get(i).setIsOpen(useIsOpen);
//	        }
//	        //总条数
//	        int totalSize = query.size();
//	        page = new Page<>(num, size, totalSize);
//	        page.setList(query);
//	        return page;
////    	}
////    	//企业ID为空不查询数据
////    	page = new Page<>(1, 2, 3);
////    	query.add(accountUser);
////    	page.setList(query);
////    	return page;
//    }
//
//    /**
//     * 名称唯一
//     */
//    @Override
//    public int checkLoginName(String loginName) {
//        List<String> allLoginName = accountUserMapper.selectAllLoginName();
//        if (allLoginName.size() > 0) {
//            boolean contains = allLoginName.contains(loginName);
//            if (contains) {
//                return 0;
//            }
//            return 1;
//        }
//        return 0;
//    }
//
//    /**
//     * 测试平台用
//     */
//    @Override
//    public String treeData(BigInteger enterId) throws Exception{
//        List<TreeEntityPOJO> enterList = accountUserMapper.getEnterInfo(enterId);
//        for (int i = 0; i < enterList.size(); i++) {
//            // 遍历企业ID查找部门
//            List<TreeEntityPOJO> departList = accountUserMapper.queryDepartList(enterList.get(i).getId());
//            if (departList != null && departList.size() > 0) {
//                for (TreeEntityPOJO tree : departList) {
//                    // 遍历部门ID查找人员
//                    List<TreeEntityPOJO> userList = accountUserMapper.queryUserList(tree.getId());
//                    if (userList != null && userList.size() > 0) {
//                        // 人员存在则放入对应部门下
//                        tree.setChildren(userList);
//                    }
//                }
//            }
//            // 部门存在则放入企业
//            enterList.get(i).setChildren(departList);
//        }
//        return JSON.toJSONString(enterList);
//    }
//
//    /**
//     * 用户总数
//     */
//    @Override
//    public int countUser() {
//        int cou = accountUserMapper.getCount();
//        return cou;
//    }
//
//    /**
//     * 添加用户到测试平台
//     */
//    @Override
//    public List<UserAddToCloudPlatEntity> toCloud(String userId, BigInteger enterpriseId) {
//        String[] split = userId.split(",");
//        List<BigInteger> usrId = new ArrayList<BigInteger>();
//        for (int i = 0; i < split.length; i++) {
//            usrId.add(BigInteger.valueOf(Long.parseLong(split[i])));
//        }
//        List<UserAddToCloudPlatEntity> toCloud = accountUserMapper.toCloud(usrId, enterpriseId);
//        accountUserMapper.exits(usrId, enterpriseId);
//        for (int i = 0; i < toCloud.size(); i++) {
//            toCloud.get(i).setEnterpriseId(enterpriseId);
//        }
//        return toCloud;
//    }
//
//    /**
//     * 记录上次操作企业ID
//     */
//    @Override
//    public int lastEnterpriseId(BigInteger enterpriseId, BigInteger userId) {
//        int lastSta = accountUserMapper.lastEnterpriseId(enterpriseId, userId);
//        if (lastSta > 0) {
//            return 1;
//        }
//        return 0;
//    }
//
//    /**
//     * 查询上次操作企业信息
//     *
//     * @param id 用户ID
//     * @return List<LastEnterpriseEntity> 上次操作企业信息
//     */
//    @Override
//    public List<LastEnterpriseEntity> getLastEnterprise(BigInteger id) {
//        return accountUserMapper.getLastEnter(id);
//    }
//
//    /**
//     * 验证企业是否存在
//     *
//     * @param enterpriseName 企业名称
//     * @return int 1存在, 0不存在
//     */
//    @Override
//    public int checkEnterExist(String enterpriseName, BigInteger userId) {
//        String enterName = accountUserMapper.checkEnterExist(enterpriseName);
//        if (enterpriseName.equals(enterName)) {
//            // 企业存在,获取企业ID
//            BigInteger enterpriseId = accountUserMapper.getEnterpriseId(enterpriseName);
//            // 验证用户是否已经存在于该企业
//            int isExistEnterprise = accountUserMapper.checkUserIsExistEnterprise(enterpriseId, userId);
//            if (isExistEnterprise > 0) {
//                return 2;
//            }
//            // 加入企业
//            //this.personJoinEnterprise(enterpriseId, userId);
//            //企业名称
//            String enterpriseNameMsg = accountUserMapper.getEnterpriseNameById(enterpriseId);
//            //给管理员发送成员申请加入消息
//            return 1;
//        } else {
//            // 企业不存在,返回错误信息
//            return 0;
//        }
//    }
//
//    /**
//     * 用户加入企业,默认状态已审核 (弃用)
//     *
//     * @param enterpriseId 企业ID
//     * @param userId       用户ID
//     * @return
//     */
//    public int personJoinEnterprise(BigInteger enterpriseId, BigInteger userId) {
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String createTime = formatter.format(currentTime);
//        // 加入企业
//        int joinSta = accountUserMapper.personJoinEnterprise(enterpriseId, createTime, userId);
//
//        Integer count = accountUserMapper.queryEnterUser(userId.toString());// 判断是新创建得用户还是企业admin
//        if (count > 0) {// 说明是新注册得用户
//            accountUserMapper.updateUserRole(enterpriseId, userId);
//        } else {
//            accountUserMapper.insertUserRole(enterpriseId, userId);
//        }
//
//        // 更改最后一次操作企业记录
//        accountUserMapper.lastEnterpriseId(enterpriseId, userId);
//        if (joinSta == 1) {
//            return 1;
//        } else {
//            return 0;
//        }
//    }
//
//    @Override
//    public List<String> queryUserRoleType(BigInteger userId, BigInteger companyId) {
//
//        return accountUserMapper.queryUserRoleList(userId, companyId);
//    }
//
//    /**
//     * 用户在测试平台被删除后状态置为未被添加
//     *
//     * @param id
//     */
//    @Override
//    public void exitsOff(BigInteger id, BigInteger enterpriseId) {
//        accountUserMapper.exitsOff(id, enterpriseId);
//    }
//
//    /**
//     * 用户加入企业的消息
//     */
//    @Override
//    public int joinEnterpriseMsgToAdmin(String enterpriseName, BigInteger userId) {
//        int iExit = 0;
//        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
//        HashMap<Object, Object> message = new HashMap<Object, Object>();
//        //返回值
//        Result result = new Result();
//
//        //用户加入企业,验证企业是否存在
//        List<Enterprise> ent = accountUserMapper.checkEnteriseExist(enterpriseName);
//
//        if (ent.size() > 0) {
//            //企业存在,给每个企业发送消息(企业可能重名)
//            for (int i = 0; i < ent.size(); i++) {
//                //验证该用户是否存在该企业内
//                hashMap.put("userId", userId);
//                hashMap.put("enterpriseId", ent.get(i).getId());
//                iExit = accountUserMapper.userExitsEnterprise(hashMap);
//                if (iExit > 0) {
//                    //用户存在企业,状态是否为冻结
//                    String freeze = accountUserMapper.freeze(hashMap);
//                    //已冻结
//                    if (freeze == "0") {
////						AccountUserEntity accountUserEntity = this.get(userId);
////						String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
////						joinEnterpriseServiceFeign.joinEnterprise("{\"userId\":\"" + userId + "\",\"enterpriseId\":\"" + ent.get(i).getId() + "\",\"loginName\":\""+accountUserEntity.getLoginName()+"\",\"userPhone\":\"" +accountUserEntity.getUserPhone()+ "\",\"userEmail\":\""+accountUserEntity.getUserEmail()+"\",\"date\":\"" +date+ "\"}");
//
//                        AccountUserEntity accountUserEntity = this.get(userId);
//                        //查找企业管理员
//                        List<BigInteger> adminIds = accountUserMapper.getAdminByEnterpriseId(ent.get(i).getId());
//                        //给每个管理员发送消息
//                        for (int j = 0; j < adminIds.size(); j++) {
//                            //定义发送消息数据集
//                            //用户ID
//                            message.put("userId", userId);
//                            //企业ID
//                            message.put("enterpriseId", ent.get(i).getId());
//                            //登录名称
//                            message.put("loginName", accountUserEntity.getLoginName());
//                            //用户手机
//                            message.put("userPhone", accountUserEntity.getUserPhone());
//                            //用户邮箱
//                            message.put("userEmail", accountUserEntity.getUserEmail());
//                            //加入时间
//                            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//                            message.put("date", date);
//                            //管理员ID
//                            message.put("adminIds", adminIds.get(i));
//                            //用户不存在该企业,发送消息通知管理员
//                            joinEnterpriseServiceFeign.joinEnterprise(JSON.toJSONString(message));
//                        }
//
//                        result.setCode(ResponseMessage.SUCCESS);
//                        result.setMsg("已加入该企业但被冻结,已通知管理员");
//                        return 2;
//                    }
//                    //未冻结,已加入该企业
//                    result.setCode(ResponseMessage.SUCCESS);
//                    result.setMsg("已加入该企业");
//                    return 2;
//                } else {
//                    AccountUserEntity accountUserEntity = this.get(userId);
//                    //查找企业管理员
//                    List<BigInteger> adminIds = accountUserMapper.getAdminByEnterpriseId(ent.get(i).getId());
//                    //给每个管理员发送消息
//                    for (int j = 0; j < adminIds.size(); j++) {
//                        //定义发送消息数据集
//                        //用户ID
//                        message.put("userId", userId);
//                        //企业ID
//                        message.put("enterpriseId", ent.get(i).getId());
//                        //登录名称
//                        message.put("loginName", accountUserEntity.getLoginName());
//                        //用户手机
//                        message.put("userPhone", accountUserEntity.getUserPhone());
//                        //用户邮箱
//                        message.put("userEmail", accountUserEntity.getUserEmail());
//                        //加入时间
//                        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//                        message.put("date", date);
//                        //管理员ID
//                        message.put("adminIds", adminIds.get(i));
//                        //用户不存在该企业,发送消息通知管理员
//                        joinEnterpriseServiceFeign.joinEnterprise(JSON.toJSONString(message));
//
//                        //将用户申请数据插入待审核表中(应新需求改变:将消息中的审核去掉,单独在审核页面进行审核)
//                        accountUserMapper.insertAuthstr(ent.get(i).getId(), userId, date);
//                    }
//                    return 1;
//                }
//            }
//        }
//
//        return 0;
//    }
//
//    /**
//     * 管理员审核用户加入企业
//     */
//    @Override
//    public Result adminCheck(BigInteger userId, BigInteger enterpriseId, Integer adminRes, String adminId) {
//    	HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
//        Result result = new Result();
//        try {
//            //管理员收到通知,操作用户是否可以加入企业
//            if (adminRes == 1) {
//                //新加用户还是被冻结用户
//                hashMap.put("userId", userId);
//                hashMap.put("enterpriseId", enterpriseId);
//                String free = accountUserMapper.freeze(hashMap);
//                if (free != null) {
//                    if (free.equals("0")) {
//                        //被冻结用户解冻
//                        accountUserMapper.unFreeze(hashMap);
//                        //判断该用户上次操作企业ID是否为空
//                        BigInteger isEmptyLast = accountUserMapper.lastEnterpriseIdIsEmpty(userId);
//                        if (isEmptyLast == null) {
//                            //为空置为当前企业ID
//                            accountUserMapper.lastEnterpriseId(enterpriseId, userId);
//                        }
//                        //解冻后发送消息
//                        //企业名称
//                        String enterpriseName = accountUserMapper.getEnterpriseNameById(enterpriseId);
//                        //定义发送信息数据集
//                        HashMap<Object, Object> message = new HashMap<Object, Object>();
//                        //用户ID
//                        message.put("userId", userId);
//                        //管理员ID
//                        message.put("adminId", adminId);
//                        //企业ID
//                        message.put("enterpriseId", enterpriseId);
//                        //企业名称
//                        message.put("enterpriseName", enterpriseName);
//                        //审核结果:0拒绝,1同意
//                        message.put("checkResult", adminRes);
//
//                        //发送审核结果通知(同意加入)
//                        joinEnterpriseServiceFeign.adminCheck(JSON.toJSONString(message));
//                        //管理员审核完毕后删除该条记录
//                        accountUserMapper.delApplyInfo(userId, enterpriseId);
//                        result.setCode(ResponseMessage.SUCCESS);
//                        result.setMsg("加入成功");
//                        return result;
//                    }
//                }
//                // 管理员同意前验证该成员是否已经被添加过(防止重复添加)
//                int userIsExit = accountUserMapper.userIsExitEnterpriseByUserIdAndEnterpriseId(userId,enterpriseId);
//                if(userIsExit > 0) {
//                	result.setCode(ResponseMessage.FAILE);
//                    result.setMsg("已将用户加入企业,不可重复加入");
//                    return result;
//                }
//                //新用户直接加入
//                hashMap.put("status", 2);
//                //加入时间
//                hashMap.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//                //是否启用:启用
//                hashMap.put("isOpen", 1);
//                //默认未加入测试平台
//                hashMap.put("isExits", 0);
//                accountUserMapper.adminCheckJoin(hashMap);
//                //判断该用户上次操作企业ID是否为空
//                BigInteger isEmptyLast = accountUserMapper.lastEnterpriseIdIsEmpty(userId);
//                if (isEmptyLast == null) {
//                    //为空置为当前企业ID
//                    accountUserMapper.lastEnterpriseId(enterpriseId, userId);
//                }
//
//                //企业名称
//                String enterpriseName = accountUserMapper.getEnterpriseNameById(enterpriseId);
//                //定义发送信息数据集
//                HashMap<Object, Object> message = new HashMap<Object, Object>();
//                //用户ID
//                message.put("userId", userId);
//                //管理员ID
//                message.put("adminId", adminId);
//                //企业ID
//                message.put("enterpriseId", enterpriseId);
//                //企业名称
//                message.put("enterpriseName", enterpriseName);
//                //审核结果:0拒绝,1同意
//                message.put("checkResult", adminRes);
//                if (adminRes == 1) {
//                    accountUserMapper.insertUserRole(enterpriseId, userId);
//                }
//
//
//                //发送审核结果通知(同意加入)
//                joinEnterpriseServiceFeign.adminCheck(JSON.toJSONString(message));
//                //管理员审核完毕后删除该条记录
//                accountUserMapper.delApplyInfo(userId, enterpriseId);
//                result.setCode(ResponseMessage.SUCCESS);
//                result.setMsg("加入成功");
//                return result;
//            }
//            //企业名称
//            String enterpriseName = accountUserMapper.getEnterpriseNameById(enterpriseId);
//            //发送审核结果通知(拒绝加入)
//            HashMap<Object, Object> message = new HashMap<Object, Object>();
//            //申请用户ID
//            message.put("userId", userId);
//            //管理员ID
//            message.put("adminId", adminId);
//            //企业ID
//            message.put("enterpriseId", enterpriseId);
//            //企业名称
//            message.put("enterpriseName", enterpriseName);
//            //审核结果:0拒绝,1同意
//            message.put("checkResult", adminRes);
//
//            joinEnterpriseServiceFeign.adminCheck(JSON.toJSONString(message));
//            //管理员审核完毕后删除该条记录
//            accountUserMapper.delApplyInfo(userId, enterpriseId);
//            result.setCode(ResponseMessage.SUCCESS);
//            result.setMsg("加入失败,管理员拒绝");
//            return result;
//        } catch (Exception e) {
//            LOG.error("加入企业失败,系统异常", e);
//            result.setCode(ResponseMessage.SUCCESS);
//            result.setMsg("加入失败,系统异常");
//            return result;
//        }
//    }
//
//
//    /**
//     * 管理员删除成员
//     */
//    @Override
//    public Result adminDelUser(String userIds, BigInteger enterpriseId, String adminId) {
//        ArrayList<Integer> arrayList = new ArrayList<Integer>();
//        Result result = new Result();
//        String[] split = userIds.split(",");
//        for (int i = 0; i < split.length; i++) {
//            arrayList.add(Integer.parseInt(split[i]));
//        }
//        //被删除的用户在该企业中冻结
//        accountUserMapper.adminDelUser(arrayList, enterpriseId);
//        //企业名称
//        String enterpriseName = accountUserMapper.getEnterpriseNameById(enterpriseId);
//        //给用户发送被删除通知
//        //定义发送信息的数据集
//        HashMap<Object, Object> message = new HashMap<Object, Object>();
//        //被删除用户ID
//        message.put("userIds", userIds);
//        //删除用户的企业ID
//        message.put("enterpriseId", enterpriseId);
//        //删除用户的企业管理员ID
//        message.put("adminId", adminId);
//        //删除用户的企业名称
//        message.put("enterpriseName", enterpriseName);
//        //发送消息
//        joinEnterpriseServiceFeign.adminDel(JSON.toJSONString(message));
//        result.setCode(ResponseMessage.SUCCESS);
//        result.setMsg("删除成功");
//        return result;
//    }
//
//    /**
//     * 管理员批量导入成员
//     */
//    @Override
//    public int insertBatch(MultipartFile file, BigInteger enterpriseId) {
//        int index = 0, phoneExis = -1,emailExis = -1, iExit = 0;
//        BigInteger userId = null;
//        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
//        String name = file.getOriginalFilename();
//        String fileType = name.substring(name.lastIndexOf("."));
//        if (!fileType.contains("xls")) {
//            return 2;
//        }
//
//        AccountUserEntity accountUser = new AccountUserEntity();
//        HashMap<Object, Object> map = new HashMap<Object, Object>();
//        InputStream inputStream = null;
//        //07年的 不兼容之前
//        XSSFWorkbook xssfWorkbook = null;
//        try {
//            inputStream = file.getInputStream();
//            xssfWorkbook = new XSSFWorkbook(inputStream);
//        } catch (IOException e1) {
//            LOG.error("批量导入初始化失败", e1);
//        }
//
//        try {
//            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
//            //企业名称
//            String enterName = accountUserMapper.getEnterpriseNameById(enterpriseId);
//            //获取行数
//            int lastRowNum = sheet.getLastRowNum();
//            for (int i = 1; i <= lastRowNum; i++) {
//                XSSFRow row = sheet.getRow(i);
//                //输入手机号
//                if (row.getCell(0) != null & row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK) {
//                    row.getCell(0).setCellType(XSSFCell.CELL_TYPE_STRING);
//                    //手机号
//                    accountUser.setUserPhone(row.getCell(0).getStringCellValue());
//                	//判断账号是否存在:验证手机号和邮箱,账号存在直接加入企业
//            		phoneExis = this.phone(row.getCell(0).getStringCellValue());
//                	//账号存在:直接将用户加入企业,0存在
//                	if(phoneExis == 0 || emailExis == 0) {
//                		//判断该用户是否已经存在该企业中
//                        hashMap.put("userId", userId);
//                        hashMap.put("enterpriseId", enterpriseId);
//                        iExit = accountUserMapper.userExitsEnterprise(hashMap);
//                		if(iExit > 0) {
//                			//用户已经加入过该企业
//                			return 2;
//                		}
//                	}
//                    //手机号认证
//                    accountUser.setPhoneSta("1");
//                }
//                //未输入手机号
//                if (row.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK) {
//                    index += 1;
//                    map.put("userPhone", "");
//                    //手机号认证
//                    accountUser.setPhoneSta("0");
//                }
//                //输入邮箱
//                if (row.getCell(1) != null & row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) {
//                    row.getCell(1).setCellType(XSSFCell.CELL_TYPE_STRING);
//                    //邮箱
//                    accountUser.setUserEmail(row.getCell(1).getStringCellValue());
//
//                    //判断账号是否存在(已经注册):验证手机号和邮箱,账号存在直接加入企业
//            		phoneExis = this.phone(row.getCell(1).getStringCellValue());
//                	//账号存在:直接将用户加入企业,0存在
//                	if(phoneExis == 0 || emailExis == 0) {
//                		//判断该用户是否已经存在该企业中
//                        hashMap.put("userId", userId);
//                        hashMap.put("enterpriseId", enterpriseId);
//                        iExit = accountUserMapper.userExitsEnterprise(hashMap);
//                		if(iExit > 0) {
//                			//用户已经加入过该企业
//                			return 2;
//                		}
//                	}
//                    //邮箱认证
//                    accountUser.setEmailSta("1");
//                }
//                //未输入邮箱
//                if (row.getCell(1).getCellType() == Cell.CELL_TYPE_BLANK) {
//                    index += 1;
//                    map.put("userEmail", "");
//                    //邮箱未认证
//                    accountUser.setEmailSta("0");
//                }
//                //输入登录名
//                if (row.getCell(2) != null & row.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK) {
//                    row.getCell(2).setCellType(XSSFCell.CELL_TYPE_STRING);
//                    accountUser.setLoginName(row.getCell(2).getStringCellValue());
//                }
//                //未输入登录名
//                if (row.getCell(2).getCellType() == Cell.CELL_TYPE_BLANK) {
//                    index += 1;
//                    if (row.getCell(0) != null) {
//                        row.getCell(0).setCellType(XSSFCell.CELL_TYPE_STRING);
//                        accountUser.setLoginName(row.getCell(0).getStringCellValue());
//                    }
//                    if (row.getCell(1) != null) {
//                        row.getCell(1).setCellType(XSSFCell.CELL_TYPE_STRING);
//                        accountUser.setLoginName(row.getCell(1).getStringCellValue());
//                    }
//                }
//                //输入真实姓名
//                if (row.getCell(3) != null & row.getCell(3).getCellType() != Cell.CELL_TYPE_BLANK) {
//                    row.getCell(3).setCellType(XSSFCell.CELL_TYPE_STRING);
//                    accountUser.setRealName(row.getCell(3).getStringCellValue());
//                }
//                //未输入真实姓名
//                if (row.getCell(3).getCellType() == Cell.CELL_TYPE_BLANK) {
//                    index += 1;
//                    accountUser.setRealName("未输入姓名");
//                }
//                if (index == 4) {
//                    index = 0;
//                    continue;
//                }
//                //初始密码,生成和加密
//                String passwordLs = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
//                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//                // 加密后存入
//                accountUser.setPassword(passwordEncoder.encode(passwordLs));
//                //企业认证状态已认证
//                accountUser.setEnterpriseStatus("1");
//                //注册时间
//                accountUser.setRegisterDate(String.valueOf(new Date().getTime()));
//                //实名认证未认证
//                accountUser.setRealStatus("0");
//                //用户性别保密
//                accountUser.setUserSex("0");
//                //信息入库
//                accountUserMapper.insertBatch(accountUser);
//                //插入用户企业中间表
//                accountUserMapper.insertRelation(enterpriseId, accountUser.getId(), new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(new Date().getTime()).toString(), null);
//                //有手机号发送短信
//                if (row.getCell(0) != null) {
//                    row.getCell(0).setCellType(XSSFCell.CELL_TYPE_STRING);
//                    PhoneCodeUtil.sendCodeMsg(row.getCell(0).getStringCellValue(), enterName, row.getCell(0).getStringCellValue(), passwordLs);
//                }
//                //有邮箱发送邮件
//                if (row.getCell(1) != null) {
//                    row.getCell(1).setCellType(XSSFCell.CELL_TYPE_STRING);
//                    this.sendEmail(row.getCell(1).getStringCellValue(), enterName, row.getCell(1).getStringCellValue(), passwordLs);
//                }
//            }
//            return 1;
//        } catch (Exception e) {
//            LOG.error("批量导入异常", e);
//        }
//        return 0;
//    }
//
//
//    /**
//     * 发送邮件
//     */
//    private void sendEmail(String mailAddr, String enterName, String loginName, String password) {
//        try {
//            mailService.sendSimpleMail(mailAddr, "工业测试服务云", "尊敬的用户您好: \r\n" +
//                    enterName + "管理员邀请加入并成为该公司成员用户，共享该企业相关的平台使用权限。\r\n" +
//                    "登录账号为" + loginName + "完成登录" + "\r\n" +
//                    "初始登录密码为" + password + "\r\n" +
//                    "请尽快完成登录并重新设置密码。\r\n" +
//                    "如有打扰请见谅。\r\n");
//        } catch (Exception e) {
//            LOG.error("系统错误::邮件发送异常" + mailAddr, e);
//        }
//    }
//
//    /**
//     * 企业名称模糊查找
//     *
//     * 企业名称关键字
//     * @return <p> List<String> </p>
//     */
//    @Override
//    public List<String> enterpriseNameFuzzySearch() {
//        return accountUserMapper.enterpriseNameFuzzySearch();
//    }
//
//	@Override
//	public List<AccountUserEntity> queryApproveList(String userId) {
//		String  enterpriseId="";
//		List<AccountUserEntity> list=new ArrayList<>();
//		if(userId!=null && userId.length()>0) {
//			enterpriseId=accountUserMapper.queryEnterByUserId(userId);
//			if(enterpriseId!=null && enterpriseId.length()>0) {
//				 list=accountUserMapper.queryApprovedList(enterpriseId);
//			}
//		}
//		return list;
//
//	}

}

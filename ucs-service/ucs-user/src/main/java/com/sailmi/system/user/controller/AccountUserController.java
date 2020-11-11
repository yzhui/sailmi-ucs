package com.sailmi.system.user.controller;

import com.alibaba.fastjson.JSON;
import com.sailmi.core.secure.AuthUser;
import com.sailmi.system.entity.Enterprise;
import com.sailmi.system.user.entity.AccountUserEntity;
import com.sailmi.system.user.entity.LastEnterpriseEntity;
import com.sailmi.system.user.service.AccountUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;

/**
 * 统一用户Controller
 *
 * @author 统一用户
 * @version 2019-07-25
 */
@RestController
@RequestMapping("accountuser")
@Slf4j
@AllArgsConstructor
public class AccountUserController {

	@Autowired(required = false)
	private AccountUserService accountUserService;

	/**
	 * 获取用户信息
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public String get(AuthUser authUser , BigInteger id) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		id = BigInteger.valueOf(authUser.getUserId());
//		id = BigInteger.valueOf(1301788793255452689L);
		AccountUserEntity acQuery = accountUserService.get(id);
		List<Enterprise> entName = null;
		List<LastEnterpriseEntity> lastEnter = null;
		if (acQuery != null) {
			 //查询该用户下的所有企业
			entName = accountUserService.getEnterpriseName(id);
//			if (entName != null && entName.size() > 0) {
//				for (Enterprise enter : entName) {
//					List<String> list = accountUserService.queryUserRoleType(id, enter.getId());
//					if (list != null && list.size() > 0) {
//						if (list.contains("1")) {
//							enter.setRoles("1");// admin角色
//						} else {
//							enter.setRoles("3");// 企业用户角色
//						}
//					}
//				}
//			}
			//上次操作企业信息(前端需要此格式)
			lastEnter = accountUserService.getLastEnterprise(id);
			hashMap.put("status", 1);
			hashMap.put("msg", "查询用户信息成功！");
			hashMap.put("result", acQuery);
			hashMap.put("resultent", entName);
			hashMap.put("LastInfo", lastEnter);
			return JSON.toJSONString(hashMap);
		}else {
			hashMap.put("status", 0);
			hashMap.put("msg", "");
			hashMap.put("result", "");
			hashMap.put("resultent", "");
			hashMap.put("LastInfo", "");
		}
		return JSON.toJSONString(hashMap);
	}
//
//	/**
//	 * 获取用户信息
//	 */
//	@RequestMapping(value = "getCloud")
//	public String getCloud(@RequestParam("id") BigInteger id) {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		AccountUserEntity acQuery = accountUserService.get(id);
//		List<Enterprise> entName = null;
//		List<LastEnterpriseEntity> lastEnter = null;
//		if (acQuery != null) {
//			// 查询该用户下的所有企业
//			entName = accountUserService.getEnterpriseName(id);
//			if (entName != null && entName.size() > 0) {
//				for (Enterprise enter : entName) {
//					List<String> list = accountUserService.queryUserRoleType(id, enter.getId());
//					if (list != null && list.size() > 0) {
//						if (list.contains("1")) {
//							enter.setRoles("1");// admin角色
//						} else {
//							enter.setRoles("3");// 企业用户角色
//						}
//
//					}
//				}
//			}
//			// 上次操作企业信息(前端需要此格式)
//			lastEnter = accountUserService.getLastEnterprise(id);
//			hashMap.put("status", 1);
//			hashMap.put("msg", "查询用户信息成功！");
//			hashMap.put("result", acQuery);
//			hashMap.put("resultent", entName);
//			hashMap.put("LastInfo", lastEnter);
//			return JSON.toJSONString(hashMap);
//		} else {
//			hashMap.put("status", 0);
//			hashMap.put("msg", null);
//			hashMap.put("result", null);
//			hashMap.put("resultent", null);
//			hashMap.put("LastInfo", null);
//		}
//		return JSON.toJSONString(hashMap);
//	}
//
//	/**
//	 * 统计用户总数
//	 */
//	@RequestMapping(value = "/countUser", method = RequestMethod.POST)
//	public String queryCount() {
//		int count = accountUserService.countUser();
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("countUser", count);
//		return JSON.toJSONString(hashMap);
//	}
//
//	/**
//	 * 安全凭证账号风险评估
//	 *
//	 * @param userId  当前登录用户的ID
//	 * @param request
//	 * @return
//	 *         <p>
//	 *         level等级:1低,2中,3高
//	 *         </p>
//	 */
//	@PostMapping("/accountSecurity")
//	public Result accountSecurity(BigInteger userId, HttpServletRequest request) {
//		int grade = 1;// 等级
//		Result result = new Result();
//		AccountUserEntity accountUserEntity = accountUserService.get(userId);
//		if (accountUserEntity.getPhoneSta().equals("1")) {
//			grade += 1;
//		}
//		if (accountUserEntity.getEmailSta().equals("1")) {
//			grade += 1;
//		}
//		if (grade > 2) {
//			result.setCode(ResponseMessage.SUCCESS);
//			result.setMsg("成功");
//			result.setData(new HashMap<String, Integer>().put("level", 3));
//			return result;
//		}
//		if (grade <= 1) {
//			result.setCode(ResponseMessage.SUCCESS);
//			result.setMsg("成功");
//			result.setData(new HashMap<String, Integer>().put("level", 1));
//			return result;
//		}
//		result.setCode(ResponseMessage.SUCCESS);
//		result.setMsg("中");
//		result.setData(new HashMap<String, Integer>().put("level", 2));
//		return result;
//	}
//
//	/**
//	 * 获取用户信息列表
//	 */
//	@RequestMapping(value = "query", method = RequestMethod.POST)
//	public String query(AccountUserEntity accountUser) {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		List<AccountUserEntity> acQuery = accountUserService.query(accountUser);
//		// List<Enterprise> entName = accountUserService.getEnterpriseName(accountUser);
//		if (acQuery.size() > 0) {
//			hashMap.put("status", 1);
//			hashMap.put("msg", "查询用户信息列表成功！");
//			hashMap.put("result", acQuery);
//			// hashMap.put("resultent", entName);
//		} else {
//			hashMap.put("status", 0);
//			hashMap.put("msg", "查询用户信息列表失败！");
//			hashMap.put("result", null);
//		}
//		return JSON.toJSONString(hashMap);
//	}
//
//	/**
//	 * 分页
//	 */
//	@RequestMapping(value = "page", method = RequestMethod.POST)
//	public String page(AccountUserEntity accountUser, Integer pageSize, Integer pageNum, HttpServletResponse response) {
//		int size = (pageSize == null) ? 10 : pageSize;
//		int num = (pageNum == null) ? 1 : pageNum;
//		Page<AccountUserEntity> page = accountUserService.page(num, size, accountUser);
//		return JSON.toJSONString(page);
//	}
//
//	/**
//	 * 增加、修改用户信息
//	 *
//	 * @param accountUser
//	 * @return
//	 */
	@RequestMapping(value = "upsert", method = RequestMethod.POST)
	public String upsert(AccountUserEntity accountUser) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		int status = accountUserService.upsert(accountUser);
		if (status == 1) {
			hashMap.put("status", 1);
			hashMap.put("msg", "增加、保存用户信息列表成功！");
			return JSON.toJSONString(hashMap);
		} else if (status == 2) {
			hashMap.put("status", 2);
			hashMap.put("msg", "账号已存在不可重复添加!");
			return JSON.toJSONString(hashMap);
		} else {
			hashMap.put("status", 0);
			hashMap.put("msg", "增加、保存用户信息列表失败！");
		}
		return JSON.toJSONString(hashMap);
	}

	/**
	 * <p>Description: 用户编辑</p>
	 *
	 * @param authUser:
	 * @param accountUser:
	 * @return: java.lang.String
	 * @Author: syt
	 * @Date: 2020/11/9/0009 17:35
	 */
	@RequestMapping(value = "userUpdate", method = RequestMethod.POST)
	public String userUpdate(AuthUser authUser, AccountUserEntity accountUser, String date){
		Long longTime= Long.valueOf(date);
		Date d = new Date(longTime);
		accountUser.setUserBrithday(d);
		String res = accountUserService.userUpdate(authUser, accountUser);
		return res;
	}
//	/**
//	 * 删除用户信息
//	 *
//	 * @param accountUser
//	 * @return
//	 */
//	@RequestMapping(value = "delete", method = RequestMethod.POST)
//	public String delete(AccountUserEntity accountUser) {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		int status = accountUserService.delete(accountUser);
//		if (status == 1) {
//			hashMap.put("status", 1);
//			hashMap.put("msg", "删除用户信息列表成功！");
//		} else {
//			hashMap.put("status", 0);
//			hashMap.put("msg", "删除用户信息列表失败！");
//		}
//		return JSON.toJSONString(hashMap);
//	}
//
//	/**
//	 * 企业名称模糊查找
//	 *
//	 * @return Result
//	 */
//	@PostMapping(value = "enterpriseNameFuzzySearch")
//	public Result enterpriseNameFuzzySearch() {
//		Result result = new Result();
//		List<Object> resu = new ArrayList<Object>();
//		try {
//			List<String> enterpriseName = accountUserService.enterpriseNameFuzzySearch();
//			if (enterpriseName.size() > 0) {
//				for (int j = 0; j < enterpriseName.size(); j++) {
//					HashMap<String, Object> put = new HashMap<String, Object>();
//					put.put("value", enterpriseName.get(j));
//					resu.add(put);
//					put = null;
//					// 清除map中元素,用于下次使用
////                    for (Iterator<String> iterator = put.keySet().iterator(); iterator.hasNext(); ) {
////                        String key = iterator.next();
////                        if (key != null || key != "") {
////                            iterator.remove();
////                        }
////                    }
//				}
//				result.setCode(ResponseMessage.SUCCESS);
//				result.setMsg("查询成功");
//				result.setData(resu);
//				return result;
//			}
//			result.setCode(ResponseMessage.SUCCESS);
//			result.setMsg("查询成功");
//			result.setData(enterpriseName);
//			return result;
//		} catch (Exception e) {
//			log.error("企业名称模糊查询系统异常", e);
//			result.setCode(ResponseMessage.FAILE);
//			result.setMsg("系统异常");
//			return result;
//		}
//	}
//
//	/**
//	 * 加入企业:验证要加入的企业是否存在,当企业存在时用户加入企业,用户已存在于该企业时不可重复加入
//	 *
//	 * @param enterpriseName 企业名称
//	 * @param userId         用户ID
//	 * @return
//	 *         <p>
//	 *         { "msg": "返回信息", "status": 状态码 }
//	 *         </p>
//	 */
//	@PostMapping(value = "/checkEnterExist")
//	public String checkEnterExist(String enterpriseName, BigInteger userId) {
//		HashMap<String, Object> hashMap = new HashMap<>();
//		if (enterpriseName == null) {
//			hashMap.put("status", 0);
//			hashMap.put("msg", "企业名称为空,重新输入");
//			return JSON.toJSONString(hashMap);
//		}
//		if (userId == null) {
//			hashMap.put("status", 0);
//			hashMap.put("msg", "用户ID为空,重新输入");
//			return JSON.toJSONString(hashMap);
//		}
////		int checkSta = accountUserService.checkEnterExist(enterpriseName,userId);
//		int checkSta = accountUserService.joinEnterpriseMsgToAdmin(enterpriseName, userId);
//		if (checkSta == 1) {
//			hashMap.put("status", 1);
//			hashMap.put("msg", "该企业存在,加入成功");
//			return JSON.toJSONString(hashMap);
//		}
//		if (checkSta == 2) {
//			hashMap.put("status", 2);
//			hashMap.put("msg", "已在该企业中,不可重复加入");
//			return JSON.toJSONString(hashMap);
//		}
//		hashMap.put("status", 0);
//		hashMap.put("msg", "该企业不存在");
//
//		return JSON.toJSONString(hashMap);
//	}
//
//	/**
//	 * 用户在对应企业中的启用状态
//	 */
//	@RequestMapping(value = "isOpen", method = RequestMethod.POST)
//	public String isOpen(BigInteger userId, BigInteger enterpriseId, String isOpen) {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		int open = accountUserService.isOpen(userId, enterpriseId, isOpen);
//		if (open == 1) {
//			hashMap.put("status", 1);
//			hashMap.put("msg", "开启/关闭成功！");
//		} else {
//			hashMap.put("status", 0);
//			hashMap.put("msg", "开启/关闭失败！");
//		}
//		return JSON.toJSONString(hashMap);
//	}
//
//	/**
//	 * 验证手机号唯一
//	 *
//	 * @param phoneNum 手机号
//	 * @return
//	 */
//	@RequestMapping(value = "check", method = RequestMethod.POST)
//	public String checkPhone(String phoneNum) {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		int sta = accountUserService.phone(phoneNum);
//		if (sta == 1) {
//			hashMap.put("status", 1);
//			hashMap.put("msg", "手机号可用");
//		} else {
//			hashMap.put("status", 0);
//			hashMap.put("msg", "手机号已被使用");
//		}
//		return JSON.toJSONString(hashMap);
//	}
//
//	/**
//	 * 验证登录名称唯一性
//	 *
//	 * @param loginName 登录名称
//	 * @return
//	 */
//	@RequestMapping(value = "checkOne", method = RequestMethod.POST)
//	public String checkLoginName(String loginName) {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		int sta = accountUserService.checkLoginName(loginName);
//		if (sta == 1) {
//			hashMap.put("status", 1);
//			hashMap.put("msg", "成员账号可用");
//		} else {
//			hashMap.put("status", 0);
//			hashMap.put("msg", "成员账号已被使用");
//		}
//		return JSON.toJSONString(hashMap);
//	}
//
//	/**
//	 * 记录上次操作企业ID
//	 */
//	@PostMapping(value = "lastEntId")
//	public String lastEnterpriseId(BigInteger enterpriseId, BigInteger userId) {
//		HashMap<String, Object> hashMap = new HashMap<>();
//		int lastEnterSta = accountUserService.lastEnterpriseId(enterpriseId, userId);
//		if (lastEnterSta == 1) {
//			hashMap.put("status", 1);// 成功
//			hashMap.put("msg", "切换企业成功");
//			return JSON.toJSONString(hashMap);
//		}
//		hashMap.put("status", 0);// 失败
//		hashMap.put("msg", "切换企业失败");
//		return JSON.toJSONString(hashMap);
//	}
//
//	/**
//	 * 测试平台用数据 树
//	 *
//	 * @param enterId 企业ID
//	 * @return
//	 */
//	@GetMapping(value = "userTree")
//	public String userTree(@RequestParam("enterId") BigInteger enterId) throws Exception {
//		String userTree = accountUserService.treeData(enterId);
//		return userTree;
//	}
//
//	/**
//	 * 测试平台插入
//	 *
//	 * @param userId
//	 * @return
//	 */
//	@RequestMapping(value = "toCloud")
//	public List<UserAddToCloudPlatEntity> UserAddToCloud(@RequestParam("userId") String userId,
//			@RequestParam("enterpriseId") BigInteger enterpriseId) {
//		return accountUserService.toCloud(userId, enterpriseId);
//	}
//
//	/**
//	 * 用户在测试平台被删除后状态置为未被添加
//	 *
//	 * @param id
//	 */
//	@PostMapping(value = "exitsOff")
//	public void exitsOff(@RequestParam("id") BigInteger id, @RequestParam("enterpriseId") BigInteger enterpriseId) {
//		accountUserService.exitsOff(id, enterpriseId);
//	}
//
//	/**
//	 * 用户加入企业
//	 *
//	 * @param enterpriseName 企业名称
//	 */
////	@PostMapping("joinEnterpriseMsgToAdmin")
////	public Result joinEnterpriseMsgToAdmin(String enterpriseName,BigInteger userId) {
////		Result result = new Result();
////		if(enterpriseName == null || enterpriseName == "") {
////			result.setCode(ResponseMessage.PARAMETER);
////			result.setMsg("企业名称不能为空");
////			return result;
////		}
////		if(userId == null) {
////			result.setCode(ResponseMessage.PARAMETER);
////			result.setMsg("用户ID不能为空");
////			return result;
////		}
////		Result res = accountUserService.joinEnterpriseMsgToAdmin(enterpriseName,userId);
////		return res;
////	}
//
//	/**
//	 * 管理员审核用户加入企业
//	 *
//	 * @param userId       申请用户ID
//	 * @param enterpriseId 企业ID
//	 * @param adminRes     管理员处理结果:0拒绝加入,1同意加入
//	 */
//	@SameUrlData
//	@PostMapping("adminCheck")
//	public Result adminCheck(BigInteger userId, BigInteger enterpriseId, Integer adminRes, HttpServletRequest request) {
//		String adminId = null;
//		Result result = new Result();
//		try {
//			// token
//			Map<String, String> parseToken = JWTDecodeUtil.parseToken(request);
//			// token中解析userId
//			adminId = parseToken.get("userId");
//		} catch (Exception e) {
//			log.error("管理员审核用户加入企业接口:统一用户Controller:获取Token失败", e);
//			result.setCode(ResponseMessage.FAILE);
//			result.setMsg("获取Token失败");
//			return result;
//		}
//		if (userId == null) {
//			result.setCode(ResponseMessage.PARAMETER);
//			result.setMsg("用户ID不能为空");
//			return result;
//		}
//		if (enterpriseId == null) {
//			result.setCode(ResponseMessage.PARAMETER);
//			result.setMsg("企业ID不能为空");
//			return result;
//		}
//		Result res = accountUserService.adminCheck(userId, enterpriseId, adminRes, adminId);
//		return res;
//	}
//
//	/**
//	 * 管理员批量删除用户
//	 */
//	@PostMapping("adminDelUser")
//	public Result adminDelUser(BigInteger enterpriseId, String userIds, HttpServletRequest request) {
//		Result result = new Result();
//		// token
//		Map<String, String> parseToken = JWTDecodeUtil.parseToken(request);
//		// token中解析userId
//		String adminId = parseToken.get("userId");
//		if (userIds == null || userIds == "") {
//			result.setCode(ResponseMessage.PARAMETER);
//			result.setMsg("用户ID不能为空");
//			return result;
//		}
//		if (enterpriseId == null) {
//			result.setCode(ResponseMessage.PARAMETER);
//			result.setMsg("企业ID不能为空");
//			return result;
//		}
//		Result res = accountUserService.adminDelUser(userIds, enterpriseId, adminId);
//		return res;
//	}
//
//	/**
//	 * 成员信息导出Excel.xlsx格式
//	 *
//	 * @throws ParseException
//	 */
//	@RequestMapping(value = "/Export", method = RequestMethod.GET)
//	public void exportExcel(AccountUserEntity accountUser, HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		List<AccountUserEntity> acQuery = accountUserService.query(accountUser);
//		List<String> li = new ArrayList<>();
//		li.add("成员账号");
//		li.add("姓名");
//		li.add("邮箱地址");
//		li.add("所属部门");
//		li.add("添加时间");
//		li.add("最后登录");
//		new ZxExportExcel().zxExprotExcelXLSX(6, acQuery.size(), li, acQuery);
//
//		// 文件下载
//		// 获取指定目录下的第一个文件
//		// File scFileDir = new File("C:\\Users\\syt_d\\Desktop");
//		File scFileDir = new File("/var/www/html/file");
//
//		File TrxFiles[] = scFileDir.listFiles();
//		System.out.println(TrxFiles[0]);
//		String fileName = TrxFiles[0].getName(); // 下载的文件名
//
//		// 如果文件名不为空，则进行下载
//		if (fileName != null) {
//			// 设置文件路径
//			// String realPath = "C:\\Users\\syt_d\\Desktop";
//			String realPath = "/var/www/html/file";
//			File file = new File(realPath, fileName);
//
//			// 如果文件名存在，则进行下载
//			if (file.exists()) {
//
//				// 配置文件下载
//				response.setHeader("content-type", "application/octet-stream");
//				response.setContentType("application/octet-stream");
//				// 下载文件能正常显示中文
//				response.setHeader("Content-Disposition",
//						"attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//
//				// 实现文件下载
//				byte[] buffer = new byte[1024];
//				FileInputStream fis = null;
//				BufferedInputStream bis = null;
//				try {
//					fis = new FileInputStream(file);
//					bis = new BufferedInputStream(fis);
//					OutputStream os = response.getOutputStream();
//					int i = bis.read(buffer);
//					while (i != -1) {
//						os.write(buffer, 0, i);
//						i = bis.read(buffer);
//					}
//					System.out.println("Download the file successfully!");
//					Thread.sleep(800);
//					for (int j = 0; j < TrxFiles.length; j++) {
//						TrxFiles[j].delete();
//					}
//
//				} catch (Exception e) {
//					System.out.println("Download the file failed!");
//				} finally {
//					Thread.sleep(1000);
//					if (bis != null) {
//						try {
//							bis.close();
//							TrxFiles[0].delete();
//						} catch (IOException e) {
//							log.error("关闭资源异常", e);
//						}
//					}
//					if (fis != null) {
//						try {
//							fis.close();
//							TrxFiles[0].delete();
//						} catch (IOException e) {
//							log.error("关闭资源异常", e);
//						}
//					}
//				}
//			}
//		}
//		// return null;
//	}
//
//	/**
//	 * 管理员下载批量添加成员所需的模板
//	 *
//	 * @param response
//	 * @throws FileNotFoundException
//	 */
//	@GetMapping("/invitetemplatedownload")
//	public void downloadLocal(HttpServletResponse response) throws FileNotFoundException {
//		// 下载本地文件
//		String fileName = "inviteTemplate.xlsx".toString(); // 文件的默认保存名
//		// 读到流中
//		InputStream inStream = new FileInputStream("/usr/share/nginx/html/template/inviteTemplate.xlsx");// 模板文件的存放路径
//		// 设置输出的格式
//		response.reset();
//		response.setContentType("bin");
//		response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//		// 循环取出流中的数据
//		byte[] b = new byte[100];
//		int len;
//		try {
//			while ((len = inStream.read(b)) > 0)
//				response.getOutputStream().write(b, 0, len);
//			inStream.close();
//		} catch (IOException e) {
//			log.error("下载模板异常", e);
//		}
//	}
//
//	/**
//	 * 管理员批量邀请成员(excel批量导入)
//	 *
//	 * @param filebytes
//	 * @param enterpriseId 企业ID
//	 * @throws Exception
//	 */
//	@RequestMapping("/batchinviteupload")
//	public Result importExcel(@RequestParam("file") MultipartFile filebytes, BigInteger enterpriseId, BigInteger adminId) throws Exception {
//		Result result = new Result();
//		try {
//			long size = filebytes.getSize();
//			if (size > 5242880) {
//				result.setCode(ResponseMessage.FILESIZEMAX);
//				result.setMsg("文件超出大小");
//				return result;
//			}
//			int insertBatch = accountUserService.insertBatch(filebytes, enterpriseId);
//			if (insertBatch == 1) {
//				result.setCode(ResponseMessage.SUCCESS);
//				result.setMsg("导入成功");
//				return result;
//			}
//			if (insertBatch == 2) {
//				result.setCode(ResponseMessage.FILETYPE);
//				result.setMsg("文件类型错误");
//				return result;
//
//			}
//			result.setCode(ResponseMessage.FAILE);
//			result.setMsg("导入失败");
//			return result;
//		} catch (Exception e) {
//			log.error("批量导入失败,系统错误", e);
//			result.setCode(ResponseMessage.FAILE);
//			result.setMsg("系统错误");
//			return result;
//		}
//	}
//
//	/**
//	 * 待审核列表
//	 *
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/joinEnterList")
//	public Result queryApprovedList(HttpServletRequest request, Integer pageSize, Integer pageNum) {
//		Result result = new Result();
//		Map<String, String> parseToken = JWTDecodeUtil.parseToken(request);
//		if (parseToken != null && parseToken.size() > 0) {
//			String userId = parseToken.get("userId");
//			if (StringUtils.isEmpty(userId)) {
//				result.setCode(ResponseMessage.PARAMETER);
//				result.setMsg("参数缺失");
//			} else {
//				List<AccountUserEntity> list = accountUserService.queryApproveList(userId);
//				Page<AccountUserEntity> page = new Page<>(pageNum, pageSize, list.size());
//				page.setList(list);
//				result.setCode(ResponseMessage.SUCCESS);
//				result.setData(page);
//				result.setMsg("查询成功");
//			}
//		} else {
//			result.setCode(ResponseMessage.PARAMETER);
//			result.setMsg("参数缺失");
//		}
//		return result;
//	}


}

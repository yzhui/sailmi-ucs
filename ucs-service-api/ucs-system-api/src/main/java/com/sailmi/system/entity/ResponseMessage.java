package com.sailmi.system.entity;

public class ResponseMessage {

	 public final static String SUCCESS = "001";//返回成功
	 public final static String FAILE = "002";//返回失败
	 public final static String PARAMETER = "003";//返回参数缺失
	public final static String PARAERROE="004";//参数错误  验证码
	 public final static String TIMEOUT="005";//超时
	public final static String SYSERROR="006";//系统错误
	 public final static String INVALID_TOKEN="007";//无效token
	 public final static String EXPIRED_TOKEN="0071";//token失效

	 public final static String MEHTOD_NOT_ALLOWED="008";//方法不允许访问
	 public final static String NO_ACCESS="009";//无权访问
	 public final static String NONEXTSTEP="0010";//下级别缺失
	 public final static String FILESIZEMAX="0011";//文件超出大小
	 public final static String FILETYPE="0012";//文件类型错


}

package com.sailmi.system.user.persist;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Tomonori
 * @mail gutrse3321@live.com
 * @data 2020-11-10 20:32
 */
@Data
public class AliceUserModel {

	@ApiModelProperty(value = "账号")
	private String loginName;
	@ApiModelProperty(value = "密码")
	private String password;
	@ApiModelProperty(value = "真名")
	private String realName;
	@ApiModelProperty(value = "手机")
	private String userPhone;
	@ApiModelProperty(value = "生日")
	private Date userBrithday;
	@ApiModelProperty(value = "性别")
	private Integer userSex;
	@ApiModelProperty(value = "实名认证状态:1已认证，0未认证")
	private String realStatus;
	@ApiModelProperty(value = "上次登陆时间")
	private String lastLogin;
	@ApiModelProperty(value = "企业认证状态:1已认证，0未认证")
	private String enterpriseStatus = "1";
	@ApiModelProperty(value = "安全凭证")
	private String safeCode;
	@ApiModelProperty(value = "手机认证:0未认证,1已认证")
	private String phoneSta = "1";
	@ApiModelProperty(value = "上次登陆的企业ID")
	private Long enterpriseId;

	private String tenantId; //租客id
}

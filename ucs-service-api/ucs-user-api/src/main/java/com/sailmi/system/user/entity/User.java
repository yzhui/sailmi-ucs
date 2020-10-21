/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
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
package com.sailmi.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sailmi.core.mp.base.TenantEntity;
import java.util.Date;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
@TableName("ucs_user")
@EqualsAndHashCode(callSuper = true)
public class User extends TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 编号
	 */
	@TableField("safe_code")
	@ApiModelProperty(value = "安全码")
	private String code;
	/**
	 * 账号
	 */
	@TableField("login_name")
	@ApiModelProperty(value = "账号")
	private String account;
	/**
	 * 密码
	 */
	@TableField("password")
	@ApiModelProperty(value = "密码")
	private String password;
	/**
	 * 昵称
	 */
	@TableField("nick_name")
	@ApiModelProperty(value = "昵称")
	private String name;
	/**
	 * 真名
	 */
	@TableField("real_name")
	@ApiModelProperty(value = "真实姓名")
	private String realName;
	/**
	 * 头像
	 */
	@TableField("head_img")
	@ApiModelProperty(value = "用户头像")
	private String avatar;
	/**
	 * 邮箱
	 */
	@TableField("user_email")
	@ApiModelProperty(value = "邮箱")
	private String email;
	/**
	 * 手机
	 */
	@TableField("user_phone")
	@ApiModelProperty(value = "手机号")
	private String phone;
	/**
	 * 生日
	 */
	@TableField("user_birthday")
	@ApiModelProperty(value = "出生日期")
	private Date birthday;
	/**
	 * 性别
	 */
	@TableField("user_sex")
	@ApiModelProperty(value = "用户性别")
	private Integer sex;
	/**
	 * 地址
	 */
	@TableField("user_address")
	@ApiModelProperty(value = "地址")
	private String userAddress;
	/**
	 * 实名认证状态:1已认证，0未认证
	 */
	@ApiModelProperty(value = "实名认证状态:1已认证，0未认证")
	@TableField("real_status")
	private String realStatus;
	/**
	 * 上次登陆时间
	 */
	@ApiModelProperty(value = "上次登陆时间")
	@TableField("last_login")
	private String lastLogin;
	/**
	 * 企业认证状态:1已认证，0未认证
	 */
	@ApiModelProperty(value = "企业认证状态:1已认证，0未认证")
	@TableField("enterprise_status")
	private String enterpriseStatus;
	/**
	 * 手机认证:0未认证,1已认证
	 */
	@ApiModelProperty(value = "手机认证:0未认证,1已认证")
	@TableField("phone_status")
	private String phoneStatus;
	/**
	 * 邮箱认证:0未认证,1已认证
	 */
	@ApiModelProperty(value = "邮箱认证:0未认证,1已认证")
	@TableField("email_status")
	private String emailStatus;
	/**
	 * 账户完善信息:0未完成,1已完成
	 */
	@ApiModelProperty(value = "账户完善信息:0未完成,1已完成")
	@TableField("info_status")
	private String infoStatus;
	/**
	 * 上次登陆的企业ID
	 */
	@ApiModelProperty(value = "默认登陆的企业ID")
	@TableField("default_enterprise")
	private Long defaultEnterpriseId;

}

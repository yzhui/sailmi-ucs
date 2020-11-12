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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;

import com.sailmi.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户表实体类
 *
 * @author sailmi
 * @since 2020-08-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UcsAccountuser对象", description = "用户表")
public class UcsAccountuser  extends  BaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
  private Long id;
	/**
	 * 短信验证码code
	 */
	@ApiModelProperty(value = "验证码")
	private String code;
    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String username;
    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    @TableField("loginName")
  private String loginName;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 真名
     */
    @ApiModelProperty(value = "真名")
    private String realName;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @TableField("userEmail")
  private String userEmail;
    /**
     * 手机
     */
    @ApiModelProperty(value = "手机")
    @TableField("userPhone")
  private String userPhone;
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    @TableField("userBrithday")
  private Date userBrithday;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    @TableField("userSex")
  private Integer userSex;
    /**
     * 实名认证状态:1已认证，0未认证
     */
    @ApiModelProperty(value = "实名认证状态:1已认证，0未认证")
    @TableField("realStatus")
  private String realStatus;
    /**
     * 上次登陆时间
     */
    @ApiModelProperty(value = "上次登陆时间")
    @TableField("lastLogin")
  private String lastLogin;
    /**
     * 企业认证状态:1已认证，0未认证
     */
    @ApiModelProperty(value = "企业认证状态:1已认证，0未认证")
    @TableField("enterpriseStatus")
  private String enterpriseStatus;
    /**
     * 安全凭证
     */
    @ApiModelProperty(value = "安全凭证")
    @TableField("safeCode")
  private String safeCode;
    /**
     * 手机认证:0未认证,1已认证
     */
    @ApiModelProperty(value = "手机认证:0未认证,1已认证")
    @TableField("phoneSta")
  private String phoneSta;
    /**
     * 邮箱认证:0未认证,1已认证
     */
    @ApiModelProperty(value = "邮箱认证:0未认证,1已认证")
    @TableField("emailSta")
  private String emailSta;
    /**
     * 账户完善信息:0未完成,1已完成
     */
    @ApiModelProperty(value = "账户完善信息:0未完成,1已完成")
    @TableField("infoSta")
  private String infoSta;
    /**
     * 上次登陆的企业ID
     */
    @ApiModelProperty(value = "上次登陆的企业ID")
    @TableField("lastEnterpriseId")
  private Long lastEnterpriseId;
	/**
	 * 头像
	 */
	@TableField("headImgUrl")
	@ApiModelProperty(value = "用户头像")
  private String headImgUrl;

	@TableField("tenant_id")
	@ApiModelProperty(value = "租客id")
	private String tenantId; //租客id
}

/**
 * Copyright (c) 2018-2028.
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
package com.sailmi.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 企业详细信息表实体类
 *
 * @author sailmi
 * @since 2020-10-23
 */
@Data
@TableName("ucs_enterprise_details")
@ApiModel(value = "EnterpriseDetails对象", description = "企业详细信息表")
public class EnterpriseDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 企业详细信息ID
     */
    @ApiModelProperty(value = "企业详细信息ID")
    @TableId(value = "id", type = IdType.AUTO)
  private Long id;
    /**
     * 企业ID
     */
    @ApiModelProperty(value = "企业ID")
    @TableField("enterpriseId")
  private Long enterpriseId;
    /**
     * 公司LoGo存放地址
     */
    @ApiModelProperty(value = "公司LoGo存放地址")
    @TableField("logoUrl")
  private String logoUrl;
    /**
     * 营业执照存放地址
     */
    @ApiModelProperty(value = "营业执照存放地址")
    @TableField("licenseUrl")
  private String licenseUrl;
    /**
     * 法人身份信息（身份证前）
     */
    @ApiModelProperty(value = "法人身份信息（身份证前）")
    @TableField("legalPersonFrontUrl")
  private String legalPersonFrontUrl;
    /**
     * 法人身份信息(身份证后)
     */
    @ApiModelProperty(value = "法人身份信息(身份证后)")
    @TableField("legalPersonAfterUrl")
  private String legalPersonAfterUrl;
    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址")
    @TableField("enterpriseAddress")
  private String enterpriseAddress;
    /**
     * 公司电话
     */
    @ApiModelProperty(value = "公司电话")
    @TableField("enterprisePhone")
  private String enterprisePhone;
    /**
     * 网址
     */
    @ApiModelProperty(value = "网址")
    @TableField("enterpriseWebsite")
  private String enterpriseWebsite;
    /**
     * 传真
     */
    @ApiModelProperty(value = "传真")
    @TableField("enterpriseFax")
  private String enterpriseFax;
    /**
     * 公司简介
     */
    @ApiModelProperty(value = "公司简介")
    @TableField("enterpriseProfile")
  private String enterpriseProfile;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    @TableField("createBy")
  private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("createDate")
  private LocalDateTime createDate;
    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    @TableField("updateBy")
  private String updateBy;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField("updateDate")
  private LocalDateTime updateDate;
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remarks;


}

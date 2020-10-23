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
 * 企业财务信息表实体类
 *
 * @author sailmi
 * @since 2020-10-23
 */
@Data
@TableName("ucs_enterprise_finance")
@ApiModel(value = "EnterpriseFinance对象", description = "企业财务信息表")
public class EnterpriseFinance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 企业财务信息ID
     */
    @ApiModelProperty(value = "企业财务信息ID")
    @TableId(value = "id", type = IdType.AUTO)
  private Long id;
    /**
     * 企业ID
     */
    @ApiModelProperty(value = "企业ID")
    @TableField("enterpriseId")
  private Long enterpriseId;
    /**
     * 企业税号
     */
    @ApiModelProperty(value = "企业税号")
    @TableField("enterpriseTaxNumber")
  private String enterpriseTaxNumber;
    /**
     * 开户银行
     */
    @ApiModelProperty(value = "开户银行")
    @TableField("openBank")
  private String openBank;
    /**
     * 银行账户
     */
    @ApiModelProperty(value = "银行账户")
    @TableField("bankAccount")
  private String bankAccount;
    /**
     * 发票抬头
     */
    @ApiModelProperty(value = "发票抬头")
    @TableField("invoicePayable")
  private String invoicePayable;
    /**
     * 电话号码
     */
    @ApiModelProperty(value = "电话号码")
    @TableField("enterprisePhone")
  private String enterprisePhone;
    /**
     * 单位地址
     */
    @ApiModelProperty(value = "单位地址")
    @TableField("companyAddress")
  private String companyAddress;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String status;
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

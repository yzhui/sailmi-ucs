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
import com.sailmi.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author sailmi
 * @since 2020-09-02
 */
@Data
@TableName("ucs_enterprise")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Enterprise对象", description = "Enterprise对象")
public class Enterprise extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 企业ID
     */
    @ApiModelProperty(value = "企业ID")
    @TableId(value = "id", type = IdType.AUTO)
  private Long id;

    /**
     * 租户ID
     */
	@ApiModelProperty(value = "租户ID")
	@TableField("tenant_id")
    private String tenantId;
    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    @TableField("enterprise_name")
  private String enterpriseName;
    /**
     * 统一社会信用代码
     */
    @ApiModelProperty(value = "统一社会信用代码")
    @TableField("credit_code")
  private String creditCode;
    /**
     * 行业类型
     */
    @ApiModelProperty(value = "行业类型")
    @TableField("industry_type")
  private String industryType;
    /**
     * 企业类型
     */
    @ApiModelProperty(value = "企业类型")
    @TableField("enterprise_type")
  private String enterpriseType;
    /**
     * 所在区域
     */
    @ApiModelProperty(value = "所在区域")
    private String region;
    /**
     * 企业规模
     */
    @ApiModelProperty(value = "企业规模")
    @TableField("enterprise_size")
  private String enterpriseSize;
    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    @TableField("charge_person")
  private String chargePerson;
    /**
     * 负责人职位
     */
    @ApiModelProperty(value = "负责人职位")
    private String position;
    /**
     * 负责人电话
     */
    @ApiModelProperty(value = "负责人电话")
    private String phone;
    /**
     * 负责人邮箱
     */
    @ApiModelProperty(value = "负责人邮箱")
    private String email;
	/**
	 * 备注信息
	 */
    @ApiModelProperty(value = "备注信息")
    private String remarks;

	/**
	 * 生效时间
	 */
    @TableField(exist = false)
	private String createDate;
}

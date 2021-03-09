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
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户-企业关系表实体类
 *
 * @author sailmi
 * @since 2020-10-16
 */
@Data
@TableName("ucs_user_enterprise")
@ApiModel(value = "UserEnterprise对象", description = "用户-企业关系表")
public class UserEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户-企业关系ID
     */
    @ApiModelProperty(value = "用户-企业关系ID")
    @TableId(value = "id", type = IdType.AUTO)
  private Long id;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    /**
     * 企业ID
     */
    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;
    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID")
    private Long departmentId;
    /**
     * 状态（1为管理员 2为普通成员）
     */
    @ApiModelProperty(value = "状态（1为管理员 2为普通成员）")
    private Integer status;
    /**
     * 加入时间
     */
    @ApiModelProperty(value = "加入时间")
  private String createTimes;
    /**
     * 该成员在本企业中的状态0关 ，1开
     */
    @ApiModelProperty(value = "该成员在本企业中的状态0关 ，1开")
    private String isOpen;
    /**
     * 用户在该企业的测试中心中是否添加过:1已添加,0未添加
     */
    @ApiModelProperty(value = "用户在该企业的测试中心中是否添加过:1已添加,0未添加")
    private String isExits;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createUser;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    private Long updateUser;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;
    /**
     * 是否已删除
     */
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;


}

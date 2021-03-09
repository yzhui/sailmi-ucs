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
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

/**
 * 服务功能包设定，表明此功能属于哪个服务包，只有享受此服务包的用户才能访问这个功能实体类
 *
 * @author sailmi
 * @since 2020-09-17
 */
@Data
@TableName("ucs_service_menu")
@ApiModel(value = "ServiceMenu对象", description = "服务功能包设定，表明此功能属于哪个服务包，只有享受此服务包的用户才能访问这个功能")
public class ServiceMenu implements Serializable {

    private static final long serialVersionUID = 1L;

  @TableField("ID")
  private Long id;
  private Long serviceId;
  private Long menuId;
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
    private Date updateTime;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;
    /**
     * 是否已删除
     */
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;


}

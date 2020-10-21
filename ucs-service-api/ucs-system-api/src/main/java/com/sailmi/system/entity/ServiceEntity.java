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
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 可提供的服务清单，企业可以通过服务清单实体类
 *
 * @author sailmi
 * @since 2020-09-09
 */
@Data
@TableName("ucs_service")
@ApiModel(value = "Service对象", description = "可提供的服务清单，企业可以通过服务清单")
public class ServiceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

  @TableId("ID")
  private Long id;
  private String serviceName;
    /**
     * 此服务由哪个系统提供
     */
    @ApiModelProperty(value = "此服务由哪个系统提供")
    private Long systemId;
    /**
     * 服务类型，所有人可访问，还是只是授权用户可以使用，授 权用户包括收费用户，只是收费是授 权的前提，是否收费由业务系统实现
     */
    @ApiModelProperty(value = "服务类型，所有人可访问，还是只是授权用户可以使用，授 权用户包括收费用户，只是收费是授 权的前提，是否收费由业务系统实现")
    private Integer serviceType;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createUser;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
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

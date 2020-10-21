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
package com.sailmi.message.dao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.sailmi.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author sailmi
 * @since 2020-07-17
 */
@Data
@TableName("t_template")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Template对象", description = "Template对象")
public class Template extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 短信模板id
     */
    @ApiModelProperty(value = "短信模板id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Integer id;
    /**
     * 第三方渠道模板
     */
    @ApiModelProperty(value = "第三方渠道模板")
    private String channelTemplateNo;
    /**
     * 模板名称
     */
    @ApiModelProperty(value = "模板名称")
    private String name;
    /**
     * 短信类型
     */
    @ApiModelProperty(value = "短信类型")
    private Integer type;
    /**
     * 短信模板
     */
    @ApiModelProperty(value = "短信模板")
    private String template;
    /**
     * 短信验证码字段
     */
    @ApiModelProperty(value = "短信验证码字段")
    private String validateCodeKey;
    /**
     * 短信验证码有效时间（秒）
     */
    @ApiModelProperty(value = "短信验证码有效时间（秒）")
    private Integer validateCodeExpire;
    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id")
    private Integer messageSettingId;
 }

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
@TableName("t_app")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "App对象", description = "App对象")
public class MessageSetting extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 应用id
     */
    @ApiModelProperty(value = "应用id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Integer id;
    /**
     * 应用名称
     */
    @ApiModelProperty(value = "应用名称")
    private String name;
    /**
     * 签名私钥
     */
    @ApiModelProperty(value = "签名私钥")
    private String privateKey;
    /**
     * 短信前缀
     */
    @ApiModelProperty(value = "短信前缀")
    private String prefix;
    /**
     * 系统生成的数字验证码长度
     */
    @ApiModelProperty(value = "系统生成的数字验证码长度")
    private byte validateCodeLength;
    /**
     * 第三方渠道SDK配置参数
     */
    @ApiModelProperty(value = "第三方渠道SDK配置参数")
    private String channelParams;

}

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
@TableName("t_message")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Message对象", description = "Message对象")
public class Message extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 短信id
     */
    @ApiModelProperty(value = "短信id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Integer id;
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;
    /**
     * 短信参数
     */
    @ApiModelProperty(value = "短信参数")
    private String params;
    /**
     * 短信系统生成的验证码
     */
    @ApiModelProperty(value = "短信系统生成的验证码")
    private String validateCode;
    /**
     * 模板id
     */
    @ApiModelProperty(value = "模板id")
    private Integer templateId;
    /**
     * 发送状态（0：失败，1：接口调用成功，2：发送成功）
     */
    @ApiModelProperty(value = "发送状态（0：失败，1：接口调用成功，2：发送成功）")
    private Integer sendStatus;
    /**
     * 验证码验证状态（0：未验证，1：已验证）
     */
    @ApiModelProperty(value = "验证码验证状态（0：未验证，1：已验证）")
    private Integer validateStatus;
    /**
     * 短信平台发送失败代码
     */
    @ApiModelProperty(value = "短信平台发送失败代码")
    private String failCode;
    /**
     * 短信平台id
     */
    @ApiModelProperty(value = "短信平台id")
    private String bizId;
    /**
     * 短信发送渠道
     */
    @ApiModelProperty(value = "短信发送渠道")
    private String channel;
    /**
     * 重试次数
     */
    @ApiModelProperty(value = "重试次数")
    private Integer retry;
    /**
     * 收到短信时间
     */
    @ApiModelProperty(value = "收到短信时间")
    private LocalDateTime receiveDate;

}

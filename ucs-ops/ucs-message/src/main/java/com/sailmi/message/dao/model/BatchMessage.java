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
@TableName("t_batch_message")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "BatchMessage对象", description = "BatchMessage对象")
public class BatchMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 批量消息id
     */
    @ApiModelProperty(value = "批量消息id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Integer id;
    /**
     * 发送总量
     */
    @ApiModelProperty(value = "发送总量")
    private Short total;
    /**
     * 发送内容
     */
    @ApiModelProperty(value = "发送内容")
    private String content;
    /**
     * 发送状态（0：失败，1：接口调用成功，2：发送成功，3：发送完成）
     */
    @ApiModelProperty(value = "发送状态（0：失败，1：接口调用成功，2：发送成功，3：发送完成）")
    private byte sendStatus;
    /**
     * 短信通道id
     */
    @ApiModelProperty(value = "短信通道id")
    private String bizId;
    /**
     * 短信平台发送失败代码
     */
    @ApiModelProperty(value = "短信平台发送失败代码")
    private String failCode;
    /**
     * 发送成功数
     */
    @ApiModelProperty(value = "发送成功数")
    private Short success;
    /**
     * 发送失败数
     */
    @ApiModelProperty(value = "发送失败数")
    private Short failure;
    /**
     * 应用id
     */
    @ApiModelProperty(value = "消息设置模板id")
    private Integer messageSettingId;

}

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
package com.sailmi.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.sailmi.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 专题表实体类
 *
 * @author sailmi
 * @since 2020-12-16
 */
@Data
@TableName("ucs_cms_topic")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Topic对象", description = "专题表")
public class Topic extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 专题ID
     */
    @ApiModelProperty(value = "专题ID")
    private Long id;
    /**
     * 企业ID
     */
    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    private String synopsis;
    /**
     * 缩略图
     */
    @ApiModelProperty(value = "缩略图")
    private String iconUrl;
    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间")
    private LocalDateTime addTime;


}

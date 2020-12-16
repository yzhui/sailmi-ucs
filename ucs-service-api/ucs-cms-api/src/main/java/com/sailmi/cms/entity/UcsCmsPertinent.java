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

import java.time.LocalDateTime;
import com.sailmi.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 相关内容表实体类
 *
 * @author sailmi
 * @since 2020-12-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UcsCmsPertinent对象", description = "相关内容表")
public class UcsCmsPertinent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 相关内容ID
     */
    @ApiModelProperty(value = "相关内容ID")
    private Long id;
    /**
     * 企业ID
     */
    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;
    /**
     * 内容ID
     */
    @ApiModelProperty(value = "内容ID")
    private Long contentId;
    /**
     * 关联内容的url
     */
    @ApiModelProperty(value = "关联内容的url")
    private String url;
    /**
     * 关联内容的标题
     */
    @ApiModelProperty(value = "关联内容的标题")
    private String title;
    /**
     * 缩略图
     */
    @ApiModelProperty(value = "缩略图")
    private String imgUrl;
    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间")
    private LocalDateTime addTime;


}

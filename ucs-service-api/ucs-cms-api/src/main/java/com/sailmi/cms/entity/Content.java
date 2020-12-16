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
 * 内容实体类
 *
 * @author sailmi
 * @since 2020-12-16
 */
@Data
@TableName("ucs_cms_content")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Content对象", description = "内容")
public class Content extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 内容ID
     */
    @ApiModelProperty(value = "内容ID")
    private Long id;
    /**
     * 企业ID
     */
    @ApiModelProperty(value = "企业ID")
    private Long enterpriseId;
    /**
     * 类型ID
     */
    @ApiModelProperty(value = "类型ID")
    private Long typeId;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;
    /**
     * 类型0文章1图片2视音频3文件
     */
    @ApiModelProperty(value = "类型0文章1图片2视音频3文件")
    private String type;
    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    private String synopsis;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;
    /**
     * 来源
     */
    @ApiModelProperty(value = "来源")
    private String whence;
    /**
     * 图标url
     */
    @ApiModelProperty(value = "图标url")
    private String iconUrl;
    /**
     * 缩略图
     */
    @ApiModelProperty(value = "缩略图")
    private String thumbnailUrl;
    /**
     * 点击次数
     */
    @ApiModelProperty(value = "点击次数")
    private Integer clicknum;
    /**
     * 文件下载地址
     */
    @ApiModelProperty(value = "文件下载地址")
    private String url;
    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间")
    private LocalDateTime addTime;


}

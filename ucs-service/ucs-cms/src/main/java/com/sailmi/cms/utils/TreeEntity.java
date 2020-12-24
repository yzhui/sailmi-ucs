package com.sailmi.cms.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sailmi.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeEntity extends BaseEntity {
	private Long id;//节点id
	private String label;//节点名称
	private List<TreeEntity> children;//子节点
	private Long pid;//父级ID
	@TableField("enterprise_id")
	private Long enterpriseId;
	@TableField("type")
	private String type;//类型
	@TableField("add_time")
	private LocalDateTime addTime;//添加时间
	@TableField("icon_url")
	private String iconUrl;
}

/**
 * <p>Description: cloud-ecommerce.com.linose.menutree.menu.entityMenuTreeResultEntity.java</p>
 * @date 2020年5月25日 下午4:58:58
 */
package com.sailmi.system.entity;

import lombok.*;

import java.util.List;

/**
 * @author <a href="mailto:suyt@linose.com">SYT</a>
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeResultEntity {

	//菜单名称
	private String menuName;
	//菜单路径
	private String menuUrl;
	//菜单排序
	private String menuSort;
	//菜单图标路径
	private String iconUrl;
	//级别 1一级 2二级 3三级...
	private String grade;
	//菜单id
	private String id;
	//父id
	private String pid;
	//子菜单
	public List<MenuTreeResultEntity> menuChild;


}

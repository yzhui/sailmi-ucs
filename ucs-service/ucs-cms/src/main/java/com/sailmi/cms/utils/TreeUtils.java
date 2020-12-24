package com.sailmi.cms.utils;

import java.util.ArrayList;
import java.util.List;


public class TreeUtils{

	//菜单树形结构
	public static List<TreeEntity> treeMenuList(List<TreeEntity> treeList, Long parentId) {
		ArrayList<TreeEntity> childList = new ArrayList<TreeEntity>();
		for (TreeEntity tree : treeList) {
			Long id = tree.getId();
			Long pid = tree.getPid();
			if (parentId.equals(pid)) {
				List<TreeEntity> trees=treeMenuList(treeList, id);
				if(tree.getChildren()!=null) {
					tree.getChildren().addAll(trees);
				}else {
					tree.setChildren(trees);
				}
				childList.add(tree);
			}
		}
		return childList;
	}



}

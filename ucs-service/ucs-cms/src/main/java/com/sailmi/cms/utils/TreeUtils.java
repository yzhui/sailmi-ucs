package com.sailmi.cms.utils;

import java.util.ArrayList;
import java.util.List;


public class TreeUtils{


	public static List<TreeEntity> listTotree(List<TreeEntity> list) {
        List<TreeEntity> treeList = new ArrayList<TreeEntity>();
        for (TreeEntity tree : list) {
            for (TreeEntity TreeEntity : list) {
                if (TreeEntity.getPid().compareTo(tree.getId())==0) {
                    if (tree.getChildren()==null) {
                        tree.setChildren(new ArrayList<TreeEntity>());
                    }
                    tree.getChildren().add(TreeEntity);
                }
            }
        }
        return treeList;
    }




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

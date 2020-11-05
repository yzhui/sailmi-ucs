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
package com.sailmi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sailmi.system.entity.MenuTreeResultEntity;
import com.sailmi.system.entity.SystemEntity;
import com.sailmi.system.service.ISystemService;
import com.sailmi.system.service.ITenantService;
import lombok.AllArgsConstructor;
import com.sailmi.core.secure.AuthUser;
import com.sailmi.core.tool.constant.AppConstant;
import com.sailmi.core.tool.node.ForestNodeMerger;
import com.sailmi.core.tool.support.Kv;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.system.dto.MenuDTO;
import com.sailmi.system.entity.Menu;
import com.sailmi.system.entity.RoleMenu;
import com.sailmi.system.mapper.MenuMapper;
import com.sailmi.system.service.IMenuService;
import com.sailmi.system.service.IRoleMenuService;
import com.sailmi.system.vo.MenuVO;
import com.sailmi.system.wrapper.MenuWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author Chill
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	IRoleMenuService roleMenuService;
	ISystemService iSystemService;
	IMenuService menuService;

	@Override
	public IPage<MenuVO> selectMenuPage(IPage<MenuVO> page, MenuVO menu) {
		return page.setRecords(baseMapper.selectMenuPage(page, menu));
	}

	@Override
	public List<MenuVO> routes(String roleId) {
		List<Menu> allMenus = baseMapper.allMenu();
		List<Menu> roleMenus = baseMapper.roleMenu(Func.toLongList(roleId));
		List<Menu> routes = new LinkedList<>(roleMenus);
		roleMenus.forEach(roleMenu -> recursion(allMenus, routes, roleMenu));
		routes.sort(Comparator.comparing(Menu::getSort));
		MenuWrapper menuWrapper = new MenuWrapper();
		List<Menu> collect = routes.stream().filter(x -> Func.equals(x.getCategory(), 1)).collect(Collectors.toList());
		return menuWrapper.listNodeVO(collect);
	}

	public void recursion(List<Menu> allMenus, List<Menu> routes, Menu roleMenu) {
		Optional<Menu> menu = allMenus.stream().filter(x -> Func.equals(x.getId(), roleMenu.getParentId())).findFirst();
		if (menu.isPresent() && !routes.contains(menu.get())) {
			routes.add(menu.get());
			recursion(allMenus, routes, menu.get());
		}
	}

	@Override
	public List<MenuVO> buttons(String roleId) {
		List<Menu> buttons = baseMapper.buttons(Func.toLongList(roleId));
		MenuWrapper menuWrapper = new MenuWrapper();
		return menuWrapper.listNodeVO(buttons);
	}

	@Override
	public List<MenuVO> tree() {
		return ForestNodeMerger.merge(baseMapper.tree());
	}

	@Override
	public List<MenuVO> grantTree(AuthUser user) {
		return ForestNodeMerger.merge(user.getTenantId().equals(AppConstant.ADMIN_TENANT_ID) ? baseMapper.grantTree() : baseMapper.grantTreeByRole(Func.toLongList(user.getRoleId())));
	}

	@Override
	public List<String> roleTreeKeys(String roleIds) {
		List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>query().lambda().in(RoleMenu::getRoleId, Func.toLongList(roleIds)));
		return roleMenus.stream().map(roleMenu -> Func.toStr(roleMenu.getMenuId())).collect(Collectors.toList());
	}

	@Override
	public List<Kv> authRoutes(AuthUser user) {
		if (Func.isEmpty(user)) {
			return null;
		}
		List<MenuDTO> routes = baseMapper.authRoutes(Func.toLongList(user.getRoleId()));
		List<Kv> list = new ArrayList<>();
		routes.forEach(route -> list.add(Kv.init().set(route.getPath(), Kv.init().set("authority", Func.toStrArray(route.getAlias())))));
		return list;
	}

	@Override
	public List<MenuVO> grantServiceMenuTree(AuthUser user) {
		//公共菜单tree和本公司的菜单tree
		List<Long> longs = new ArrayList<>();
		List<Menu> menus =null;
		if(user!=null && user.getTenantId()!=null) {
			String tenantIds="000000";
			longs=tenantMenuIds(tenantIds,longs);//公共菜单
			if(!user.getTenantId().equals("000000")) {
				tenantIds = user.getTenantId();//本租户菜单
				longs = tenantMenuIds(tenantIds, longs);//公共菜单
			}
		}
		if(longs!=null && longs.size()>0){
			QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
			menuQueryWrapper.in("system_id",longs);
			 menus = baseMapper.selectList(menuQueryWrapper);
		}
		MenuWrapper menuWrapper = new MenuWrapper();
		return ForestNodeMerger.merge(menuWrapper.listNodeVO(menus));
	}

	@Override
	public List<MenuTreeResultEntity> queryUserMenus(QueryWrapper<Menu> menuQueryWrapper) {
		ArrayList<MenuTreeResultEntity> menuTreeResultEntities = new ArrayList<>();
		ArrayList<MenuTreeResultEntity> menuTrees = new ArrayList<>();
		List<Menu> list = menuService.list(menuQueryWrapper);
		if(list!=null && list.size()>0){
			list.stream().forEach(Menu->{
				MenuTreeResultEntity menuTreeResultEntity = new MenuTreeResultEntity();
				menuTreeResultEntity.setId(Menu.getId().toString());//id
				if(Menu.getParentId()!=null) {
					menuTreeResultEntity.setPid(Menu.getParentId().toString());//pid
				}else{
					menuTreeResultEntity.setPid("0");
				}
				menuTreeResultEntity.setIconUrl(Menu.getSource());//icon
				menuTreeResultEntity.setMenuName(Menu.getName());//name
				menuTreeResultEntity.setMenuUrl(Menu.getPath());//url
				menuTreeResultEntity.setMenuSort(Menu.getSort().toString());//sort
				menuTreeResultEntities.add(menuTreeResultEntity);
			});
		}
		if(menuTreeResultEntities.size()>0){
			 menuTrees = sortMenus(menuTreeResultEntities, "0");
		}

		return menuTrees;
	}

	private ArrayList<MenuTreeResultEntity> sortMenus(ArrayList<MenuTreeResultEntity> menuTreeResultEntities,String parentId) {
		ArrayList<MenuTreeResultEntity> childList = new ArrayList<MenuTreeResultEntity>();
		for (MenuTreeResultEntity tree : menuTreeResultEntities) {
			String id = tree.getId();
			String pid = tree.getPid();
			if (parentId.equals(pid)) {
				List<MenuTreeResultEntity> trees=sortMenus(menuTreeResultEntities, id);
				if(tree.getMenuChild()!=null) {
					tree.getMenuChild().addAll(trees);
				}else {
					tree.setMenuChild(trees);
				}
				childList.add(tree);
			}
		}
		return childList;
	}

	private List<Long> tenantMenuIds(String tenantId,List<Long> longs) {
		QueryWrapper<SystemEntity> systemEntityQueryWrapper = new QueryWrapper<>();
		systemEntityQueryWrapper.eq("tenant_id", tenantId);//查询本租户菜单
		List<SystemEntity> list = iSystemService.list(systemEntityQueryWrapper);
		if(list!=null && list.size()>0){
			list.stream().forEach(systemEntity->{
				longs.add(systemEntity.getId());
			});
		}
		return longs;
	}

}

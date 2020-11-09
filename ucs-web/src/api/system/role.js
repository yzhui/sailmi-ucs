import request from '@/router/axios';
import {apiUrl} from '@/config/env';
export const getList = (current, size, params) => {
  return request({
    url: apiUrl+'/sailmi-system/role/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}
export const grantTree = () => {
  return request({
    url: apiUrl+'/sailmi-system/menu/grant-tree',
    method: 'get',
  })
}
export const grantServiceMenuTree = () => {
  return request({
    url: apiUrl+'/sailmi-system/menu/grantservicemenu-tree',
    method: 'get',
  })
}
export const grant = (roleIds, menuIds) => {
  return request({
    url: apiUrl+'/sailmi-system/role/grant',
    method: 'post',
    params: {
      roleIds,
      menuIds
    }
  })
}

export const remove = (ids) => {
  return request({
    url: apiUrl+'/sailmi-system/role/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: apiUrl+'/sailmi-system/role/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: apiUrl+'/sailmi-system/role/submit',
    method: 'post',
    data: row
  })
}


export const getRole = (roleIds) => {
  return request({
    url: apiUrl+'/sailmi-system/menu/role-tree-keys',
    method: 'get',
    params: {
      roleIds,
    }
  })
}

export const getRoleTree = (tenantId) => {
  return request({
    url: apiUrl+'/sailmi-system/role/tree',
    method: 'get',
    params: {
      tenantId,
    }
  })
}

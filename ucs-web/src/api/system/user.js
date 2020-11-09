import request from '@/router/axios';
import {apiUrl} from '@/config/env';

export const getList = (current, size, params) => {
  return request({
    url: apiUrl+'/sailmi-user/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}
export const remove = (ids) => {
  return request({
    url: apiUrl+'/sailmi-user/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: apiUrl+'/sailmi-user/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: apiUrl+'/sailmi-user/update',
    method: 'post',
    data: row
  })
}

export const grant = (userIds, roleIds) => {
  return request({
    url: apiUrl+'/sailmi-user/grant',
    method: 'post',
    params: {
      userIds,
      roleIds,
    }
  })
}

export const getUser = (id) => {
  return request({
    url: apiUrl+'/sailmi-user/detail',
    method: 'get',
    params: {
      id,
    }
  })
}

export const getUserInfo = () => {
  return request({
    url: apiUrl+'/sailmi-user/info',
    method: 'get',
  })
}

export const resetPassword = (userIds) => {
  return request({
    url: apiUrl+'/sailmi-user/reset-password',
    method: 'post',
    params: {
      userIds,
    }
  })
}

export const updatePassword = (oldPassword, newPassword, newPassword1) => {
  return request({
    url: apiUrl+'/sailmi-user/update-password',
    method: 'post',
    params: {
      oldPassword,
      newPassword,
      newPassword1,
    }
  })
}


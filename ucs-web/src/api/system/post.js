import request from '@/router/axios';
import {apiUrl} from '@/config/env';
export const getList = (current, size, params) => {
  return request({
    url: apiUrl+'/sailmi-system/post/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const getPostList = (tenantId) => {
  return request({
    url: apiUrl+'/sailmi-system/post/select',
    method: 'get',
    params: {
      tenantId
    }
  })
}

export const getDetail = (id) => {
  return request({
    url: apiUrl+'/sailmi-system/post/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: apiUrl+'/sailmi-system/post/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: apiUrl+'/sailmi-system/post/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: apiUrl+'/sailmi-system/post/submit',
    method: 'post',
    data: row
  })
}


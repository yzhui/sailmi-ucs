import request from '@/router/axios';
import {apiUrl} from '@/config/env';

export const getList = (current, size, params) => {
  return request({
    url: apiUrl + '/sailmi-system/region/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const getLazyTree = (parentCode, params) => {
  return request({
    url:  apiUrl + '/sailmi-system/region/lazy-tree',
    method: 'get',
    params: {
      ...params,
      parentCode
    }
  })
}

export const getDetail = (code) => {
  return request({
    url:  apiUrl + '/sailmi-system/region/detail',
    method: 'get',
    params: {
      code
    }
  })
}

export const remove = (id) => {
  return request({
    url:  apiUrl + '/sailmi-system/region/remove',
    method: 'post',
    params: {
      id,
    }
  })
}

export const submit = (row) => {
  return request({
    url: apiUrl + '/sailmi-system/region/submit',
    method: 'post',
    data: row
  })
}


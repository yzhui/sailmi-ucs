import request from '@/router/axios';
import {apiUrl} from '@/config/env';
export const getList = (current, size, params) => {
  return request({
    url:  apiUrl + '/sailmi-message/template/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const getDetail = (id) => {
  return request({
    url:  apiUrl + '/sailmi-message/template/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url:  apiUrl + '/sailmi-message/template/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url:  apiUrl + '/sailmi-message/template/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url:  apiUrl + '/sailmi-message/template/submit',
    method: 'post',
    data: row
  })
}


import request from '@/router/axios';

export const getList = (current, size, params) => {
  return request({
    url: '/api/sailmi-system/service/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}
export const grantServiceTree = () => {
  return request({
    url: '/api/sailmi-system/service/service-tree',
    method: 'get',
  })
}

export const getDetail = (id) => {
  return request({
    url: '/api/sailmi-system/service/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/api/sailmi-system/service/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const getServiceMenus = (serviceId) => {
  return request({
    url: '/api/sailmi-system/service/service-tree-keys',
    method: 'get',
    params: {
      serviceId,
    }
  })
}

export const grant = (serviceId, menuIds) => {
  return request({
    url: '/api/sailmi-system/service/grant',
    method: 'post',
    params: {
      serviceId,
      menuIds
    }
  })
}



export const add = (row) => {
  return request({
    url: '/api/sailmi-system/service/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/sailmi-system/service/submit',
    method: 'post',
    data: row
  })
}


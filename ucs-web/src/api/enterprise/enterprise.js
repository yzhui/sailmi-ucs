import request from '@/router/axios';

export const getList = (current, size, params) => {
  return request({
    url: '/api/sailmi-enterprise/enterprise/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}
export const grantService = (enterpriseId, serviceIds) => {
  return request({
    url: '/api/sailmi-enterprise/enterprise/servicerant',
    method: 'post',
    params: {
      enterpriseId,
      serviceIds
    }
  })
}
export const getEnterpriseService = (enterpriseId) => {
  return request({
    url: '/api/sailmi-enterprise/enterprise/enterprise-tree-keys',
    method: 'get',
    params: {
      enterpriseId,
    }
  })
}

export const getDetail = (id) => {
  return request({
    url: '/api/sailmi-enterprise/enterprise/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/api/ucs-enterprise/enterprise/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/api/sailmi-enterprise/enterprise/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/sailmi-enterprise/enterprise/submit',
    method: 'post',
    data: row
  })
}


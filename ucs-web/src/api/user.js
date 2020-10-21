import request from '@/router/axios';
import {baseUrl} from '@/config/env';
import website from "@/config/website";
import {apiUrl} from '@/config/env';

export const loginByUsername = (tenantId, account, password, type, key, code) => request({
  url: apiUrl+'/sailmi-auth/token',
  method: 'post',
  headers: {
    'Captcha-Key': key,
    'Captcha-Code': code,
  },
  params: {
    grantType: (website.captchaMode ? "captcha" : "password"),
    tenantId,
    account,
    password,
    type
  }
});

export const getButtons = () => request({
  url: apiUrl+'/sailmi-system/menu/buttons',
  method: 'get'
});

export const getUserInfo = () => request({
  url: baseUrl + '/user/getUserInfo',
  method: 'get'
});

export const refeshToken = () => request({
  url: baseUrl + '/user/refesh',
  method: 'post'
})

export const getMenu = () => request({
  url: apiUrl+'/sailmi-system/menu/routes',
  method: 'get'
});

export const getCaptcha = () => request({
  url: apiUrl+'/sailmi-auth/captcha',
  method: 'get'
});

export const getTopMenu = () => request({
  url: baseUrl + '/user/getTopMenu',
  method: 'get'
});

export const sendLogs = (list) => request({
  url: baseUrl + '/user/logout',
  method: 'post',
  data: list
})

export const logout = () => request({
  url: baseUrl + '/user/logout',
  method: 'get'
})

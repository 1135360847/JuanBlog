import request from '@/utils/request'
const api_name = '/front/articleCategory'
export function getCategoryList(params) {
  return request({
    url: `${api_name}`,
    method: 'get',
    params
  })
}



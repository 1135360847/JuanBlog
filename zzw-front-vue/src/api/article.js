import request from '@/utils/request'
const api_name = '/front/article'
export function getList(params) {
  return request({
    url: `${api_name}`,
    method: 'get',
    params
  })
}

export function getPage(params) {
  return request({
    url: `${api_name}/page`,
    method: 'get',
    params
  })
}

export function getById(id) {
  return request({
    url: `${api_name}/${id}`,
    method: 'get'
  })
}

export function getTagList() {
  return request({
    url: `${api_name}/tagList`,
    method: 'get'
  })
}

export function getTimeLine() {
  return request({
    url: `${api_name}/timeLine`,
    method: 'get'
  })
}

export function updateReadCount(id) {
  return request({
    url: `${api_name}/readCount`,
    method: 'put',
    params: {
      id
    }
  })
}


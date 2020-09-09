import request from '@/utils/request'

const api_name = '/admin/permission'

export default {
  getNestedTreeList(searchObj) {
    return request({
      url: `${api_name}`,
      method: 'get',
      params: searchObj
    })
  },
  removeById(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: "delete"
    })
  },
  saveLevelOne(menu) {
    return request({
      url: `${api_name}/save`,
      method: "post",
      data: menu
    })
  },
  update(menu) {
    return request({
      url: `${api_name}/update`,
      method: "put",
      data: menu
    })
  },
  getAssign(roleId) {
    return request({
      url: `${api_name}/assign/${roleId}`,
      method: 'get'
    })
  },
  doAssign(roleId, permissionId) {
    return request({
      url: `${api_name}/assign`,
      method: "post",
      params: {roleId, permissionId}
    })
  }
}

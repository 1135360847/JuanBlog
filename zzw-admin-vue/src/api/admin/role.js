
import request from '@/utils/request'

const api_name = '/admin/role'

export default {

    getPage(query) {
        return request({
            url: `${api_name}/page`,
            method: 'get',
            params: query // url查询字符串或表单键值对
        })
    },
    getById(id) {
        return request({
          url: `${api_name}/${id}`,
          method: 'get'
        })
      },
    
      save(role) {
        return request({
          url: `${api_name}`,
          method: 'post',
          data: role
        })
      },
    
      updateById(role) {
        return request({
          url: `${api_name}`,
          method: 'put',
          data: role
        })
      },
    //   getAssign(roleId){
    //     return request({
    //       url: `${api_name}/assign/${roleId}`,
    //       method: 'get'
    //     })
    //   },
      removeById(id){
        return request({
          url: `${api_name}/${id}`,
          method: 'delete'
        })
      },
      removeRows(idList){
        return request({
          url: `${api_name}/batch`,
          method: 'delete',
          data:idList
        })
      },
      check(rolecheck) {
        return request({
          url: `${api_name}/check`,
          method: 'post',
          data: rolecheck
        })
      },
}
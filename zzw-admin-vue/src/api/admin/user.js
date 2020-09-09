import request from '@/utils/request'

const api_name = '/admin/user'

export default {

    getPage( query) {
      return request({
          url: `${api_name}/page`,
          method: 'get',
          params: query
      })
    },

    getById(id) {
      return request({
        url: `${api_name}/${id}`,
        method: 'get'
      })
    },
    
    save(user) {
      return request({
        url: `${api_name}`,
        method: 'post',
        data: user
      })
    },
    
    updateById(user) {
      return request({
        url: `${api_name}`,
        method: 'put',
        data: user
      })
    },

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

    getAssign(userId){
      return request({
        url: `${api_name}/assign/${userId}`,
        method: 'get'
      })
    },
  
    saveAssign(userId, roleIds){
      return request({
          url: `${api_name}/assign`,
          method: 'post',
          params:{userId, roleIds}
          })
      },
 
    
    check(userCheck) {
      return request({
        url: `${api_name}/check`,
        method: 'post',
        data: userCheck
      })
    },
}

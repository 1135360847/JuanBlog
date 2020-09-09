import request from '@/utils/request'

const api_name = '/admin/article'

export default {

    getPage(query) {
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
    
    save(article) {
      return request({
        url: `${api_name}`,
        method: 'post',
        data: article
      })
    },
    
    updateById(article) {
      return request({
        url: `${api_name}`,
        method: 'put',
        data: article
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
}

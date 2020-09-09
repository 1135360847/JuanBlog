import request from '@/utils/request'

const api_name = '/file'

export default {
    getBlobImg(filename) {
        return request({
            url: `${api_name}/image/${filename}`,
            method: 'get',
            responseType: 'blob'
        })
    },

    uploadFile(file) {
        return request({
            url: `${api_name}`,
            method: 'post',
            data: file
        })
    },

    deleteByFileKey(fileKey){
        return request({
            url: `${api_name}/${fileKey}`,
            method: 'delete'
          })
    }
    
}
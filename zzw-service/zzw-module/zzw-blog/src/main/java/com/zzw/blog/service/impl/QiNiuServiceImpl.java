package com.zzw.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.zzw.blog.service.QiNiuService;
import com.zzw.common.core.constant.properties.QiNiuProperties;
import com.zzw.common.core.util.KeyUtil;
import com.zzw.core.api.vo.QiNiuPath;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * create by zhangzhiwen on 2020/8/6
 */
@Slf4j
@Service
public class QiNiuServiceImpl implements QiNiuService, InitializingBean {
    @Autowired
    private UploadManager uploadManager;
    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    // 定义七牛云上传的相关策略
    private StringMap putPolicy;


    /**
     * 以文件的形式上传
     *
     * @param file
     * @return
     * @throws QiniuException
     */
    @Override
    public QiNiuPath uploadFile(File file) throws QiniuException {
        Response response = this.uploadManager.put(file, KeyUtil.genUniqueKey(), getUploadToken());
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(file, KeyUtil.genUniqueKey(), getUploadToken());
            retry++;
        }
        //解析结果
        DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
        String return_path = QiNiuProperties.prefix + "/" + putRet.key;
        log.info("文件名称={}", return_path);
        QiNiuPath qiNiuPath = new QiNiuPath();
        qiNiuPath.setFileKey(putRet.key);
        qiNiuPath.setFilePath(return_path);
        return qiNiuPath;
    }

    /**
     * 以流的形式上传
     *
     * @param inputStream
     * @return
     * @throws QiniuException
     */
    @Override
    public QiNiuPath uploadFile(InputStream inputStream) throws QiniuException {
        Response response = this.uploadManager.put(inputStream, KeyUtil.genUniqueKey(), getUploadToken(), null, null);
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(inputStream, KeyUtil.genUniqueKey(), getUploadToken(), null, null);
            retry++;
        }
        //解析结果
        DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
        String return_path = QiNiuProperties.prefix + "/" + putRet.key;
        log.info("文件名称={}", return_path);
        QiNiuPath qiNiuPath = new QiNiuPath();
        qiNiuPath.setFileKey(putRet.key);
        qiNiuPath.setFilePath(return_path);
        return qiNiuPath;
    }


    /**
     * 删除七牛云上的相关文件
     *
     * @param key
     * @return
     * @throws QiniuException
     */
    @Override
    public Response delete(String key) throws QiniuException {
        Response response = bucketManager.delete(QiNiuProperties.bucket, key);
        int retry = 0;
        while (response.needRetry() && retry++ < 3) {
            response = bucketManager.delete(QiNiuProperties.bucket, key);
        }
        return response;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
        // 自定义文件名字
        // putPolicy.put("saveKey", UUID.randomUUID().timestamp());
    }

    /**
     * 获取上传凭证
     *
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(QiNiuProperties.bucket, null, 3600, putPolicy);
    }

}

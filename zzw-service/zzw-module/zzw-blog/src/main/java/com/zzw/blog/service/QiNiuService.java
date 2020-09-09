package com.zzw.blog.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.zzw.core.api.vo.QiNiuPath;

import java.io.File;
import java.io.InputStream;

/**
 * create by zhangzhiwen on 2020/8/6
 */
public interface QiNiuService {

    QiNiuPath uploadFile(File file) throws QiniuException;

    QiNiuPath uploadFile(InputStream inputStream) throws QiniuException;

    Response delete(String key) throws QiniuException;
}

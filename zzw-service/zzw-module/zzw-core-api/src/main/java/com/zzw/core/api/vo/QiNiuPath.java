package com.zzw.core.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * create by zhangzhiwen on 2020/8/6
 */
@Data
public class QiNiuPath {
    @ApiModelProperty(name = "Path" , value = "可以直接访问的文件地址")
    private String filePath;
    @ApiModelProperty(name = "fileKey" , value = "上传文件后返回的文件Key")
    private String fileKey;
}

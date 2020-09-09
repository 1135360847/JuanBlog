package com.zzw.blog.controller;

import com.qiniu.http.Response;
import com.zzw.blog.service.QiNiuService;
import com.zzw.common.core.rest.baseVO.R;
import com.zzw.core.api.vo.QiNiuPath;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 博客文章相关API接口层</P>
 * @version: V1.0
 * @author: zzw
 * @time    2020年07月13日
 *
 */
@Api(tags = "博客文件相关",value="博客文件相关" )
@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController {
    private QiNiuService qiNiuService;
//
//    //显示图片
//    @GetMapping(value = "/image/{filename:.+}")
//    public void getFile(HttpServletResponse response, @PathVariable String filename) throws IOException {
//        String realPath=ROOT+filename;
//        FileInputStream inputStream = new FileInputStream(realPath);
//        int i = inputStream.available();
//        //byte数组用于存放图片字节数据
//        byte[] buff = new byte[i];
//        inputStream.read(buff);
//        //记得关闭输入流
//        inputStream.close();
//        //设置发送到客户端的响应内容类型
//        response.setContentType("image/*");
//        OutputStream out = response.getOutputStream();
//        out.write(buff);
//        //关闭响应输出流
//        out.close();
//    }

//    //上传图片
//    @PostMapping
//    public R upload(MultipartFile file) throws IOException {
//        //返回的文件地址
//
//        String name=file.getOriginalFilename();
//        name=name.substring(name.lastIndexOf("."));
//        name= UUID.randomUUID().toString().replace("-","")+name;
//
//        File dir = new File(ROOT);
//        if(!dir.exists()){
//            dir.mkdirs();
//        }
//        file.transferTo(Paths.get(ROOT, name));
//        return R.ok(name);
//    }

    /**
     * 七牛云流式上传图片
     * @param file
     * @return 返回图片地址
     * @throws IOException
     */
    @PostMapping
    public R<QiNiuPath> upload(MultipartFile file) throws IOException {
        FileInputStream inputStream = (FileInputStream) file.getInputStream();
        QiNiuPath result = qiNiuService.uploadFile(inputStream);
        return R.ok(result);
    }

    /**
     * 七牛云删除文件
     * @param fileKey
     * @return 返回图片地址
     * @throws IOException
     */
    @ApiOperation(value = "删除文件", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileKey", value = "七牛云文件key", required = true, paramType = "path")
    })
    @DeleteMapping(value = "{fileKey}")
    public R<Response> deleteByFileKy(@PathVariable("fileKey") String fileKey) throws IOException {
        Response result = qiNiuService.delete(fileKey);
        return R.ok(result);
    }


}
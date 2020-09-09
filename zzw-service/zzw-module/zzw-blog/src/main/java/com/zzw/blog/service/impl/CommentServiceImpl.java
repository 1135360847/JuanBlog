package com.zzw.blog.service.impl;

import com.zzw.core.api.po.Comment;
import com.zzw.blog.mapper.CommentMapper;
import com.zzw.blog.service.CommentService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p> 
 * 
 * <p>说明： 博客文章相关服务实现层</P>
 * @version: V1.0
 * @author: zzw
 * 
 */
@Service
@AllArgsConstructor
public class CommentServiceImpl  extends ServiceImpl<CommentMapper, Comment> implements CommentService  {
	
}
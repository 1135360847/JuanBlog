package com.zzw.blog.service.impl;

import com.zzw.core.api.po.Tag;
import com.zzw.blog.mapper.TagMapper;
import com.zzw.blog.service.TagService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
/**   
 * <p>自动生成工具：mybatis-dsc-generator</p> 
 * 
 * <p>说明： 博客标签相关服务实现层</P>
 * @version: V1.0
 * @author: zzw
 * 
 */
@Service
@AllArgsConstructor
public class TagServiceImpl  extends ServiceImpl<TagMapper, Tag> implements TagService  {
	
}
package com.zzw.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzw.core.api.dto.ArticleDTO;
import com.zzw.core.api.po.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.core.api.vo.ArticleVO;
import com.zzw.core.api.vo.TimeLine;

import java.util.List;
import java.util.Set;

/**
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 博客文章相关服务层</P>
 * @version: V1.0
 * @author: zzw
 * 
 */
public interface ArticleService extends IService<Article> {
    IPage<ArticleVO> page(Page page, ArticleDTO articleDTO);

    List<ArticleVO> list(ArticleDTO articleDTO);

    ArticleVO selectVOById(Integer articleId);

    Set<String> selectTagList();

    List<TimeLine> timeLine();

    Article updateReadCount(Integer id);
}
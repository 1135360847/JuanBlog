package com.zzw.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzw.core.api.dto.ArticleDTO;
import com.zzw.core.api.vo.ArticleVO;
import org.apache.ibatis.annotations.Mapper;
import com.zzw.core.api.po.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**   
 * <p>自动生成工具：mybatis-dsc-generator</p>
 * 
 * <p>说明： 博客文章相关数据访问层</p>
 * @version: V1.0
 * @author: zzw
 * 
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    IPage<ArticleVO> articlePage(Page page, @Param("query") ArticleDTO articleDTO);

    List<ArticleVO> selectList(@Param("query") ArticleDTO articleDTO);

    ArticleVO selectVOById(Integer articleId);
}

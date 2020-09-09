package com.zzw.blog.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.zzw.blog.mapper.TagMapper;
import com.zzw.common.core.constant.ArticleConstants;
import com.zzw.common.core.constant.CommonConstants;
import com.zzw.common.core.util.HtmlUtils;
import com.zzw.core.api.dto.ArticleDTO;
import com.zzw.core.api.po.Article;
import com.zzw.blog.mapper.ArticleMapper;
import com.zzw.blog.service.ArticleService;
import com.zzw.core.api.po.Tag;
import com.zzw.core.api.vo.ArticleVO;
import com.zzw.core.api.vo.TimeLine;
import io.netty.util.internal.ObjectUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
public class ArticleServiceImpl  extends ServiceImpl<ArticleMapper, Article> implements ArticleService  {
    private ArticleMapper articleMapper;
    private TagMapper tagMapper;

    /**
     * 新增文章
     */
    public boolean save(Article entity) {
        //取出html转化为纯文字
        String html = entity.getArticleContentHtml();
        String str = HtmlUtils.html2Str(html);

        //截取一段内容纯文本当作文章简介
        if (str.length() < ArticleConstants.REMARK_LENGTH) {
            entity.setArticleRemark(str+ArticleConstants.REMARK_SUFFIX);
        }else{
            entity.setArticleRemark(str.substring(0,ArticleConstants.REMARK_LENGTH)+ArticleConstants.REMARK_SUFFIX);
        }

        //插入标签总表
        if(StringUtils.isEmpty(entity.getArticleTags())){
            List<String> tagList = Arrays.asList(entity.getArticleTags().split(","));
            tagList.forEach(item ->{
                //先判断是否存在
                Integer count = tagMapper.selectCount(Wrappers.<Tag>lambdaQuery().eq(Tag::getName,item));
                if(count<=0){
                    Tag tag = new Tag();
                    tag.setName(item);
                    tagMapper.insert(tag);
                }
            });
        }
        return SqlHelper.retBool(this.getBaseMapper().insert(entity));
    }

    public boolean updateById(Article entity) {
        //取出html转化为纯文字
        String html = entity.getArticleContentHtml();
        String str = HtmlUtils.html2Str(html);

        //截取一段内容纯文本当作文章简介
        if (str.length() < ArticleConstants.REMARK_LENGTH) {
            entity.setArticleRemark(str+ArticleConstants.REMARK_SUFFIX);
        }else{
            entity.setArticleRemark(str.substring(0,ArticleConstants.REMARK_LENGTH)+ArticleConstants.REMARK_SUFFIX);
        }

        //插入标签总表
        if(StringUtils.isEmpty(entity.getArticleTags())){
            List<String> tagList = Arrays.asList(entity.getArticleTags().split(","));
            tagList.forEach(item ->{
                //先判断是否存在
                Integer count = tagMapper.selectCount(Wrappers.<Tag>lambdaQuery().eq(Tag::getName,item));
                if(count<=0){
                    Tag tag = new Tag();
                    tag.setName(item);
                    tagMapper.insert(tag);
                }
            });
        }


        return SqlHelper.retBool(this.getBaseMapper().updateById(entity));
    }


    /**
     * 分页查询文章
     * @param page
     * @param articleDTO
     * @return
     */
    @Override
    public IPage<ArticleVO> page(Page page, ArticleDTO articleDTO) {

        IPage<ArticleVO> articleVOIPage  = articleMapper.articlePage(page,articleDTO);
        List<ArticleVO> articleVOList =  articleVOIPage.getRecords();
        if(CollectionUtils.isNotEmpty(articleVOList)){
            articleVOList.forEach(item -> {
                String tags = item.getArticleTags();
                if(!StringUtils.isEmpty(tags)){
                    item.setArticleTagList(Arrays.asList(tags.split(",")));
                }
            });

        }
        return articleVOIPage;
    }

    /**
     * 查询文章list
     * @param articleDTO
     * @return
     */
    @Override
    public List<ArticleVO> list( ArticleDTO articleDTO) {

        List<ArticleVO> articleVOList  = articleMapper.selectList(articleDTO);
        if(CollectionUtils.isNotEmpty(articleVOList)){
            articleVOList.forEach(item -> {
                String tags = item.getArticleTags();
                if(!StringUtils.isEmpty(tags)){
                    item.setArticleTagList(Arrays.asList(tags.split(",")));
                }
            });

        }
        return articleVOList;
    }
    /**
     * 查询一条文章
     * @param articleId
     * @return
     */
    @Override
    public ArticleVO selectVOById(Integer articleId) {
        ArticleVO  articleVO = articleMapper.selectVOById(articleId);
        if(articleVO!= null && !StringUtils.isEmpty(articleVO.getArticleTags())){
            articleVO.setArticleTagList(Arrays.asList(articleVO.getArticleTags().split(",")));
        }
        return articleVO;
    }

    /**
     *查询所有的tag
     */
    @Override
    public Set<String> selectTagList() {
        //查出所有的标签
        Set<String> result = new HashSet<>();
        List<Tag> tagList = tagMapper.selectList(Wrappers.emptyWrapper());
        if(CollectionUtils.isNotEmpty(tagList)){
            result = tagList.stream().map(Tag::getName).collect(Collectors.toSet());
        }
        return result;
    }

    /**
     * 查询归档
     * @return
     */
    @Override
    public List<TimeLine> timeLine() {
        //返回的结果
        List<TimeLine> result = new ArrayList<>();
        //查询所有的文章
        List<ArticleVO> articleVOList = this.list(new ArticleDTO());
        //拿出所有的年份
        if(CollectionUtils.isNotEmpty(articleVOList)){
            List<LocalDateTime> yearTimesList = articleVOList.stream().map(Article::getGmtCreate).collect(Collectors.toList());
            //获取年份并去重
            List<Integer> yearList = yearTimesList.stream().map(LocalDateTime::getYear).collect(Collectors.toList());
            yearList = yearList.stream().distinct().collect(Collectors.toList());
            yearList.forEach(item ->{
                TimeLine timeLine = new TimeLine();
                List<ArticleVO> yearArticleVOList = articleVOList.stream().filter(yearArticleVO -> yearArticleVO.getGmtCreate().getYear()==item).collect(Collectors.toList());
                timeLine.setYear(item);
                timeLine.setArticleVOList(yearArticleVOList);
                result.add(timeLine);
            });
        }
        return result;
    }

    /**
     * 修改阅读数
     * @param id
     * @return
     */
    @Override
    public Article updateReadCount(Integer id) {
        //先查询出原来的阅读数
        Article article = baseMapper.selectById(id);
        if(ObjectUtils.isNotEmpty(article)){
            Integer readCount = article.getArticleReadCount();
            //在原有的基础上加上数字
            readCount = readCount+ArticleConstants.READ_ADD;
            article.setArticleReadCount(readCount);

            Article  update = new Article();
            update.setId(article.getId());
            update.setArticleReadCount(readCount);
            //修改
            baseMapper.updateById(update);
        }
        return article;
    }

}
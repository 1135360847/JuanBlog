<template>
  <div class="home-content"  v-cloak>
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <div class="layout-left" style="background-color: #fff;padding:30px 20px 10px 20px">
          <article-page-header :article="article"></article-page-header>
          <article-page-content>
            <article id="article-main-page" class="typo container" slot="content" ref="article" v-html="article.articleContentHtml">
            </article>
          </article-page-content>
          <!-- <article-pay></article-pay> -->
          <article-page-footer  :commentList="arrlist"></article-page-footer>
          <article-page-footer  :tags="tags" :postId="article.id"></article-page-footer>
        </div>
      </Col>
      <Col :xs="0" :sm="0" :md="0" :lg="7">
        <div class="layout-right">
          <recommend></recommend> <!-- 这里是引入推荐阅读 -->
          <category-wall style="margin-top: 15px;"></category-wall>
          <tag-wall style="margin-top: 15px;"></tag-wall>
          <Affix :offset-top="60">
            <side-toc style="margin-top: 15px;"></side-toc>
          </Affix>
        </div>
      </Col>
    </Row>
  </div>
</template>
<script type="text/ecmascript-6">
import ArticlePageHeader from '@/components/views/Article/ArticlePageHeader'
import ArticlePageContent from '@/components/views/Article/ArticlePageContent'
import ArticlePageFooter from '@/components/views/Article/ArticlePageFooter'
import About from '@/components/views/About'
import CategoryWall from '@/components/views/CategoryWall'
import TagWall from '@/components/views/TagWall'
import Recommend from '@/components/views/Recommend'
import TOC from '@/common/js/MarkdownToc'
import SideToc from '@/components/views/SideToc'
// highlight.js引入
import hljs from 'highlight.js'
// 样式文件
import 'highlight.js/styles/solarized-light.css'
// TOC滚动监听
import TocScrollSpy from '@/common/js/TocScrollSpy'
import {getById,updateReadCount } from '@/api/article'
var HLJS = hljs

export default {
  data () {
    return {
      article: {},
      tags: [],
      arrlist: [],
      arrnum: 5
    }
  },
  components: {
    'article-page-header': ArticlePageHeader,
    'article-page-content': ArticlePageContent,
    'article-page-footer': ArticlePageFooter,
    'about': About,
    'category-wall':CategoryWall,
    'tag-wall': TagWall,
    'recommend': Recommend,
    // 'article-pay':Pay,
    'side-toc':SideToc
  },
  created(){
    this.getArticle(this.$route.params.articleId)
  },
  mounted() {
    this.article = {}
    this.updateReadCount(this.$route.params.articleId)
    // this.refreshDiectory()
    // this.refreshMobileDirectory()
  },
  //监听路由id是否发生变化，变化就重新请求
  watch: {
    '$route.params.articleId':function(val, old) {
      if (val !== old) {
        this.tags = []
        this.getArticle(val)
      }
    },
  },
  methods: {
    addCodeLineNumber () {
      // 添加行号
      let blocks = this.$refs.article.querySelectorAll('pre code')
      blocks.forEach((block) => {
        HLJS.highlightBlock(block)
        // 去前后空格并添加行号
        block.innerHTML = '<ul><li>' + block.innerHTML.replace(/(^\s*)|(\s*$)/g, '').replace(/\n/g, '\n</li><li>') + '\n</li></ul>'
      })
    },
    getArticle (articleId) {
      getById(articleId).then(res => {
        if (res.success) {
          this.article = res.data
          this.tags = res.data.articleTagList
          // 更新目录、高亮代码
          this.$nextTick(function () {
            this.addCodeLineNumber()
            this.refreshDiectory()//这个就是更新渲染目录
            // this.refreshMobileDirectory()
            document.title = this.article.articleTitle + ' | Juan的个人博客 '
          })
        }
      })
    },
    /**
     * 修改阅读数
     */
    updateReadCount(id){
      updateReadCount(id).then()
    },

    refreshDiectory () {
      /* eslint-disable*/
        new TOC('article-main-page', {
          'level': 8,
          'top': 0,
          'class': 'list',
          'targetId': 'side-toc'
        })
        /* eslint-disable */
        new TocScrollSpy('article-main-page', 'side-toc', {
          'spayLevel': 8,
          'articleMarginTop': 0
        })
      }
      
    }
};
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .home-content
    width auto
    min-height calc(100vh - 108px)
    @media only screen and (max-width: 768px)
      margin 5px 5px 0 5px
    @media screen and (min-width: 768px)
      margin 10px 10px 0 10px
    @media screen and (min-width: 992px)
      margin 15px 35px 0 35px
    @media screen and (min-width: 1200px)
      width 1200px
      margin 15px auto 0
      margin-bottom 200px
    .layout-left, .layout-right
      padding 0
      @media only screen and (max-width: 768px)
        padding 0
      @media screen and (min-width: 768px)
        padding 0
      @media screen and (min-width: 992px)
        padding 0 10px
      @media screen and (min-width: 1200px)
        padding 0 10px
  /*.live-bg{
    background-image:url({{this.imgUrl}})
  }*/
</style>

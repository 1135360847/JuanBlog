<template>
  <div class="home-content">
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <div class="layout-left">
          <photo-wall></photo-wall>
          <section-title :mainTitle="'博文'" :subTitle="'Articles'">
            <title-menu-filter slot="menu" :menu-filter-list="defaultFilterList"></title-menu-filter>
          </section-title> 

          <article-list-cell v-for="article in articleList" :article="article" :key="article.title" :type="'article'"></article-list-cell>
          <Page class='mt-10 text-right' :total="total" :current='page.currentPage' :page-size='page.pageSize' @on-change="changePage" @on-page-size-change="changeSize" show-elevator show-total/> 
        </div>
      </Col>
      <Col :xs="0" :sm="0" :md="0" :lg="7">
        <div class="layout-right">
          <about></about>
          <recommend></recommend>
          <category-wall style="margin-top: 15px;"></category-wall>
          <tag-wall style="margin-top: 15px;"></tag-wall>
          <!-- <friend-links style="margin-top:15px;"></friend-links> -->
        </div>
        <div class="layout-left">
        </div>
      </Col>
    </Row>
  </div>
</template>

<script type="text/ecmascript-6">
import {getList,getPage} from '@/api/article'
import {DefaultFilterList, DefaultLimitSize} from '@/common/js/const'
import merge from 'lodash/merge' // 合并对象工具

import PhotoWall from '@/components/views/PhotoWall'
import ArticleListCell from '@/components/views/Article/ArticleListCell'
import SectionTitle from '@/components/views/SectionTitle/SectionTitle'
import TitleMenuFilter from '@/components/views/SectionTitle/TitleMenuFilter'
import Recommend from '@/components/views/Recommend'
import About from '@/components/views/About'
import CategoryWall from '@/components/views/CategoryWall'
import TagWall from '@/components/views/TagWall'
import SideToc from '@/components/views/SideToc'

export default {
  data () {
    return {
      articleList: [],
      defaultFilterList: [],
      total: 0, // 数据库中的总记录数
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10, // 每页显示多少条,
        isAsc: false//是否倒序
      },

      searchObj: {}, // 查询表单对象
    }
  },
  components: {
    'photo-wall': PhotoWall,
    'article-list-cell': ArticleListCell,
    'section-title': SectionTitle,
    'title-menu-filter': TitleMenuFilter,
    'about': About,
    'side-toc': SideToc,
    'category-wall':CategoryWall,
    'tag-wall': TagWall,
    'recommend': Recommend,
  },
  created: function () {
    this.searchObj = {}
    this.refreshArticle()
    // this.hello()
  },
  methods: {
    refreshArticle () {
      getPage(Object.assign({
          current: this.page.currentPage,
          size: this.page.pageSize
        }, this.searchObj)).then(response => {
        if (response.success) {
          this.articleList = response.data.records
          this.total = response.data.total
        }
      })
    },

    handleMove (liveModel) {
      liveModel.setParamFloat('PARAM_ANGLE_X', 1)
    },
    changePage (page) {
      this.page.currentPage = page
      this.refreshArticle()
    },
    changeSize (size) {
      this.page.pageSize = size
      this.page.currentPage = 1
      this.refreshArticle()
    },
  }
}
</script>

<style lang="stylus" rel="stylesheet/stylus">
  .live-category{
    color: #9ea7b4;
    width: 200px;
    margin-top: 5px;
    font-size: 10px;
  }
  .live-name{
    color: #9ea7b4;
    width: 200px;
    margin-top: 5px;
    font-size: 10px;
  }
  .live-time{
    color: #9ea7b4;
    width: 200px;
    margin-top: 5px;
    font-size: 10px;
  }

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

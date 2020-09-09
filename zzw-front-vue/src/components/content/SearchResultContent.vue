<template>
  <div class="article-list-content">
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <section-title :mainTitle="'搜索结果'" :subTitle="this.valueType()"> </section-title>
        <Card  class="ivu-card ivu-card-bordered"
                  style="margin-bottom: 10px; cursor: pointer;background-color: #f5f5f5;width: 830px"
                  v-for="article in articleList"
                  :key="article.id">
          <div class="ivu-row">
            <a v-bind:href="['/article/'+ article.id]">
              <div class="ivu-col ivu-col-span-7">
                <!--<div class="live-cover" style="background-image:'url('+ article.articleHeadPic +')"></div>-->
                <div class="live-cover" :style="{background: 'url('+article.articleHeadPic+')',backgroundPosition:'center center ',backgroundSize:'cover'}"></div>
              </div>
              <div class="ivu-col ivu-col-span-15" >
                <div class="live-info">
                  <div class="live-title">{{article.articleTitle}}</div>
                  <div class="live-desc">{{article.articleRemark}}</div>
                </div>
                <div class="live-down">
                  <div class="live-down-left" >
                    <div  class="live-for" v-for="(item,index) in article.articleTagList" :key="item" :name="item"  >
                      <Icon type="ios-pricetag" :color="index | mapTagColor" /> {{ item }}
                    </div>
                  </div>
                  <div class="live-down-right">
                    <div class="live-category"><Icon type="ios-film-outline" class="icon"/>{{article.articleCategoryName}}</div>
                    <div class="live-name"><Icon type="ios-contact" class="icon"/>{{article.managerName}}</div>
                    <div class="live-time"><Icon type="ios-timer-outline" class="icon"/>{{article.gmtCreate}}</div>
                  </div>
                </div>
              </div>
            </a>
          </div>
        </Card>
        <Page class='mt-10 text-right' :total="total" :current='page.currentPage' :page-size='page.pageSize' @on-change="changePage" @on-page-size-change="changeSize" show-elevator show-total/> 
      </Col>
      <Col :xs="0" :sm="0" :md="0" :lg="7">
        <div class="layout-right">
          <recommend></recommend>
          <category-wall style="margin-top: 15px;"></category-wall>
          <tag-wall style="margin-top: 15px;"></tag-wall>
        </div>
      </Col>
    </Row>
  </div>
</template>

<script type="text/ecmascript-6">
// mixin
import {mixin} from '@/utils'
import ArticlePageContent from '@/components/views/Article/ArticlePageContent'
import ArticlePageFooter from '@/components/views/Article/ArticlePageFooter'
import ArticleListCell from '@/components/views/Article/ArticleListCell'
import Recommend from '@/components/views/Recommend'
import CategoryWall from '@/components/views/CategoryWall'
import TagWall from '@/components/views/TagWall'
import SectionTitle from '@/components/views/SectionTitle/SectionTitle'

import {getList,getPage} from '@/api/article'
export default {
  data () {
    return {
      articleList: [],
      total: 0, // 数据库中的总记录数
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10, // 每页显示多少条,
      },
      keywords: '',
      searchObj:{}
    }
  },
  mixins: [mixin],
  created () {
    console.log(1)
    this.searchObj={ keywords:this.$route.query.keywords}
    this.listSearchArticle()
  },
  watch: {
    '$route.query.keywords':function(val, old) {
      if (val !== old) {
        this.listSearchArticle()
      }
    },
  },
  methods: {
    //性别显示转换
    valueType () {
      return this.$route.query.keywords?'关键字&'+this.$route.query.keywords:
      this.$route.query.tagKeywords?'标签&'+this.$route.query.tagKeywords:
      this.$route.query.categoryKeywords?'分类&'+this.$route.query.categoryValuewords:'';
      
    },

    listSearchArticle () {
      console.log(1)
      getPage(Object.assign({
          current: this.page.currentPage,
          size: this.page.pageSize
        }, this.searchObj)).then(res => {
          if(res.success){
            this.articleList = res.data.records
            this.total = res.data.total
          }else{
            this.articleList = null
            this.total = null
          }
      })
    },
    

    changePage (page) {
      this.page.currentPage = page
      this.listArticle()
    },
    changeSize (size) {
      this.page.pageSize = size
      this.page.currentPage = 1
      this.listArticle()
    },
  },
  components: {
    'article-page-content': ArticlePageContent,
    'article-page-footer': ArticlePageFooter,
    'article-list-cell': ArticleListCell,
    'recommend': Recommend,
    'tag-wall': TagWall,
    'section-title': SectionTitle
  }
}
</script>

<style lang="stylus" type="text/stylus" rel="stylesheet/stylus">
  @import "../../common/stylus/theme.styl";
  .article-list-content
    width auto
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

  .ivu-card, .ivu-card-bordered
    border 1px solid #dcdee2
    border-color #e8eaec
  .before {
    box-sizing: border-box;
  }
  .live-cover{
    height: 150px;
    background-repeat: no-repeat;
    background-size: cover;
    background-position: 50%;
    border-radius: 4px;
  }
  .live-info{
    height: 110px;
    padding: 10px
  }

  .live-title{
    font-size: 16px;
    font-weight: 700;
  }
  .live-desc{
    color: #9ea7b4;
    margin-top: 20px;
    line-height:20px
  }

  .live-down{
    margin-top :-10px;
  }

  .live-down-left{
    margin-top :20px;
    float: left
    width: 200px;
  }
  .live-for{
    float: left;
    margin-left : 15px;
  }

  .live-down-right{
    width: 50px;
    margin-left : 400px;
  }
  .icon{
    margin-right: 10px;
  }
</style>

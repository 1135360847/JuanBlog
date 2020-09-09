<template>
  <div class="recommend">
    <panel :title="'推荐阅读'">
      <div slot="content" class="content">
        <div class="top" v-for="recommend in recommendList" :key="recommend.id">
          <!-- <a :href="'/article' + '/' + recommend.id" preventDefault> -->
          <router-link :to="{name:'article',params:{articleId:recommend.id}}">
            <p class="title">{{recommend.articleTitle}}</p>
            <div class="tags">
              <Tag  :color="index | mapTagColor" v-for="(item,index) in recommend.articleTagList" :key="index">{{item}}</Tag>
            </div>
            <p class="info">
              <span class="time"><Icon type="md-time" />{{'：' +recommend.gmtCreate}}</span>
              <!-- <span class="likes"><a><Icon type="md-heart" /> {{recommend.likeNum}} </a></span> -->
              <!-- <span class="comments"><a><Icon type="md-compose"></Icon> {{recommend.commentNum}} </a></span> -->
              <span class="readings"><a><Icon type="md-eye" /> {{recommend.articleReadCount}} </a></span>
            </p>
            <!--<div class="img">-->
              <!--<img :src="topRecommend.cover" alt="">-->
            <!--</div>-->
            <!-- <p class="desc">{{recommend.commentNum | textLineBreak(60)}}</p> -->
          </router-link>
        </div>
        <!--<ul class="others">
          <li v-for="recommend in recommendList" :key="recommend.id">
            <a :href="'/article' + '/' + recommend.id">
              <p class="title">{{recommend.articleName}}</p>
              <p class="info">
                <span class="time">{{recommend.createTime | socialDate }}</span>
                <span class="likes"><a href=""><Icon type="heart"></Icon> {{recommend.likeNum}} </a></span>
                <span class="comments"><a href=""><Icon type="compose"></Icon> {{recommend.commentNum}} </a></span>
                <span class="readings"><a href=""><Icon type="eye"></Icon> {{recommend.readNum}} </a></span>
              </p>
            </a>
          </li>
        </ul>-->
      </div>
    </panel>
  </div>
</template>

<script type="text/ecmascript-6">

import {getList,getPage} from '@/api/article'
import {mixin} from '@/utils/index'
import Panel from '@/components/utils/Panel'
import merge from 'lodash/merge' // 合并对象工具

export default {
  data () {
    return {
      recommendList: [],
      page: {
        currentPage: 1, // 当前页数
        pageSize: 5, // 每页显示多少条,
      },
      searchObj:{}
      // topRecommend: {}
    }
  },
  mixins: [mixin],
  created () {
    this.listRecommend()
  },
  
  methods: {
    listRecommend () {
      let orderBy = {
        pageSize: this.pageSize,
        currentPage: this.currentPage
      }
      let params = merge(orderBy)

      this.searchObj = {
        articleTypeName: '推荐阅读',
      }
      // params = merge(params, this.menuParams)
      getPage(Object.assign({
        current: this.page.currentPage,
        size: this.page.pageSize
      }, this.searchObj)).then(res => {
        if (res.success) {
          this.recommendList = res.data.records
        }
      })
    }
  },
  components: {
    'panel': Panel
  }
}
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "../../common/stylus/index.styl";

  .recommend
    // margin-top 90px
    .content
      padding 5px 20px
    .top, .others
      a
        display block
        overflow hidden
        .tags
          margin-bottom 10px
        .title
          text-align justify
          color $color-gradually-gray-41
          font-size 16px
          line-height 23px
          margin-bottom 5px
        .info
          margin 5px 0 0px
          span
            font-size 13px
            line-height 18px
            font-weight 100
            color $color-secondary-info
            + span
              float right
              margin-left 10px
          a
            display inline-block
            color: #777
            cursor pointer
            &:hover
              color $color-main-primary
              text-decoration underline
        .img
          padding-bottom: 40%
          width: 100%
          height: 0
          margin 5px 0
          overflow hidden
          img
            width 100%
            transition: All 0.4s ease-in-out
            transform: scale(1.0)
            zoom: 1.0
        .desc
          text-align justify
          color $color-secondary-info
          font-size 13px
          line-height 20px
          margin 5px 0 0
        &:hover
          .title
            color $color-main-primary
          img
            transition: All 0.4s ease-in-out
            transform: scale(1.05)
            zoom: 1.05
    .others
      li
        list-style-type none
        margin-top 10px
        padding-top 10px
        border-top 1px solid $color-border

</style>

<template>
  <div class="article-page-header">
    <div class="tags">
      <Tag :color="index | mapTagColor" v-for="(tag , index) in article.tagList" :key="tag.id">{{tag.name}}</Tag>
    </div>
    <p class="title">{{article.articleTitle}}</p>
    <Row>
      <Col :xs="24" :sm="10" :md="10" :lg="10" style="padding-left: 0;padding-right: 0;">
        <p class="info">
          <span class="author"><Icon type="ios-contact" class="icon"/><a href="">{{article.managerName}}</a></span>
          <span class="publish-time">  <Icon type="ios-timer-outline" class="icon"/>{{article.gmtCreate}}</span>
          <span class="publish-time">  <Icon type="ios-film-outline" class="icon"/>{{article.articleCategoryName}}</span>
        </p>
      </Col>
      <Col :xs="24" :sm="14" :md="14" :lg="14" style="padding-left: 0;padding-right: 0;">
        <p class="operate_info">
          <span><a><Icon type="md-eye"/>{{article.articleReadCount}} 阅读</a></span>
          <!--<span><a @click="likePost(article)"><Icon type="heart"></Icon> |收藏</a></span>-->
         <!-- <span><a><Icon type="compose"></Icon>编辑</a></span>-->
        </p>
      </Col>
    </Row>
    <p class="abstract" v-if="article.description">
      {{article.description}}
    </p>
  </div>
</template>

<script type="text/ecmascript-6">
import { mixin } from '@/utils'
export default {
  props: {
    article: {}
  },
  mixins: [mixin],
  methods: {
    likePost (post) {
      this.$http({
        url: this.$http.adornUrl('/article/like/' + post.id),
        method: 'put',
        data: this.$http.adornData()
      }).then(({data}) => {
        if (data && data.code === 200) {
          post.likeNum += 1
          this.$Message.success('点赞成功')
        }
      }).catch((error) => {
        console.log(error)
      })
    }
  }
}
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "../../../common/stylus/index.styl";

  .article-page-header
    text-align left
    padding 25px 5px 10px 5px
    @media only screen and (max-width: 768px)
      padding-top 10px
    @media screen and (min-width: 768px)
      padding-top 10px
    @media screen and (min-width: 992px)
      padding-top 25px
    .tags
      margin-bottom 18px
    .title
      font-size 27px
      line-height 33px
      font-weight 500
      color $color-typegraphy-title
      margin-bottom 23px
      text-align center
    .info
      margin-top 10px
      font-size 14px
      line-height 18px
      font-weight 200
      a
        color: #777
        cursor pointer
        &:hover
          color $color-main-primary
          text-decoration underline
      .publish-time
        margin-left 20px
    .operate_info
      text-align right
      font-size 14px
      margin 15px 0
      @media only screen and (max-width: 768px)
        text-align left
      span
        margin-right 10px
        + span
          margin-left 10px
        a
          cursor pointer
          &:hover
            color $color-main-primary
            text-decoration underline
    .abstract
      color $default-desc-color
      border 1px dashed $default-border-color
      background $default-border-color
      @media only screen and (max-width: $responsive-sm)
        padding 8px
        margin-top 8px
        font-size 13px
        line-height 18px
      @media screen and (min-width: $responsive-sm)
        padding 15px
        margin-top 8px
        font-size 14px
        line-height 24px
      @media screen and (min-width: $responsive-md)
        padding 20px
        margin-top 15px
        line-height 28px
      @media screen and (min-width: $responsive-lg)
        padding 20px
        margin-top 15px
        line-height 28px
  // .icon{
  //   margin-right: 5px;
  // }
</style>

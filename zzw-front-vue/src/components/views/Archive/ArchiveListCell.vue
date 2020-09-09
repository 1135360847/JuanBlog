<template>
  <div class="archive-list-cell">
    <div class="info" >
       <Card  class="ivu">
          <div class="ivu-row" >
            <router-link :to="{name:'article',params:{articleId:post.id}}">
              <div class="ivu-col ivu-col-span-7">
                <!--<div class="live-cover" style="background-image:'url('+ article.articleHeadPic +')"></div>-->
<!--
                <div class="live-cover" :style="{background: 'url('+article.articleHeadPic+')',backgroundPosition:'center',backgroundSize:'100%'}"></div>
-->
                <img class="live-cover" :src="post.articleHeadPic" style="width:100%;">
              </div>
              <div class="ivu-col ivu-col-span-17" >
                <div class="live-info">
                  <div class="live-title">{{post.articleTitle}}</div>
                  <div class="live-desc">{{post.articleRemark}}</div>
                </div>
                <div class="live-down">
                  <div class="live-down-left" >
                    <div  class="live-for" v-for="(item,index) in post.articleTagList" :key="item" :name="item"  >
                      <Icon type="ios-pricetag" :color="index | mapTagColor" /> {{ item }}
                    </div>
                  </div>
                  <div class="live-down-right">
                    <div class="live-name"><Icon type="ios-contact" class="icon"/>{{post.managerName}}</div>
                    <div class="live-time"><Icon type="ios-timer-outline" class="icon"/>{{post.gmtCreate}}</div>
                  </div>
                </div>
              </div>
            </router-link>
          </div>
        </Card>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
import {mixin} from '@/utils'

export default {
  name: 'archive-list-cell',
  props: {
    post: {
      Type: Object
    }
  },
  mixins: [mixin],
  data () {
    return {
      showDesc: false
    }
  },
  methods: {
    seeDesc () {
      this.showDesc = !this.showDesc
    }
  }
}
</script>

<style lang="stylus" type="text/stylus" rel="stylesheet/stylus">
  @import "../../../common/stylus/theme.styl"
  
  .archive-list-cell
    position relative
    padding 5px 0
    &::after
      content ''
      position absolute
      min-height 25px
      top 0
      bottom 0
      left 88px
      width 4px
      background $default-link-hover-color
    .info
      display flex
      font-size 17px
      line-height 25px
      margin-left 130px
      cursor pointer
      @media only screen and (max-width: 720px)
        font-size 13px
      .live-down-right{
        width: 50px;
        margin-left : 300px;
      }
    .ivu-row{
      height: 150px  
    }
</style>

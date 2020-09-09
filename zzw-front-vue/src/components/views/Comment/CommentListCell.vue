<template>
  <div class="comment-list-cell">

    <Row>
      <Col :xs="cellSpan('xs')" :sm="cellSpan('sm')" :md="cellSpan('md')" :lg="cellSpan('lg')" :xl="cellSpan('xl')">
        <div class="comment-main">
          <Row :gutter="8">
            <Col :xs="cellLeftSpan('xs')" :sm="cellLeftSpan('sm')" :md="cellLeftSpan('md')"
                    :lg="cellLeftSpan('lg')" :xl="cellLeftSpan('xl')">
              <div class="avatar">
               <!-- <img src="../../../assets/avatar.png" alt="">-->
              </div>
            </Col>
            <Col :xs="cellRightSpan('xs')" :sm="cellRightSpan('sm')" :md="cellRightSpan('md')"
                    :lg="cellRightSpan('lg')" :xl="cellRightSpan('xl')">
              <div class="content">
                <div class="title">
                  <div class="name">{{comment}}</div>
                 <!-- <span class="reply-icon" :class="theme" v-if="comment.parentNickName">&nbsp;<Icon type="forward"></Icon></span>
                  <span class="reply-name" :class="theme" v-if="comment.parentNickName"><a href="">{{comment}}</a></span>-->
                  <!--<span class="time">{{comment.publishTime | socialDateFormat}}</span>-->
                </div>
                <div class="operate-area" style="margin-left: 700px;border: 1px">
                  <span class="like"><a><Icon type="thumbsup"></Icon>&nbsp;{{comment.likeNum}}</a></span>
                  <span class="unlike"><a><Icon type="thumbsdown"></Icon>&nbsp;{{comment.dislikeNum}}</a></span>
                  <span class="reply"><a @click="showInput"><Icon type="forward"></Icon> 回复</a></span>
                </div>
              </div>
            </Col>
          </Row>
        </div>
      </Col>
    </Row>

   <!-- <iv-modal
      v-model="showEditor"
      title="Common Modal dialog box title"
      @on-ok="ok"
      @on-cancel="cancel">
      <p>Content of dialog</p>
      <p>Content of dialog</p>
      <p>Content of dialog</p>
    </iv-modal>-->
  </div>
</template>

<script type="text/ecmascript-6">
import { mixin } from '@/utils'

const CELL_LEFT_SPAN = {
  'xs': 3,
  'sm': 3,
  'md': 2,
  'lg': 2
}
const CELL_RIGHT_SPAN = {
  'xs': 24 - CELL_LEFT_SPAN['xs'],
  'sm': 24 - CELL_LEFT_SPAN['sm'],
  'md': 24 - CELL_LEFT_SPAN['md'],
  'lg': 24 - CELL_LEFT_SPAN['lg']
}

export default {
  props: {
    comment: {},
    count: '',
    tipText: {
      default: 'View All'
    },
    theme: {
      Type: String,
      default: ''
    }
  },
  mixins: [mixin],
  data () {
    return {
      openEditor: false
    }
  },
  methods: {
    showInput () {
      // console.log((this.$cookie.get('currentManagerId')))
      let currentManager = JSON.parse(sessionStorage.getItem('currentManager'))
      if (currentManager === null) {
        this.$Message.error('您还没有登录，请先去登录')
        setTimeout(() => {
          this.$router.push('/login')
        }, 1300)
        return
      }
      this.openEditor = true
      this.$emit('childFn', this.openEditor)
    },
    cellSpan (size) {
      var span = {}
      span['offset'] = CELL_LEFT_SPAN[size] * this.comment.commentLevel
      span['span'] = 24 - span['offset']
      return span
    },
    cellLeftSpan (size) {
      let span = {}
      span['span'] = CELL_LEFT_SPAN[size]
      return span
    },
    cellRightSpan (size) {
      let span = {}
      span['span'] = CELL_RIGHT_SPAN[size]
      return span
    },
    valueChanged (flag) {
      this.showEditor = flag
    }
  },
  components: {

  },
  // 初始化
  mounted () {

  }
}
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "../../../common/stylus/theme.styl";

  .comment-list-cell
    position relative
    text-align left
    .avatar
      img
        border-radius $border-radius
        width 100%
    .content
      margin 5px 0 8px
      .title
        width 800px
        font-size 0
        margin-bottom 5px
        line-height 18px
        .name
            font-size 15px
            color $color-main-primary
            width 800px
            word-wrap:break-word;
            word-break:break-all;
  .name-tag
          font-size 12px
          background-color $color-secondary-info
          padding 2px 5px
          margin 0 5px
          color #fff
          border-radius $border-radius
        .reply-icon
          font-size 15px
          color $light
        .reply-name
          font-size 15px
          margin 0 5px
          a
            color $dark
            &:hover
              color $color-main-primary
              text-decoration underline

          &.dark-theme
            a
              color $color-gradually-gray-71
              &:hover
                color $color-secondary-warning
                text-decoration underline
        .time
          font-size 13px
          color $light
          margin-left 8px
      .comment-content
        font-size 16px
        line-height 24px
        &.dark-theme
          color $color-gradually-gray-71
    .operate-area
      margin-top 8px
      font-size 14px
      width 800px
      span
        margin-right 10px
      .iv-dropdown-link
        cursor pointer
      .like, .unlike
        color $light
        font-weight 700
      .reply
        cursor pointer
      &.dark-theme
        .iv-dropdown-link
          &:hover
            color $color-secondary-warning
        .reply
          a
            color $color-secondary-warning
    .comment-area
      margin-bottom 10px
      .reply-editor
        margin-top 15px
        height 200px
        transition height 0.7s
        &.spread
          height 300px
      p.comment-tip
        a
          font-size 14px
          &:hover
            color $color-main-primary
</style>

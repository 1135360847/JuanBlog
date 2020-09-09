<template>
  <div class="license-tag">

    <Row>
      <Col :xs="24" :sm="14" :md="14" :lg="14">
        <span  class="live-for" v-for="(item,index) in tags" :key="item" :name="item"  >
          <Tag :checked="false"><Icon type="ios-pricetag" :style="{color: getTagsColor(index)}"/>{{ item }}</Tag>
        </span>
        <!--<p class="operate_info">
          <span><a><Icon type="ios-redo"></Icon>  评论</a></span>
        </p>-->
      </Col>
    </Row>
  </div>
</template>

<script type="text/ecmascript-6">
export default {
  props: {
    tags: Array,
    link: {
      default: 'https://creativecommons.org/licenses/by-sa/4.0/deed.zh'
    },
    type: {
      default: 'CC BY-SA 4.0'
    }
  },
  data(){
    return{
      tagsColor: ['#D52BB3','#3CC48D','#E6E61A'],
    }
  },
  methods:{
    getTagsColor(index){
      return this.tagsColor[index]
    },
  }
}
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "../../common/stylus/theme.styl"

  .license-tag
    margin 10px 0
    height 30px
    font-size 0
    a
      display inline-block
      &:hover
        cursor pointer
        text-decoration none
      .name, .type
        display inline-block
        padding 5px 8px
        font-size 15px
        text-align center
      .name
        background-color $color-main-primary
        color $white
        border 1px solid $color-main-primary
        font-weight 700
      .type
        background-color $white
        color $color-main-primary
        border 1px solid $color-main-primary


</style>

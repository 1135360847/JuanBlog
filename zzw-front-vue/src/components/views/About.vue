<template>
  <div class="about">
    <img class="background" src="http://qg0wn77fd.hn-bkt.clouddn.com/350.jpg" alt="">
      <Tooltip content="弟弟，疾风亦有归途.........." placement="top">
          <!-- A balloon appears when the mouse passes over this text -->
          <img class="avatar" src="http://qg0wn77fd.hn-bkt.clouddn.com/%E8%8E%B2%E5%8D%8E%E7%AB%8B%E7%BB%98%20%E4%BA%9A%E7%B4%A26.png" alt="">
      </Tooltip>
    
    <!-- <p class="name">弟弟</p>
    <p class="desc">疾风亦有归途...</p> -->
    <div class="progresses">
      <Row :gutter="20">
        <Col :span="6">
          <p class="title">Java</p>
        </Col>
        <Col :span="18">
          <Progress stroke-color ="#FFA2D3" status="normal" :hide-info="true" :stroke-width="8" :percent="90" class="bar"></Progress>
        </Col>
      </Row>
      <Row :gutter="20">
        <Col :span="6">
          <p class="title">Vue</p>
        </Col>
        <Col :span="18">
          <Progress stroke-color ="#FFA2D3" status="wrong" :hide-info="true" :stroke-width="8" :percent="70" class="bar"></Progress>
        </Col>
      </Row>
      <Row :gutter="20">
        <Col :span="6">
          <p class="title">docker</p>
        </Col>
        <Col :span="18">
          <Progress stroke-color ="#FFA2D3" status="success" :hide-info="true" :stroke-width="8" :percent="70" class="bar"></Progress>
        </Col>
      </Row>
      <Row :gutter="20">
        <Col :span="6">
          <p class="title">Cloud</p>
        </Col>
        <Col :span="18">
          <Progress stroke-color ="#FFA2D3" status="active" :hide-info="true" :stroke-width="8" :percent="80" class="bar"></Progress>
        </Col>
      </Row>
    </div>

    <ul class="social"> 
      <li> 
        <!-- <div style="float: left;margin-top: 0px" class="desc">联系:</div> -->
        <Tooltip content="QQ" placement="top">
          <a href="http://wpa.qq.com/msgrd?v=3&uin=1135360847&site=qq&menu=yes" target="_blank"><img src="../../assets/icon/qq.png"  alt=""></a>
        </Tooltip>
        <Tooltip content="github主页" placement="top">
          <a href="https://github.com/1135360847" target="_blank"><img src="../../assets/icon/github.png" alt=""></a>
        </Tooltip>
        <Tooltip content="gitee主页" placement="top">
         <a href="https://gitee.com/zzw_520" target="_blank"><img src="../../assets/icon/gitee.png"  alt=""></a>
        </Tooltip>
        <Tooltip content="简书主页" placement="top">
          <a href="https://www.jianshu.com/u/b5852ad39d9a" target="_blank"><img src="../../assets/icon/jianshu.png"  alt=""></a>
        </Tooltip>
        <Tooltip content="QQ群链接" placement="top">
          <a href="#" target="_blank"><img src="../../assets/icon/QQqun.png"  alt=""></a>
        </Tooltip>
       
      </li>
    </ul>
  
  </div>
</template>

<script type="text/ecmascript-6">
export default {

}
</script>

<style lang="stylus" rel="stylesheet/stylus" scoped>
  @import "../../common/stylus/theme.styl";

  .about
    position relative
    text-align center
    border 1px solid $color-border
    padding-bottom 20px
    background-color #fff
    height 417px
    margin-bottom 90px
    img.background
      position absolute
      opacity 0.8
      top 0
      left 0
      right 0
      width 100%
      height 100%
      z-index 0
    img.avatar
      position relative
      margin 40px auto 15px
      width 128px
      height 128px
      // border 5px solid $color-gradually-gray-91
      border-radius 50%
    .name
      position relative
      font-size 24px
      color #fefefe
      line-height 30px
      font-weight 700
    .desc
      position relative
      font-size 20px
      color #fefefe
      line-height 30px
      font-weight 100
    .social
      position relative
      text-align center
      padding 0 20px
      margin-top  30px
      > li
        position relative
        padding 8px
        a
          position relative
          display inline-block
          width: 44px
          height: 44px
          margin: auto
          img
            width 60%
    .line
      position relative
      height 1px
      background-color #e39cda
      margin 10px 30px
    h4
      font-size 19px
      margin 30px 0 20px
      font-weight 600
    .progresses
      padding 0 30px
      margin-right 10px
      p.title
        color $color-main-primary
        height 38px
        line-height 38px
        text-align right
      .bar
        margin 10px 0
</style>

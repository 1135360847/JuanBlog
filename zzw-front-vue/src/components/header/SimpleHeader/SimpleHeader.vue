<template>

  <div class="simple-header">
    <!-- <div class="headerImg" >
      <img src="http://qg0wn77fd.hn-bkt.clouddn.com/%E8%8E%B2%E5%8D%8E%E8%83%8C%E6%99%AF12.jpg"/>
    </div> -->
    <transition name="slide-fade">
      <div id="mobile-bar" v-show="show" >
        <a class="menu-button" ref="menubutton"></a>
        <router-link class="logo" to="/"></router-link>
      </div>
    </transition>

    
    <transition name="slide-fade">
      <div id="header"  v-show="show">
    <router-link id="logo" to="/">
      <img src="../../../assets/logo.png">
      <span class="title">Juan </span>
      <span class="motto"> ....正在送命.....</span>
    </router-link>
    <ul id="nav" >
      <!-- <li><router-link to="/articleList" class="nav-link contribute" ><span class="iconfont icon-icon-test">实验改造</span></router-link></li>
      <li><router-link to="/codes" class="nav-link contribute" ><span class="iconfont icon-fenxiang">祖安分享</span></router-link></li>
      <li><router-link to="/life" class="nav-link contribute" ><span class="iconfont icon-erjiji">程序人生</span></router-link></li> -->

      <li>
        <router-link to="/waluolan" class="nav-link contribute" >
          <svg class="routIcon" aria-hidden="true">
            <use xlink:href="#icon-bianqian"></use>
          </svg>
          瓦罗兰图志
        </router-link>
      </li>
      <li>
        <router-link to="/timeline" class="nav-link contribute" >
          <svg class="routIcon" aria-hidden="true">
            <use xlink:href="#icon-shijian"></use>
          </svg>
          两极反转
        </router-link>
      </li>
      <li>
        <router-link to="/aboutme" class="nav-link contribute" >
          <svg class="routIcon" aria-hidden="true">
            <use xlink:href=" #icon-shenghuofuwu"></use>
          </svg>
          关于蒙多
        </router-link>
      </li>
      <li >
        <!-- <form id="search-form" action="/articles/search"> -->
      <span class="algolia-autocomplete" style="position: relative; display: inline-block; direction: ltr;">
        <input
          type="text" id="search-query-nav" class="search-query st-default-search-input aa-input" name="keywords" v-model="keywords" @keyup.enter="submit(keywords)"
          autocomplete="off" spellcheck="false" role="combobox" aria-autocomplete="list" aria-expanded="false"
          aria-owns="algolia-autocomplete-listbox-0" dir="auto" style="position: relative; vertical-align: top;" placeholder="臭弟弟搜索在这....">
        <pre
          aria-hidden="true"
          style="position: absolute; visibility: hidden; white-space: pre; font-family: system-ui; font-size: 12px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: normal; word-spacing: 0px; letter-spacing: normal; text-indent: 0px; text-rendering: auto; text-transform: none;"></pre>
        <span
          class="aa-dropdown-menu" role="listbox" id="algolia-autocomplete-listbox-0"
          style="position: absolute; top: 100%; z-index: 100; display: none; left: 0px; right: auto;"><div
          class="aa-dataset-1"></div></span></span>
        <!-- </form> -->
      </li>
      <!-- <li><router-link to="/login"  class="nav-link contribute" style="margin-right: 0px;color: #E6E61A">登录</router-link></li> -->
       <!-- v-show="this.manager.id !== 0"-->
      <!--<li><router-link :to="{name:'publish',params:{managerId:manager.id}}"  class="nav-link contribute" style="margin-right: 0px;color: dodgerblue"><span class="iconfont icon-wen">发表文章</span></router-link></li>-->
    </ul>
    </div>
   

    </transition>
    <!-- 适应手机端 -->
    <!-- <sidebar ref="sidebar"></sidebar> -->
 
  </div>
</template>

<script type="text/ecmascript-6">
import {treeDataTranslate} from '@/utils'


export default {

  data () {
    return {
      show: true,
      keywords: '',
      manager:{
        id: 0
      }
    }
  },
  created () {
    this.keywords = this.$route.query.keywords
  },
  mounted: function () {
    // let manager = JSON.parse(sessionStorage.getItem('currentManager'))
    // if (manager !== null){
    //   this.manager = manager;
    // } else {
    //   this.manager = this.manager;
    // }
    this.$nextTick(function () {
      this.initMobileMenu()
    })
    // 给页面绑定滑轮滚动事件
    if (document.addEventListener) { // firefox
      document.addEventListener('DOMMouseScroll', this.watchScroll, false)
    }
    // 滚动滑轮触发scrollFunc方法  //ie 谷歌
    window.onmousewheel = document.onmousewheel = this.watchScroll

  },
  watch:{
  },
  
  methods: {
    

    submit(keywords){
      this.$router.push({path:'/articleSearch',query:{keywords}})
    },
    initMobileMenu () {
      // 显示手机端的菜单
      var sidebar = this.$refs.sidebar
      this.$refs.menubutton.addEventListener('click', function () {
        sidebar.toggleSideBar()
      })
    },
    watchScroll (e) {
      e = e || window.event
      if (e.wheelDelta) {
        if (e.wheelDelta > 0 && this.show === false) { // 当滑轮向上滚动
          this.show = true
        }
        if (e.wheelDelta < 0 && this.show === true) { // 当滑轮向下滚动
          this.show = false
        }
      } else if (e.detail) {
        if (e.detail < 0 && this.show === false) { // 当滑轮向上滚动
          this.show = true
        }
        if (e.detail > 0 && this.show === true) { // 当滑轮向下滚动
          this.show = false
        }
      }
    }
  }
}
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "stylus/header.styl";
   // 添加导航栏显示动画
  .slide-fade-enter-active ,.slide-fade-leave-active {
    transition: all .8s ease;
  }
  .slide-fade-leave-to ,.slide-fade-enter {
    /* .slide-fade-leave-active for below version 2.1.8 */
    transform: translateY(-70px);
    opacity: 0;
  }
  .headerImg
    padding-bottom: 0px
    width: 100%
    height: 800px
    overflow hidden
    img
      width 100%
      height: 800px
      transition: All 0.4s ease-in-out
      transform: scale(1.0)
      zoom: 1.0

  .routIcon
    width 1em;
    height 1em;
    vertical-align -0.15em;
    fill currentColor;
    overflow hidden;
  // #header{
  //   background-image: url("../../../assets/莲华背景2.jpg")  
  // }

</style>

<template>
  <div id="app">
    <router-view v-if="isRouterALive"></router-view>
  </div>
</template>

<script>
export default {
  name: 'app',
  metaInfo: {
    title: 'Juan', // set a title
    meta: [
      {
        // set meta
        name: 'Juan',
        content: 'Juan，欢迎大家交流学习'
      }
    ],
    link: [
      {
        // set link
        rel: 'asstes',
        href: 'http://www.aquestian.cn'
      }
    ]
  },
  created () {
    // setTimeout(() => {
    //   window.L2Dwidget.init({
    //     pluginRootPath: 'static/live2dw/',
    //     pluginJsPath: 'lib/',
    //     pluginModelPath: 'live2d-widget-model-koharu/assets/',
    //     tagMode: false,
    //     debug: false,
    //     model: { jsonPath: '/static/live2dw/live2d-widget-model-koharu/assets/koharu.model.json' },
    //     display: { position: 'right', width: 150, height: 400 },
    //     mobile: { show: true },
    //     log: false
    //   })
    // }, 1000);
    this.initCNZZ()
  },
  provide () {
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      isRouterALive: true
    }
  },
  methods: {
    reload () {
      this.isRouterALive = false
      this.$nextTick(function () {
        this.isRouterALive = true
      })
    },
    initCNZZ () {
      // 添加脚本
      const script = document.createElement('script')
      script.src =
        'https://s9.cnzz.com/z_stat.php?id=1278283813&web_id=1278283813'
      script.language = 'JavaScript'
      script.id = 'cnzz'
      document.body.appendChild(script)
    }
  },

  watch: {
    $route: {
      handler (to, from) {
        setTimeout(() => {
          // 避免首次获取不到window._czc
          if (window._czc) {
            let location = window.location
            let contentUrl = location.pathname + location.hash
            let refererUrl = '/'
            // 用于发送某个URL的PV统计请求，
            window._czc.push(['_trackPageview', contentUrl, refererUrl])
          }
        }, 300)
      },
      immediate: true // 首次进入页面即执行
    }
  }
}
</script>

<style>
@import "assets/css/reset.css";
</style>

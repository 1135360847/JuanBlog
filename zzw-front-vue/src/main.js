import Vue from 'vue'
import httpRequest from '@/utils/request'
import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ViewUI from 'view-design';
// import style
import 'view-design/dist/styles/iview.css';
import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'
import '@/icon/iconfont.css'
import '@/icon/iconfont.js'
import VueCookie from 'vue-cookie' // 挂在在全局了
import VueParticles from 'vue-particles'
import 'mavon-editor/dist/css/index.css'
import mavonEditor from 'mavon-editor'
// import APlayer from '@moefe/vue-aplayer'
import MetaInfo from 'vue-meta-info'

Vue.use(VueCookie)
Vue.use(VueParticles)
Vue.use(MetaInfo)
Vue.use(mavonEditor)
Vue.use(ViewUI);
// Vue.use(APlayer, {
//   defaultCover: 'https://github.com/u3u.png',
//   productionTip: true,
// });

Vue.directive('title', {
  inserted: function (el, binding) {
    document.title = el.dataset.title
  }
})

Vue.prototype.$http = httpRequest // Ajax 请求方法
Vue.prototype.$https = httpRequest // Ajax 请求方法
Vue.prototype.$Modal = ViewUI.Modal
Vue.prototype.$Message = ViewUI.Message
Vue.prototype.$Notice = ViewUI.Notice
Vue.prototype.$loadScript = (script, url, callback) => {
  script = script || document.createElement('script')
  if (script.readyState) {
    // IE浏览器
    script.onreadystatechange = function () {
      if (script.readyState === 'loaded' || script.readyState === 'complete') {
        script.onreadystatechange = null
        callback()
      }
    }
  } else {
    // 其他浏览器
    script.onload = function () {
      callback()
    }
  }
  script.src = url
  document.getElementsByTagName('head')[0].appendChild(script)
}


Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})

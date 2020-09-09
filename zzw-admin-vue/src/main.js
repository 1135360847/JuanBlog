import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import '@/styles/index.scss' // global css

import App from './App'
import router from './router'
import store from './store'
import './utils/dialog'
import '@/icons' // icon
import '@/assets/iconfont/iconfont.css'
import '@/permission' // permission control
import { hasBtnPermission } from './utils/permission' // button permission
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

Vue.use(ElementUI, { size: 'mini'});

Vue.use(mavonEditor);

Vue.config.productionTip = false
Vue.prototype.hasPerm = hasBtnPermission

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})

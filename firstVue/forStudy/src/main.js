// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

//参数序列化
var qs = require('qs')

// 引用axios，并设置基础URL为后端服务api地址
var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8080/wolfServer'
axios.interceptors.request.use(function(config) {
  config.data = qs.stringify(config.data)
  return config
})
axios.interceptors.response.use(function(response) {
  if (response.data.resultCode == '250') {
    router.replace({
      path: '/login',
      query: { redirect: router.currentRoute.fullPath }
    })
  }
  return response
})

// 将API方法绑定到全局
Vue.prototype.$axios = axios

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

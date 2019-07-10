import Vue from 'vue'
import Vuex from 'vuex'

//使用Vuex
Vue.use(Vuex)

//创建仓库实例
const store = new Vuex.store({
  state: {
    userInfo: {}
  },
  getters: {
    checkIsLog(state) {
      if (state.userInfo.userName != null && state.userInfo.userName != '') {
        return true
      } else {
        return false
      }
    }
  },
  mutations: {
    logIn(state, userInfo) {
      state.userInfo = userInfo
    }
  }
})

export default store

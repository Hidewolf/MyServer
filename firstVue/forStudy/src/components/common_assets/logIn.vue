<template>
  <a-modal
    v-model="isVisible"
    title="登陆"
    okText="登陆"
    :maskClosable=false
    :closable=false
  >
    <template slot="footer">
      <a-button
        key="submit"
        type="primary"
        @click="logIn"
      >
        登陆
      </a-button>
    </template>
    <a-layout>
      <a-layout-content class="main_content">
        用户名：
        <a-input
          placeholder="请输入用户名"
          v-model="logInfo.userName"
        >
          <a-icon
            slot="prefix"
            type="user"
          />
          <a-icon
            v-if="logInfo.userName"
            slot="suffix"
            type="close-circle"
            @click="emitEmpty('userName')"
          />
        </a-input>
        密码：
        <a-input
          type="password"
          placeholder="请输入密码"
          v-model="logInfo.psw"
        >
          <a-icon
            slot="prefix"
            type="lock"
          />
          <a-icon
            v-if="logInfo.userName"
            slot="suffix"
            type="close-circle"
            @click="emitEmpty('psw')"
          />
        </a-input>
      </a-layout-content>
    </a-layout>
  </a-modal>
</template>
<script>
export default {
  name: 'logAssets',
  data () {
    return { logInfo: { userName: '', psw: '' } }
  },
  created: function () {
  },
  computed: {
    isVisible () {
      return !this.$store.getters.checkIsLog;
    }
  },
  methods: {
    logIn: function () {
      this.$axios.post('/login/log', this.logInfo).then(successResponse => {
        if (successResponse.data.resultCode == '200') {
          this.$store.dispatch('logIn', successResponse.data.res);
          //this.visible = false;
          var path = this.$route.query.redirect;
          this.$router.replace({ path: path === '/' || path === undefined ? '/' : path })
        } else {
          switch (successResponse.data.resultCode) {// eslint-disable-next-line
            case '502': this.$message.error('用户名或密码不正确！'); break;// eslint-disable-next-line
            case '503': this.$message.error('请输入用户名及密码！'); break;
          }
        }
      })
    },
    emitEmpty: function (id) {
      console.log(id);
      this.logInfo[id] = '';
    }
  }
}
</script>
<style>
.main_content {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  height: 120px;
  background: #fff;
}
</style>


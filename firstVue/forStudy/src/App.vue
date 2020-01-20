<template>
  <a-layout style="min-height: 100vh;max-height: 100vh">
    <a-layout-header></a-layout-header>
    <a-layout>
      <a-layout-sider collapsible>
        <menuAsset />
      </a-layout-sider>
      <a-layout-content>
        <router-view />
        <logAssets />
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script>
import menuAsset from '@/components/common_assets/menu.vue'
import footPart from '@/components/foot.vue'
import logAssets from '@/components/common_assets/logIn.vue'

export default {
  name: 'App1',
  components: { menuAsset, footPart, logAssets },
  data () {
    return {
      loginShow: false
    }
  },
  created: function () {
  },
  methods: {
  }
}
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

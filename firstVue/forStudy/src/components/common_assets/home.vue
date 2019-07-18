<template>
  <div>首页
    <a-button v-on:click="test"> 测试 </a-button>
  </div>
</template>
<script>
export default {
  name: 'homePage',
  methods:{
    test:function(){
      this.$axios.post('/privateCloudDriver/getFileList');
    }
  }
}
</script>

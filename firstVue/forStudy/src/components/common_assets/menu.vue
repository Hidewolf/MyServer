<template>
  <a-menu mode='inline' theme="dark" :defaultSelectedKeys=defaultSelectKey>
    <a-menu-item v-for='(menuItem,menuIndex) in menuList' :key='menuIndex' >
      <router-link :to= 'menuItem.router'/>
      <a-icon :type="menuItem.iconType" />
      <span>{{menuItem.menuName}}</span>
    </a-menu-item>
  </a-menu>
</template>
<script>
export default {
  name: 'menuAsset',
  data(){
    return {menuList:{},defaultSelectKey:['homePage']};
  },
  created(){
    //获取目录信息
    this.menuList.homePage={menuName:'首页',router:'/home',iconType:'home',menuKey:'homePage'};
    this.menuList.cloudDriver={menuName:'云盘',router:'/fileList',iconType:'cloud',menuKey:'cloudDriver'};
  },
  methods:{
  }
}
</script>
<template>
  <a-menu mode='inline' theme="dark" :defaultSelectedKeys="['cloud_driver']">
    <a-menu-item key='cloud_driver' title="云盘">
      <a-icon type="cloud" />
      <span>云盘</span>
    </a-menu-item>
    <a-menu-item key='test' title="测试">
      <a-icon type="exclamation-circle" />
      <span>测试</span>
    </a-menu-item>
  </a-menu>
</template>
<script>
export default {
  name: 'menuAsset'
}
</script>
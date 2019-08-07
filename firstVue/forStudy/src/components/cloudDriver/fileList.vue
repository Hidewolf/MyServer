<template>
  <a-list :dataSource="filesList">
    <a-list-item slot="renderItem" slot-scope="item, index">
      {{index}}
      <div>名称：{{item.fileName}}</div>
      <div>类型：{{item.type}}</div>
    </a-list-item>
  </a-list>
</template>
<script>
export default {
  name: 'fileList',
  data () { return { filesList: [] } },
  created () {
    this.searchFiles();
  },
  methods: {
    searchFiles () {
      this.$axios.post('/privateCloudDriver/getFileList').then(successResponse => {
        var res = successResponse.data;
        if (res.resultCode == '200') {
          this.filesList = successResponse.data.res
        }
      });
    }
  },
}
</script>


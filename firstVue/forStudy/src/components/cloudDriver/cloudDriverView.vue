<template>
  <a-layout>
    <a-layout-header>
      <fileListHead @refreshFilesList="searchFiles"></fileListHead>
    </a-layout-header>
    <a-layout-content>
      <fileList @refreshFilesList="searchFiles" :filesList="filesList"></fileList>
    </a-layout-content>
  </a-layout>
</template>
<script>
import fileList from '@/components/cloudDriver/fileList'
import fileListHead from '@/components/cloudDriver/fileListHead'

export default {
  components: { fileList,fileListHead },
  data () { return { filesList: [{ type: 'dir', fileName: 'test' }, { type: 'file', fileName: 'test.txt' }] } },
  created () {
    this.searchFiles();
  },
  methods: {
    searchFiles (dir) {
      this.$axios.post('/privateCloudDriver/getFileList').then(successResponse => {
        var res = successResponse.data;
        if (res.resultCode == '200') {
          this.filesList = successResponse.data.res
        }
      });
    }
  }
}
</script>

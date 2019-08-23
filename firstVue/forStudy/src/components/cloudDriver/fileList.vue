<template>

  <a-table
    :columns="columns"
    :dataSource="filesList"
  >
    <span>checkBox</span>
    <span>{{type}}</span>
    <span>{{fileName}}</span>
    <span>size</span>
    <span>date</span>
    <span>操作</span>
  </a-table>
</template>
<script>
export default {
  name: 'fileList',
  props: ['filesList'],
  data () {
    return {
      columns: [
        { title: '选择' }, { title: '类型', dataIndex: 'type' }, { title: '文件名', dataIndex: 'fileName' }, { title: '大小' }, { title: '修改时间' }, { title: '操作' }
      ]
    }
  }
}
</script>

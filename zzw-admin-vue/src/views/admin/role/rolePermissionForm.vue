<template>
<el-dialog
  v-dialogDrag
  :title="dialogTitle"
  :visible.sync="dialogVisible"
  
  width="30%"
  :before-close="handleClose">
  <span></span>
  <span slot="footer" class="dialog-footer">
    <el-button @click="handleClose">取 消</el-button>
    <el-button type="primary" :disabled="saveBtnDisabled"  @click="save">保存</el-button>
  </span>
    <div style="margin: 20px 20px">
      <el-tree
          :data="allPermission"
          show-checkbox
          node-key="id"
          ref="tree"
          :props="defaultProps"
          :default-expanded-keys="checkPermissionIdList"
          :default-checked-keys="checkPermissionIdList"
          >
        </el-tree>
    </div>
</el-dialog>
</template>
<script>
import menuApi from '@/api/admin/menu'

export default {
  data() {
    return {
      dialogTitle:'分配内容',
      saveBtnDisabled:false,
      dialogVisible: false,
      allPermission: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      checkPermissionIdList: [],
      roleId:''

    };
  },
  // 监听器
  watch: {
    $route(to, from) {
      this.init()
    }
  },

  created () {
  },
  methods: {
    fetchDataById(roleId){
        menu.toAssign(roleId).then(response => {
            this.data = response.data.children
            var jsonList = JSON.parse(JSON.stringify(this.data))
            debugger;
            var list = []
            this.getJsonToList(list, jsonList[0]['children'])
            this.setCheckedKeys(list)
        })
    },
    //把json数据转成string再转成对象，根据Key获取value数据
    getJsonToList(list, jsonList){
        //遍历这个集合对象，获取key的值
        for(var i = 0; i < jsonList.length; i++){
            if(jsonList[i]['select'] == true && jsonList[i]['level'] == 4){
                list.push(jsonList[i]['id'])
            }
            if(jsonList[i]['children'] != null){ 
              this.getJsonToList(list, jsonList[i]['children'])
            } 
        }
        
    },

    getCheckedNodes() {
      console.log(this.$refs.tree.getCheckedNodes());
    },
    getCheckedKeys() {
      console.log(this.$refs.tree.getCheckedKeys());
    },

    setCheckedKeys(list) {
      this.$refs.tree.setCheckedKeys(list);
    },

    save(){
      this.saveBtnDisabled = true
      var ids = this.$refs.tree.getCheckedKeys().join(",");
      //vue elementUI tree树形控件获取父节点ID的实例
      //node_modules\element-ui\lib\element-ui.common.js
      //25348行修改源码
      menuApi.doAssign(this.roleId, ids).then(response => {
          if(response.success){
              this.$message({
                type:'success',
                message:'保存成功'
              })
              this.rest();
              this.$emit('onSubmit');
            }
      })
    },
     handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.rest();
        })
        .catch(_ => {});
    },
    rest(){
      this.data=[];
      this.checkPermissionIdList = [];
      this.dialogVisible= false;
      this.saveBtnDisabled = false;
    },

  }
};
</script>

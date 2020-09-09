<template>
<el-dialog
  :title="dialogTitle"
  :visible.sync="dialogVisible"
  width="30%"
  :before-close="handleClose">
  <span></span>
  <span slot="footer" class="dialog-footer">
    <el-button @click="handleClose">取 消</el-button>
    <el-button type="primary" :disabled="saveBtnDisabled"  @click="update">保存</el-button>
  </span>

    <div class="app-container">
      <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
      <div style="margin: 15px 0;"></div>
      <el-checkbox-group v-model="checkedRoles" @change="handleCheckedRolesChange">
          <el-checkbox v-for="role in allRoleList" :label="role.id" :key="role.id">{{role.roleName}}</el-checkbox>
      </el-checkbox-group>
    </div>
  </el-dialog>

</template>

<script>
import userApi from '@/api/admin/user'
export default {
    name: 'UserRole',
    data() {
      return {
        dialogTitle: '用户角色',
        checkAll: false,
        checkedRoles: [], //已选中
        allRoleList: [], //所有的
        isIndeterminate: true,
        userId:'',
        saveBtnDisabled: false, // 保存按钮是否禁用,
        dialogVisible: false
      };
    },
    created () {
      this.init()  
    },
    methods: {
      init(){
      },
     
      handleCheckAllChange(val) {
        this.checkedRoles = val ? this.getJsonToList(this.allRoleList,'id') : [];
        this.isIndeterminate = false;
      },
      handleCheckedRolesChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.allRoleList.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.allRoleList.length;
      },
      //把json数据转成string再转成对象，根据Key获取value数据
      getJsonToList(json,key){
        
          //把JSON字符串转成对象
          var list = JSON.parse(JSON.stringify(json));
          //var list = JSON.parse(json)
          var strText = []
          //遍历这个集合对象，获取key的值
          for(var i = 0; i < list.length; i++){
              strText.push(list[i][key])
          }
          return strText;
      },


      update(){
        this.saveBtnDisabled = true // 防止表单重复提交
        var ids = this.checkedRoles.join(",")
        //修改权限
        userApi.saveAssign(this.userId, ids).then(response => {
            if(response.success){
              this.$message({
                type:'success',
                message:'保存成功'
              })
              
              // this.$router.push({ path: '/admin/user/list' })
            }else{
              this.$message({
                type:'error',
                message: response.msg
              })
            }
            this.rest();
            this.$emit('onSubmit')
        })
      },
      rest(){
        this.checkedRoles = [];
        this.allRoleList = [];
        this.dialogVisible= false;
        this.saveBtnDisabled = false;
      },

      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            this.rest();
          })
          .catch(_ => {});
      },
    }
};
</script>

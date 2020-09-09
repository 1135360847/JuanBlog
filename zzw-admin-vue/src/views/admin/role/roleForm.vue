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
    <el-button type="primary" :disabled="saveBtnDisabled"  @click="saveOrUpdate">确 定</el-button>
  </span>
  <div class="app-container">
    <el-form ref="role" :model="role" :rules="validateRules" label-width="120px">
      <el-form-item :error="errorMsg" label="角色名称" prop="roleName">
        <el-input v-model="role.roleName" @blur="check"/>
      </el-form-item>

      <el-form-item label="角色编码" prop="roleCode">
        <el-input v-model="role.roleCode"/>
      </el-form-item>
    </el-form>
  </div>
</el-dialog>
  
</template>

<script>

import roleApi from '@/api/admin/role'

const defaultForm = {
  roleName: '',
  roleCode: '',
  isDelete: 0
}

export default {
  data() {
    return {
      dialogTitle: '',
      errorMsg: '',
      role: defaultForm,
      dialogVisible: false,
      saveBtnDisabled: false, // 保存按钮是否禁用,
      validateRules: {
        roleName: [{ required: true, trigger: 'blur', message: '角色名必须输入' }]
      }
    }
  },

  // 监听器
  watch: {
    $route(to, from) {
    }
  },

  // 生命周期方法（在路由切换，组件不变的情况下不会被调用）
  created() {
  },

  methods: {
    saveOrUpdate() {
      this.$refs.role.validate(valid => {
        if (valid) {
          this.saveBtnDisabled = true // 防止表单重复提交
          if (!this.role.id) {
            this.saveData()
          } else {
            this.updateData()
          }
        }
      })
    },

    // 新增角色
    saveData() {
      roleApi.save(this.role).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            message: '新增成功'
          })
          this.rest();
          this.$emit('onSubmit')
        }
    
      })
    },

    // 根据id更新记录
    updateData() {
      // teacher数据的获取
      roleApi.updateById(this.role).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            message: '修改成功'
          })
          this.rest();
          this.$emit('onSubmit')
        }
        this.rest();
        this.$emit('fetchData')
      })
    },

    // 根据id查询记录
    fetchDataById(id) {
      roleApi.getById(id).then(response => {
        this.role = response.data.item
      })
    },

    check() {
      this.errorMsg = ""; //校验前需情况错误信息
      var checkForm = {
        id: this.role.id,
        roleName: this.role.roleName
      };
      roleApi.check(checkForm).then(response => {
        if (!response.success) {
          this.errorMsg = response.msg; //后台返回提示信息
        }
      });
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.rest();
        })
        .catch(_ => {});
    },
    rest(){
      this.role={
        roleName: '',
        roleCode: '',
        isDelete: 0
      };
      this.errorMsg = '';
      this.dialogVisible= false;
      this.saveBtnDisabled = false;
    },

  }
}
</script>

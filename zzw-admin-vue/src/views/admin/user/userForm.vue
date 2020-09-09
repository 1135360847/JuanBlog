<template>
<el-dialog
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
    <el-form ref="user" label-position="right"  :model="user" :rules="validateRules" label-width="80px">
      <el-form-item :error="errorMsg" label="用户名" prop="username">
          <el-input prefix-icon ="iconfont iconuser-1" v-model="user.username" @blur="check"/>
      </el-form-item>
      <el-form-item label="用户昵称">
          <el-input prefix-icon="iconfont iconnicheng1" v-model="user.nickName"/>
      </el-form-item>
     
      <el-form-item v-if="!user.id" label="用户密码" prop="password">
          <el-input prefix-icon="iconfont iconmima" v-model="user.password"/>
      </el-form-item>
    </el-form>
  </div>

</el-dialog>


</template>

<script>

import userApi from '@/api/admin/user'

const defaultForm = {
  id: '',
  username: '',
  nickName: '',
  password: '',
  isDelete: 0
}

const validatePass = (rule, value, callback) => {
  if (value.length < 6) {
    callback(new Error('密码不能小于6位'))
  } else {
    callback()
  }
}

export default {
  name: 'UserForm',
  data() {
    return {
      user: defaultForm,
      dialogTitle: '',
      saveBtnDisabled: false, // 保存按钮是否禁用,
      dialogVisible: false,
      errorMsg: '',
      validateRules: {
        username: [{ required: true, trigger: 'blur', message: '用户名必须输入' }],
        password: [{ required: true, trigger: 'blur', validator: validatePass }]
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
      this.$refs.user.validate(valid => {
        if (valid) {
          this.saveBtnDisabled = true // 防止表单重复提交
          if (!this.user.id) {
            this.saveData()
          } else {
            this.updateData()
          }
        }
      })
    },

    // 新增人员
    saveData() {
      userApi.save(this.user).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            message: '新增成功'
          })
          this.rest();
          this.$emit('onSubmit')
          // this.$router.push({ path: '/admin/user/list' })
        }
      })
    },

    // 根据id更新记录
    updateData() {
      // teacher数据的获取
      userApi.updateById(this.user).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            message: '修改成功'
          })
          this.rest();
          this.$emit('onSubmit')
          // this.$router.push({ path: '/admin/user/list' })
        }
      })
    },

    // // 根据id查询记录
    // fetchDataById(id) {
    //   userApi.getById(id).then(response => {
    //     this.user = response.data
    //   })
    // },

    check() {
      this.errorMsg = ""; //校验前需情况错误信息
      var checkForm = {
        id: this.user.id,
        username: this.user.username
      };
   
      userApi.check(checkForm).then(response => {
        if (!response.success) {
          this.errorMsg = response.msg; //后台返回提示信息
        }
      });
    },
    rest(){
      this.user={
        username: '',
        nickName: '',
        password: '',
        isDelete: 0
      };
      this.errorMsg = '';
      this.dialogVisible= false;
      this.saveBtnDisabled = false;
    },

    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.rest();
        })
        .catch(_ => {});
    }

  }
}
</script>

<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
         <el-input v-model="searchObj.username" placeholder="用户名"/>
      </el-form-item>

      <el-form-item>
         <el-input v-model="searchObj.nickName" placeholder="用户昵称"/>
      </el-form-item>


      <el-form-item>
        <el-col :span="11">
          <el-date-picker placeholder="创建起始日" v-model="searchObj.startDate" style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
        </el-col>
        <el-col class="line" :span="2"></el-col>
        <el-col :span="11">
          <el-date-picker placeholder="创建截止日" v-model="searchObj.endDate" style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
        </el-col>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 工具条 -->
    <div>
      <el-button type="primary" @click="addUser()" v-if="hasPerm('user.add')">添加</el-button>
      <el-button type="danger" @click="removeRows()" v-if="hasPerm('user.remove')">批量删除</el-button>

    </div>

    <!-- 用户列表 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      style="width: 100%"
      show-header
      @selection-change="handleSelectionChange">

      <el-table-column
        type="selection"
        width="55"
        align="center"
        />

      <el-table-column
        label="序号"
        min-width="70">
        <template slot-scope="scope">
          {{ (page.currentPage - 1) * page.pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="username" label="用户名" min-width="150" />

      <el-table-column prop="nickName" label="用户昵称" min-width="150"/>

      <el-table-column prop="gmtCreate" label="创建时间" min-width="150">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.gmtCreate }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="200">
        <template slot-scope="scope">

          <el-tooltip placement="top">
            <div slot="content">设置角色</div>
              <el-button type="info" size="mini" icon="el-icon-s-custom" @click="editUserRole(scope.row.id)"  v-if="hasPerm('user.assgin')"></el-button>
          </el-tooltip>

          <el-tooltip placement="top">
            <div slot="content">修改</div>
            <el-button type="primary" size="mini" icon="el-icon-edit"  @click="editUser(scope.row.id)" v-if="hasPerm('user.update')"></el-button>
          </el-tooltip>
          
          <el-tooltip placement="top">
            <div slot="content">删除</div>
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)" v-if="hasPerm('user.remove')"></el-button>
          </el-tooltip>
          
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="page.currentPage"
      :total="page.total"
      :page-size="page.pageSize"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      style="padding: 30px 0; text-align: center;"
      layout="sizes, prev, pager, next, jumper, ->, total, slot"
      @current-change="currentChange"
      @size-change="sizeChange"
    />

    <user-form @onSubmit="fetchData()" ref="userFrom" ></user-form>
    <user-role @onSubmit="fetchData()" ref="userRole" ></user-role>

  </div>
</template>

<script>
import user from '@/api/admin/user'
import userForm from './userForm'
import userRole from './userRoleForm'

export default {
  components: {userForm,userRole},
  data() {
    return {
      listLoading: true, // 数据是否正在加载
      list: null, // 讲师列表
      total: 0, // 数据库中的总记录数
      page: {
        total: 0, // 总页数
        currentPage: 1, // 当前页数
        pageSize: 10, // 每页显示多少条,
        isAsc: false//是否倒序
      },

      searchObj: {}, // 查询表单对象
      multipleSelection: [] // 批量选择中选择的记录列表
    }
  },

  // 生命周期函数：内存准备完毕，页面尚未渲染
  created() {
    this.fetchData()
  },

  // 生命周期函数：内存准备完毕，页面渲染成功
  mounted() {
  },

  methods: {

    // 当页码发生改变的时候
    sizeChange(size) {
      this.page.pageSize = size
      this.fetchData()
    },

    // 当页码发生改变的时候
    currentChange(page) {
      this.page.currentPage = page;
      this.fetchData();
    },

    addUser(){
      // this.$router.push({ path: '/admin/user/add' })
      this.$refs.userFrom.dialogTitle = '新增'
      this.$refs.userFrom.dialogVisible = true;
    },

    editUser(id){
      //  // 根据id查询记录
      this.$refs.userFrom.dialogTitle = '修改';
      this.$refs.userFrom.dialogVisible = true;
      user.getById(id).then(response => {
        this.$refs.userFrom.user = response.data
      })
    },
    //分配角色
    editUserRole(id){
      this.$refs.userRole.userId = id;
      this.$refs.userRole.dialogVisible = true;
      user.getAssign(id).then(response => {
          var jsonObj = response.data.assignRoles
          this.$refs.userRole.checkedRoles = this.getJsonToList(jsonObj,"id")
          this.$refs.userRole.allRoleList = response.data.allRoleList
      })
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
    // 加载员工列表数据
    fetchData() {
      // 异步获取远程数据（ajax）
      user.getPage(Object.assign({
          current: this.page.currentPage,
          size: this.page.pageSize
        }, this.searchObj)).then(
        response => {
          this.list = response.data.records
          this.page.total = response.data.total
          // // 数据加载并绑定成功
          this.listLoading = false
        }
      )
    },

    // 重置查询表单
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },

    // 根据id删除数据
    removeDataById(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // promise
        // 点击确定，远程调用ajax
        return user.removeById(id)
      }).then((response) => {
        this.fetchData(this.page)
        if (response.success) {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    // 当表格复选框选项发生变化的时候触发
    handleSelectionChange(selection) {
      this.multipleSelection = selection
    },

    // 批量删除
    removeRows() {
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择要删除的记录!'
        })
        return
      }

      this.$confirm('此操作将永久删除这些记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // promise
        // 点击确定，远程调用ajax
        // 遍历selection，将id取出放入id列表
        var idList = []
        this.multipleSelection.forEach(item => {
          idList.push(item.id)
        })
        // 调用api
        return user.removeRows(idList)
      }).then((response) => {
        this.fetchData(this.page)
        if (response.success) {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

  }
}
</script>
<style scoped>

.el-table {
  margin-top: 15px
}
</style>



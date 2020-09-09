<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="searchObj.roleName" placeholder="角色名称"/>
      </el-form-item>

      <el-form-item>
        <el-input v-model="searchObj.roleCode" placeholder="角色编码"/>
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
      <el-button type="primary" size="mini" @click="addRole()" v-if="hasPerm('role.add')">添加</el-button>
      <el-button type="danger" size="mini" @click="removeRows()" v-if="hasPerm('role.remove')">批量删除</el-button>
    </div>

    <!-- 讲师列表 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      style="width: 100%"
      @selection-change="handleSelectionChange">

      <el-table-column
        type="selection"
        width="55" />

      <el-table-column
        label="序号"
        min-width="70">
        <template slot-scope="scope">
          {{ (page.currentPage - 1) * page.pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>


      <el-table-column prop="roleName" label="角色名称" />

      <el-table-column prop="roleCode" label="角色编码" />

      <el-table-column prop="gmtCreate" label="创建时间" min-width="150">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.gmtCreate }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-tooltip placement="top">
            <div slot="content">设置内容</div>
            <el-button type="info" size="mini" icon="el-icon-s-order" @click="editRolePermission(scope.row.id)" v-if="hasPerm('role.acl')"></el-button>
          </el-tooltip>
          
          <el-tooltip placement="top">
            <div slot="content">修改</div>
            <el-button type="primary" size="mini" icon="el-icon-edit" @click="editRole(scope.row.id)" v-if="hasPerm('role.update')"></el-button>
          </el-tooltip>

          <el-tooltip placement="top">
            <div slot="content">删除</div>
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)" v-if="hasPerm('role.remove')"></el-button>
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
    <role-form @onSubmit="fetchData()" ref="roleForm" ></role-form>
    <role-permission-form @onSubmit="fetchData()" ref="rolePermissionForm" ></role-permission-form>
  </div>
</template>

<script>
import roleApi from '@/api/admin/role'
import menuApi from '@/api/admin/menu'
import roleForm from './roleForm'
import rolePermissionForm from './rolePermissionForm'
export default {
  components: {roleForm,rolePermissionForm},
  data() {
    return {
      listLoading: true, // 数据是否正在加载
      list: null, // 讲师列表
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

    addRole(){
      this.$refs.roleForm.dialogTitle = '新增'
      this.$refs.roleForm.dialogVisible = true;
    },

    editRole(id){
      //  // 根据id查询记录
      this.$refs.roleForm.dialogTitle = '修改';
      this.$refs.roleForm.dialogVisible = true;
      roleApi.getById(id).then(response => {
        this.$refs.roleForm.role = response.data
      })
    },
     //分配内容
    editRolePermission(id){
      this.$refs.rolePermissionForm.roleId = id;
      this.$refs.rolePermissionForm.dialogVisible = true;
      //先把数据置空解决打开权限角色权限有缓存的问题
      this.$refs.rolePermissionForm.allPermission = [];
      menuApi.getAssign(id).then(response => {
        this.$refs.rolePermissionForm.allPermission = response.data.allPermission;
        this.$refs.rolePermissionForm.checkPermissionIdList = response.data.checkPermissionIdList;
      })
    },

    // 加载角色列表数据
    fetchData() {
      // 异步获取远程数据（ajax）
      roleApi.getPage(Object.assign({
          current: this.page.currentPage,
          size: this.page.pageSize
        }, this.searchObj)).then(
        response => {
          this.list = response.data.records
          this.page.total = response.data.total
          // 数据加载并绑定成功
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
      // debugger
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // promise
        // 点击确定，远程调用ajax
        return roleApi.removeById(id)
      }).then((response) => {
        this.fetchData()
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

      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
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
        return roleApi.removeRows(idList)
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


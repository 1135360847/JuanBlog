<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="searchObj.name" placeholder="分类名称"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="searchObj.isEnable" placeholder="禁用状态">
          <el-option label="禁用" value="1"></el-option>
          <el-option label="正常" value="0"></el-option>
        </el-select>
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
      <el-button type="primary" size="mini" @click="add()" v-if="hasPerm('articleCategory.add')">添加</el-button>
      <el-button type="danger" size="mini" @click="removeRows()" v-if="hasPerm('articleCategory.remove')">批量删除</el-button>
    </div>

    <!-- 分类列表 -->
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


      <el-table-column prop="name" label="分类名称" />

      <el-table-column label="禁用状态" >
        <template slot-scope="scope">
          <el-tooltip :content="'Switch value: ' + scope.row.isEnable" placement="top">
            <el-switch
              v-model="scope.row.isEnable"
              active-color="#13ce66"
              inactive-color="#d1d1d1"
              :active-value="0"
              :inactive-value="1"
              @change="changeSwitch($event,scope.row,scope.$index)"
              >
            </el-switch>
          </el-tooltip>
        </template> 
      </el-table-column>


      <el-table-column prop="gmtCreate" label="创建时间" min-width="150">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.gmtCreate }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          
          <el-tooltip placement="top">
            <div slot="content">修改</div>
            <el-button type="primary" size="mini" icon="el-icon-edit" @click="edit(scope.row.id)" v-if="hasPerm('articleCategory.update')"></el-button>
          </el-tooltip>

          <el-tooltip placement="top">
            <div slot="content">删除</div>
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)" v-if="hasPerm('articleCategory.remove')"></el-button>
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

    <!-- 添加功能的窗口 -->
    <el-dialog v-dialogDrag :visible.sync="dialogVisible" :title="dialogTitle">
      <el-form ref="articleCategory" :model="articleCategoryFrom" :rules="addRules" label-width="120px">
        <el-row>
          <el-col :span="11">
            <el-form-item label="分类名称" prop="name" >
              <el-input v-model="articleCategoryFrom.name"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="分类状态" prop="isEnable">
                <el-select v-model="articleCategoryFrom.isEnable" placeholder="分类状态">
                <el-option label="禁用" value="1"></el-option>
                <el-option label="正常" value="0"></el-option>
                </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="restData()">取 消</el-button>
        <el-button type="primary" :disabled="saveBtnDisabled"  @click="saveOrUpdate()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import articleCategoryApi from '@/api/admin/articleCategory'
export default {
  data() {
    return {
        dialogVisible: false,
        dialogTitle:'',
        saveBtnDisabled: false,
        listLoading: true, // 数据是否正在加载
        list: null, // 讲师列表
        articleCategoryFrom: {
          id:null,
          name:'',
          isEnable:''
        },
        page: {
          // 总条数
          total: 0, 
          // 当前页数
          currentPage: 1, 
          // 每页显示多少条,
          pageSize: 10, 
        },
        // 查询表单对象
        searchObj: {}, 
        // 批量选择中选择的记录列表
        multipleSelection: [], 
        //校验规则
        addRules: {
          name: [{ required: true, trigger: 'blur', message: '分类名称' }],
          isEnable: [{ required: true, trigger: 'blur',  message: '分类状态必须选择'}],
        },
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

    add(){
      this.articleCategoryFrom ={
        id:null,
        name:'',
        isEnable:'0'
      },
      this.dialogTitle = '新增'
      this.dialogVisible = true;
    },

    edit(id){
      //  // 根据id查询记录
      this.dialogTitle = '修改';
      this.dialogVisible = true;
      articleCategoryApi.getById(id).then(res => {
        this.articleCategoryFrom = res.data
        this.articleCategoryFrom.isEnable = String(res.data.isEnable)
      })
    },

    // 加载分类列表数据
    fetchData() {

      // 异步获取远程数据
      articleCategoryApi.getPage(Object.assign({
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
        return articleCategoryApi.removeById(id)
      }).then((response) => {
        this.fetchData()
        if (response.success) {
            this.$message({
                type: 'success',
                message: '删除成功!'
            })
        }else{
            this.$message({
                type: 'error',
                message: response.msg
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
        return articleCategoryApi.removeRows(idList)
      }).then((response) => {
        this.fetchData(this.page)
        if (response.success) {
            this.$message({
                type: 'success',
                message: '删除成功!'
            })
        }else{
            this.$message({
                type: 'error',
                message: response.msg
            })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    changeSwitch(data,n,index){
      console.log(n)
      articleCategoryApi.updateById(n).then(res =>{
        if(res.success){
          this.$message({
            message: res.msg,
            type: 'success'
          })
        }
      })
    },
    saveOrUpdate() {
      this.$refs.articleCategory.validate(valid => {
        if (valid) {
          // this.saveBtnDisabled = true // 防止表单重复提交
          if (!this.articleCategoryFrom.id) {
            this.saveData()
          } else {
            this.updateData()
          }
        }
      })
    },

    // 新增
    saveData() {
      articleCategoryApi.save(this.articleCategoryFrom).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            message: '新增成功'
          })
          this.rest();
          this.fetchData();
        }
      })
    },
    rest(){
      this.$refs.articleCategory.resetFields();
      this.saveBtnDisabled = false
      this.dialogVisible = false
    },

    // 修改
    updateData() {
      articleCategoryApi.updateById(this.articleCategoryFrom).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            message: '修改成功'
          })
          this.rest();
          this.fetchData();
        }
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


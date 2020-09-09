<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
         <el-input v-model="searchObj.articleTitle" placeholder="文章标题"/>
      </el-form-item>

      <el-form-item>
         <el-input v-model="searchObj.articleTag" placeholder="文章标签"/>
      </el-form-item>

      <el-form-item>
         <el-input v-model="searchObj.managerName" placeholder="作者"/>
      </el-form-item>
      <!-- //文章类型 -->


      <el-button type="primary" icon="el-icon-search" @click="fetchData()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 工具条 -->
    <div>
      <el-button type="danger" @click="removeRows()" v-if="hasPerm('article.remove')">批量删除</el-button>

    </div>

    <!-- 文章列表 -->
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

      <el-table-column label="文章封面" min-width="150" >
        <template slot-scope="scope">
           <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.articleHeadPic"
            fit="fill"></el-image>
        </template>
      </el-table-column>

      <el-table-column prop="articleTitle" label="文章标题" min-width="150"/>

      <el-table-column label="文章标签" min-width="150">
        <template slot-scope="scope">
          <div slot="reference" class="name-wrapper">
            <el-tag :key="tag" v-for="tag in scope.row.articleTagList">{{tag}}</el-tag>
          </div>
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
            <el-button type="primary" size="mini" icon="el-icon-edit"  @click="edit(scope.row.id)" v-if="hasPerm('article.update')"></el-button>
          </el-tooltip>
          
          <el-tooltip placement="top">
            <div slot="content">删除</div>
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)" v-if="hasPerm('article.remove')"></el-button>
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
    


  </div>
</template>

<script>
import article from '@/api/admin/article'

export default {
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

    add(){
    },

    edit(id){
      console.log(id)
      //  // 根据id查询记录
      this.$router.push(
        {
          path: '/article/form',
          query:{ id:id}
        }
      );
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
    // 加载文章列表数据
    fetchData() {
      // 异步获取远程数据（ajax）
      article.getPage(Object.assign({
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
        return article.removeById(id)
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
        return article.removeRows(idList)
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

.el-tag {
  margin-left: 10px;
}
</style>



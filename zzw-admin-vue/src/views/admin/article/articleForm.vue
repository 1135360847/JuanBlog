<template>
  <div class="app-container">
    <el-form ref="article" label-position="right"  :model="articleFrom" :rules="validateRules"  label-width="100px">

      <el-form-item label="选择封面：" prop="articleHeadPic">
        <el-upload
          class="avatar-uploader"
          :action="uploadUrl"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img v-if="articleFrom.articleHeadPic" :src="articleFrom.articleHeadPic" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

  
      <el-row class="demo-autocomplete">
        <el-col :span="12">
          <el-form-item label="文章类型：" prop="articleCategoryId">
            <el-select v-model="articleFrom.articleCategoryId" placeholder="请选择文章类型">
              <el-option
                v-for="item in articleTypeList"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="文章标签：" prop="articletagList">
            <el-tag
              :key="tag"
              v-for="tag in articletagList"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
              {{tag}}
            </el-tag>
            <el-input
              class="input-new-tag"
              v-if="inputVisible"
              v-model="inputValue"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm"
            >
            </el-input>
            <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="文章标题：" prop="articleTitle">
        <el-input  placeholder="请填写文章标题..." :maxlength="50" v-model="articleFrom.articleTitle" />
      </el-form-item>
      <el-form-item label="文章内容：" prop="articleContent">
        <div>
            <mavon-editor class="editor-wrapper" :ishljs = "true" ref=md @imgAdd="$imgAdd" v-model="articleFrom.articleContent" :toolbars="toolbars" @change="mavonChangeHandle"/>
        </div>
      </el-form-item>

    </el-form>

    <div class="submit-button" slot="footer">
        <el-button type="primary" :disabled="saveBtnDisabled" @click="saveOrUpdate">发布</el-button>
    </div>
  </div>
  

</template>

<script>

import articleCategory from '@/api/admin/articleCategory'
import fileApi from '@/api/admin/file'
import paramApi from '@/utils/param'
import articleApi from '@/api/admin/article'
const defaultForm = {
  id: null,
  articleContent: '',
  articleContentHtml: '',
  articleHeadPic: '',
  articleHeadPicKey: '',
  articleRemark: '',
  articleTitle: '',
  articleTag: '',
  articleTypeId: null,
  articleTags: ''
}

export default {
  data() {
    return {
      articleFrom: defaultForm,
      //标签相关
      inputVisible: false,
      inputValue: '',


      saveBtnDisabled: false,
      errorMsg: '',
      validateRules: {
        articleHeadPic: [{ required: true, trigger: 'blur', message: '封面必须输入' }],
        articleTitle: [{ required: true, trigger: 'blur',  message: '标题必须输入'}],
        articleCategoryId: [{ required: true, trigger: 'blur',  message: '请选择文章类型'}],
        articleContent: [{ required: true, trigger: 'blur',  message: '文章内容为空'}],
        
      },
      //所有文章类型集合
      articleTypeList: [] ,
      articletagList: [],
      //图片显示
      avatarUrl: '',
      uploadUrl: process.env.VUE_APP_BASE_API+'/file',
      //七牛云文件key
      fileKey: '',

      //md相关
      toolbars: {
          bold: true, // 粗体
          italic: true, // 斜体
          header: true, // 标题
          underline: true, // 下划线
          strikethrough: true, // 中划线
          mark: false, // 标记
          superscript: false, // 上角标
          subscript: false, // 下角标
          quote: true, // 引用
          ol: true, // 有序列表
          ul: true, // 无序列表
          link: true, // 链接
          imagelink: true, // 图片链接
          code: true, // code
          table: true, // 表格
          fullscreen: true, // 全屏编辑
          readmodel: false, // 沉浸式阅读
          htmlcode: true, // 展示html源码
          help: false, // 帮助
          /* 1.3.5 */
          undo: true, // 上一步
          redo: false, // 下一步
          trash: false, // 清空
          save: false, // 保存（触发events中的save事件）
          /* 1.4.2 */
          navigation: false, // 导航目录
          /* 2.1.8 */
          alignleft: true, // 左对齐
          aligncenter: true, // 居中
          alignright: true, // 右对齐
          /* 2.2.1 */
          subfield: true, // 单双栏模式
          preview: true, // 预览
          boxShadow: false
        },
    }
  },

  // 监听器
  watch: {
    '$route':function(newUrl,oldUrl){
        if(newUrl!=oldUrl){
          this.rest();
        }
    }
  },

  // 生命周期方法（在路由切换，组件不变的情况下不会被调用）
  created() {
  },

  mounted(){
    this.init();
  },

  methods: {
    //文件上传后执行的操作
    handleAvatarSuccess(res, file) {
      if(res.success){
        this.articleFrom.articleHeadPic = res.data.filePath
        this.articleFrom.articleHeadPicKey = res.data.fileKey
        console.log(file)
        this.articleFrom.articleContent ='!['+file.name+']('+res.data.filePath+')' +'\n'+(this.articleFrom.articleContent?this.articleFrom.articleContent:'')
        console.log(this.articleFrom.articleContent)
      }
    },
    //文件上传前执行的操作
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'||'image/png';
      const isLt2M = file.size / 1024 / 1024 < 10;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG/PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 10MB!');
      }
      //先删除之前的图片
      // if(isJPG && isLt2M){
      //   if(this.articleFrom.articleHeadPicKey){
      //     console.log(this.articleFrom.articleHeadPicKey)
      //     fileApi.deleteByFileKey(this.articleFrom.articleHeadPicKey).then(res =>{
      //       if(!res.success){
      //         this.$message({
      //           type: 'error',
      //           message: '删除原文件失败'
      //         })
      //         return false
      //       }
      //     })
      //   }
      // }
      return isJPG && isLt2M;
    },


    handleClose(tag) {
      this.articletagList.splice(this.articletagList.indexOf(tag), 1);
      this.articleFrom.articleTags = this.articletagList.join(',');
    },

    showInput() {
      console.log(2)
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      console.log(1)
      let inputValue = this.inputValue;
      if (inputValue) {
        this.articletagList.push(inputValue);
        this.articleFrom.articleTags = this.articleFrom.articleTags===''?inputValue:this.articleFrom.articleTags+','+inputValue
      }
      this.inputVisible = false;
      this.inputValue = '';
    },

    //md图片上传
    $imgAdd (pos, $file) {
      var formdata = new FormData();
      formdata.append('file', $file);
      console.log(formdata)
      fileApi.uploadFile(formdata).then(res => {
        if(res.success){
            this.$refs.md.$img2Url(pos, res.data.filePath) 
        }
      })
    },
    //md内容改变
    mavonChangeHandle (context, html) {
      // this.contentxt = html
      this.articleFrom.articleContent = context
      this.articleFrom.articleContentHtml = html
    },
    //获取所有的分类
    getArticleTypeList(){
      let param = {
        isDeleted:0,
        isEnabled:0
      }
      articleCategory.getList(param).then(response => {
         if (response.success) {
           this.articleTypeList = response.data
         }
      })

    },
    getById(){
      //编辑
      let id = this.$route.query.id;
      if(id){
          articleApi.getById(id).then(res =>{
            this.articleFrom = res.data
            console.log(this.articleFrom)
            this.articletagList  = res.data.articleTagList
          })
      }
    },

   saveOrUpdate() {
      this.$refs.article.validate(valid => {
        if (valid) {
          // this.saveBtnDisabled = true // 防止表单重复提交
          if (!this.articleFrom.id) {
            this.saveData()
          } else {
            this.updateData()
          }
        }
      })
    },

    // 新增文章
    saveData() {
      articleApi.save(this.articleFrom).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            message: '发布成功'
          })
          this.rest();
        }
      })
    },

    // 修改文章
    updateData() {
      articleApi.updateById(this.articleFrom).then(response => {
        if (response.success) {
          this.$message({
            type: 'success',
            message: '发布成功'
          })
          this.rest();
          this.$router.push({path: '/article/list'});
        }
      })
    },

    rest(){
      this.articleFrom = {};
      this.articletagList =[];
      this.saveBtnDisabled = false
    },

    init(){
      this.getArticleTypeList();
      this.getById();
    }

  }
}
</script>



<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 120px;
    height: 120px;
    display: block;
  }

   .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    height: 25px;
    line-height: 20px;
    margin-left: 10px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 80px;
    height: 25px;
    margin-left: 10px;
    vertical-align: bottom;
  }
  .editor-wrapper{
    height: 600px;
  }
  .submit-button{
    text-align: center
  }
 
</style>
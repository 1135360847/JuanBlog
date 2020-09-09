<template>
  <div class="category-wall">
    <panel :title="'分类墙'">
      <div slot="content" class="content" v-for="(item,index) in categoryList" :key="index" >
        <Tag class="tag" :color="index | mapTagColor" style="cursor: pointer;color:#666;font-weight:130" :checkable="true" @click.native="submit(item.id,item.name)">
          <Icon type="ios-film-outline"></Icon>
          {{item.name}}
        </Tag>
       
      </div>
    </panel>
  </div>
</template>

<script type="text/ecmascript-6">
// const TagCloud = require('TagCloud');
import Panel from '@/components/utils/Panel'
import {getCategoryList} from '@/api/articleCategory'
import {mixin} from '@/utils/index'

export default {
  data () {
    return {
      categoryList: [],
    }
  },
  computed:{

  },
  mixins: [mixin],
  components: {
    'panel': Panel
  },
  created () {
    this.list();

  },
  mounted(){
  },
  methods: {
    list () {
      let param={
        isDeleted: 0
      }
      getCategoryList(param).then(res => {
          if (res.success) {
            this.categoryList = res.data
            console.log(this.categoryList)
          }
        })
    },
    submit(categoryKeywords,categoryValuewords){
      this.$router.push({path:'/articleSearch',query:{categoryKeywords,categoryValuewords}})
    },
  }
}
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "../../common/stylus/index.styl";

  .category-wall
    .content
      width 150px
      padding 5px 20px
      .tag
        width:100%

</style>

<template>
  <div class="tag-wall">
    <panel :title="'标签墙'">
      <div slot="content" class="content" >
        <Tag v-for="(item,index) in tagList" :key="index" :color="index | mapTagColor" style="cursor: pointer;color:#666;font-weight:130" :checkable="true" @click.native="submit(item)">{{item}}</Tag>
      </div>
    </panel>
  </div>
</template>

<script type="text/ecmascript-6">
// const TagCloud = require('TagCloud');
import Panel from '@/components/utils/Panel'
import {getTagList} from '@/api/article'
import {mixin} from '@/utils/index'

export default {
  data () {
    return {
      tagList: [],
    }
  },
  computed:{

  },
  mixins: [mixin],
  components: {
    'panel': Panel
  },
  created () {
    this.listTag();

  },
  mounted(){
  },
  methods: {
    listTag () {
     getTagList().then(res => {
        if (res.success) {
          this.tagList = res.data
        }
      })
    },
    submit(tagKeywords){
      this.$router.push({path:'/articleSearch',query:{tagKeywords}})
    },
  }
}
</script>

<style lang="stylus" rel="stylesheet/stylus">
  @import "../../common/stylus/index.styl";

  .tag-wall
    .content
      height 200px
      padding 5px 20px
      tag 
        float: left;
  // .live-for {
  //   float: left;
  //   margin-left: 15px;
  // }
</style>

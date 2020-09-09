<template>
  <div class="waluolan-home-content">

 
    <pdf ref="pdf" 
      :src="url" 
      :page="pageNum"
      :rotate="pageRotate"  
      @progress="loadedRatio = $event"
      @page-loaded="pageLoaded($event)" 
      @num-pages="pageTotalNum=$event" 
      @error="pdfError($event)" 
      @link-clicked="page = $event">
    </pdf>
    <div class="tools">
			<Button  @click.stop="prePage" class="mr10"> 上一页</Button>
			<Button  @click.stop="nextPage" class="mr10"> 下一页</Button>
			<Button  @click.stop="clock" class="mr10"> 顺时针</Button>
			<Button  @click.stop="counterClock" class="mr10"> 逆时针</Button>
    	{{pageNum}}/{{pageTotalNum}}
		</div>
  </div>
</template>

<script type="text/ecmascript-6">
import TimeLineHeader from '@/components/views/TimeLine/TimeLineHeader'
import ArchiveListCell from '@/components/views/Archive/ArchiveListCell'
import ArticleListCell from '@/components/views/Article/ArticleListCell'
import ArchiveListTimeTitle from '@/components/views/Archive/ArchiveListTimeTitle'
import Recommend from '@/components/views/Recommend'
import TagWall from '@/components/views/TagWall'
import pdf from 'vue-pdf'
import Pie from '@/components/views/Comment/charts/pie'
import timeline from '@/components/views/Comment/shengyaTime/timeline'
import timelineItem from '@/components/views/Comment/shengyaTime/timeline-item'
import {getTimeLine} from '@/api/article'
export default {
  data () {
    return {
        url: "/static/瓦洛兰图志·卷一.pdf",
				pageNum: 1,
				pageTotalNum: 1,
				pageRotate: 0,
				// 加载进度
				loadedRatio: 0,
				curPageNum: 0,
    }
  },
  components: {
    'shengya-time': timeline,
    'shengya-time-item': timelineItem,
    'article-list-cell': ArticleListCell,
    'timeline-header': TimeLineHeader,
    'archive-list-time-title': ArchiveListTimeTitle,
    'archive-list-cell': ArchiveListCell,
    'recommend': Recommend,
    'tag-wall': TagWall,
    'chart-pie':Pie,
    pdf
  },
  created () {
  },
  mounted () {
  },
  methods: {
      // 上一页函数，
			prePage() {
				var page = this.pageNum
				page = page > 1 ? page - 1 : this.pageTotalNum
				this.pageNum = page
			},
      // 下一页函数
			nextPage() {
				var page = this.pageNum
				page = page < this.pageTotalNum ? page + 1 : 1
				this.pageNum = page
			},
      // 页面顺时针翻转90度。
			clock() {
				this.pageRotate += 90
			},
      // 页面逆时针翻转90度。
			counterClock() {
				this.pageRotate -= 90
			},
      // 页面加载回调函数，其中e为当前页数
			pageLoaded(e) {
				this.curPageNum = e
			},
      // 其他的一些回调函数。
			pdfError(error) {
				console.error(error)
			},
  }
}
</script>

<style lang="stylus" rel="stylesheet/stylus" scope>
  .waluolan-home-content
    margin-top 0px
    width auto
    min-height calc(100vh - 108px)


</style>
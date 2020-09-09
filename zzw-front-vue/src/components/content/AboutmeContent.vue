<template>
  <div class="home-content">
    <Row>
      <Col :xs="24" :sm="24" :md="24" :lg="17">
        <div class="aboutme-left">
          <div class="layout-left">

            <div style="background-color: #fff;">
                <Card>
                  <span class="shengyaTitle">个人简介</span>

                  <div style=" #fff; margin-top: 10px"  align="center">
                    <img class="avatar" src="http://qg0wn77fd.hn-bkt.clouddn.com/%E8%8E%B2%E5%8D%8E%E7%AB%8B%E7%BB%98%20%E5%8D%83%E7%8F%8F.png" alt="">
                  </div>
                  <div style=" #fff; margin-top: 10px">
                    <Tag color="orange">个人标签：</Tag><span>Java SpringCloud redis MongoDB开发工程师 avue Docker开发工程师 Linux Mysql mybatis-plus jpa swagger .....</span>
                  </div>
                  <div style=" #fff; margin-top: 10px">
                    <Tag color="primary">学历学位：</Tag><span>本科</span>
                  </div>
                  <div style=" #fff; margin-top: 10px">
                    <Tag color="success">自我评价：</Tag><span>专业从事服务器后台工作，精通java后台服务的开发，熟悉前端页面的开发和运作流程，熟悉linux项目部署等操作</span>
                  </div>
                  <div style=" #fff; margin-top: 10px">
                    <Tag color="error">大学专业：</Tag><span>通信工程</span>
                  </div>
                </Card>
            </div>
            <Divider />
            <div style="background-color: #fff;">
              <Row >
                <Col :xs="0" :sm="0" :md="0" :lg="12"  >
                  <Card>
                    <chart-pie style="height: 300px;" :value="pieData" text="工作技能"></chart-pie>
                  </Card>
                </Col>
                <Col :xs="0" :sm="0" :md="0"  :lg="12" >
                  <Card>
                    <chart-pie style="height: 300px;" :value="aihaoData" text="兴趣爱好"></chart-pie>
                  </Card>
                </Col>
              </Row>
            </div>
      
            <Divider />
            <div style="background-color: #fff; margin-bottom: 50px">
            <Card>
              <span class="shengyaTitle">生涯</span>
              <Divider />
              <shengya-time>
                <shengya-time-item>
                  <p class="shengyaTime">2020年</p>
                  <Card>
                    <p class="shengyaTime">学习</p>
                    <br/>
                    <p class="shengyaContent">努力在公司奋斗,并在空余时间做一些自己喜欢的小项目，当然大多数是借鉴别人的</p>
                  </Card>
                </shengya-time-item>
                <shengya-time-item>
                  <p class="shengyaTime">2019年</p>
                  <Card>
                    <p class="shengyaTime">跳槽</p>
                    <br/>
                    <p class="shengyaContent">公司地址迁移很远,拿到新的公司offer</p>
                  </Card>
                </shengya-time-item>
                <shengya-time-item>
                  <p class="shengyaTime">2018年</p>
                  <Card>
                    <p class="shengyaTime">实习结束</p>
                    <br/>
                    <p class="shengyaContent">大四实习结束,拿到另一家公司的offer</p>
                  </Card>
                </shengya-time-item>
                <shengya-time-item>
                  <p class="shengyaTime">2017年</p>
                  <Card>
                    <p class="shengyaTime">第一次工作</p>
                    <br/>
                    <p class="shengyaContent">大四实习,拿到某厂的java实习生工作</p>
                  </Card>
                </shengya-time-item>
              </shengya-time>
            </Card>
            </div>
          </div>
         
        </div>
      </Col>
      
      <Col :xs="0" :sm="0" :md="0" :lg="7">
        <div class="layout-right">
          <recommend></recommend>
          <category-wall style="margin-top: 15px;"></category-wall>
          <tag-wall style="margin-top: 15px;"></tag-wall>
        </div>
      </Col>
    </Row>

  </div>
</template>

<script type="text/ecmascript-6">
import TimeLineHeader from '@/components/views/TimeLine/TimeLineHeader'
import ArchiveListCell from '@/components/views/Archive/ArchiveListCell'
import ArticleListCell from '@/components/views/Article/ArticleListCell'
import ArchiveListTimeTitle from '@/components/views/Archive/ArchiveListTimeTitle'
import Recommend from '@/components/views/Recommend'
import TagWall from '@/components/views/TagWall'

import Pie from '@/components/views/Comment/charts/pie'
import timeline from '@/components/views/Comment/shengyaTime/timeline'
import timelineItem from '@/components/views/Comment/shengyaTime/timeline-item'
import {getTimeLine} from '@/api/article'

export default {
  data () {
    return {
      timelineList: [],
      pieData: [
        { value: 335, name: '数据库' },
        { value: 310, name: '前端' },
        { value: 234, name: '运维' },
        { value: 135, name: '设计' },
        { value: 1548, name: '后端' }
      ],

      aihaoData: [
        { value: 1000, name: '游戏' },
        { value: 500, name: '运动' },
        { value: 500, name: '开发' },
        { value: 400, name: '唱歌' }
      ],

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
    'chart-pie':Pie
  },
  created () {
    this.listTimeline()
  },
  mounted () {
    var h = document.body.clientHeight - 60 - 35 - 30;
    document.getElementsByClassName('aboutme-left')[0].style.height = h + 'px'
  },
  methods: {
    listTimeline () {
      getTimeLine().then(res => {
        if (res.success) {
          this.timelineList = res.data
        }
      })

    },
    selectCategory (categoryId) {
      this.page = 0
    }
  }
}
</script>

<style lang="stylus" rel="stylesheet/stylus" scope>
  .home-content
    width auto
    min-height calc(100vh - 108px)
    @media only screen and (max-width: 768px)
      margin 5px 5px 0 5px
    @media screen and (min-width: 768px)
      margin 10px 10px 0 10px
    @media screen and (min-width: 992px)
      margin 15px 35px 0 35px
    @media screen and (min-width: 1200px)
      width 1200px
      margin 15px auto 0
      margin-bottom 200px
    .layout-left, .layout-right
      padding 0
      @media only screen and (max-width: 768px)
        padding 0
      @media screen and (min-width: 768px)
        padding 0
      @media screen and (min-width: 992px)
        padding 0 10px
      @media screen and (min-width: 1200px)
        padding 0 10px
  .shengyaTitle
    font-size 24px
    color #FFA2D3    
  .shengyaTime
    font-size 14px
    color #FFA2D3  
  img.avatar
      position relative
      margin 20px auto 15px
      width 128px
      height 128px
      // border 5px solid $color-gradually-gray-91
      border-radius 50%  
  // .aboutme-left
  //   background-image:url({{this.imgUrl}})
</style>
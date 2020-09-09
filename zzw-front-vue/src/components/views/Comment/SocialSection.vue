<template>
  <div class="social-section">
    <div id="gitalk-container" v-if="show"></div>
    <!--<div id="SOHUCS" :sid="type+postId"></div>-->
  </div> 
</template>

<script type="text/ecmascript-6">
import BrowseMore from '@/components/views/BrowseMore'

export default {
  props: {
  
    postId: Number
  },
  data () {
    return {
      gitHubName: '1135360847',
      show: null
    }
  },
  components: {
    'browse-more': BrowseMore
  },
  watch: {
    postId (value) {
      this.show = value
      this.$nextTick(() => {
        // eslint-disable-next-line no-undef
        var gitalk = new Gitalk({
          clientID: '7163e4a3f4adff503f2a',
          clientSecret: '23b0cc8ea35f688de875ff03f49b143576a105cd',
          repo: 'juan-comment',
          owner: this.gitHubName,
          labels: [this.gitHubName],
          admin: [this.gitHubName],
          id: location.pathname, // Ensure uniqueness and length less than 50
          distractionFreeMode: false // Facebook-like distraction free mode
        })
        gitalk.render('gitalk-container')
      })
    }
  },
  mounted () {
    console.log(location.pathname)
  },

}
</script>

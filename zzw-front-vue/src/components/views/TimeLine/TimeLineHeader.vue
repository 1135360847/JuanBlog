<template>
  <div class="timeline-header">
    <section-title :mainTitle="'存档时光'" :subTitle="'TimeLine'">
      <!--<title-menu-timeline slot="menu"></title-menu-timeline>-->
    </section-title>
  </div>
</template>

<script type="text/ecmascript-6">
import SectionTitle from '@/components/views/SectionTitle/SectionTitle'
import TitleMenuTimeLine from '@/components/views/SectionTitle/TitleMenuTimeLine'

export default {
  components: {
    'section-title': SectionTitle,
    'title-menu-timeline': TitleMenuTimeLine
  }
}
</script>

<style lang="stylus" rel="stylesheet/stylus">
</style>

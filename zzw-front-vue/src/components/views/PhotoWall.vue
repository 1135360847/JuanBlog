<template>
  <div class="photowall">
    <Carousel autoplay :model="0" loop>
        <CarouselItem>
          <div class="big-photo">
            <a href="">
              <img src="http://qg0wn77fd.hn-bkt.clouddn.com/photowall_pic_1.jpg" alt="">
            </a>
          </div>
        </CarouselItem>
        <CarouselItem>
          <div class="big-photo">
            <a href="">
              <img src="http://qg0wn77fd.hn-bkt.clouddn.com/photowall_pic_2.jpg" alt="">
            </a>
          </div>
        </CarouselItem>
        <CarouselItem>
          <div class="big-photo">
            <a href="">
              <img src="http://qg0wn77fd.hn-bkt.clouddn.com/photowall_pic_3.jpg" alt="">
            </a>
          </div>
        </CarouselItem>

    </Carousel>
  </div>
</template>

<script type="text/ecmascript-6">

</script>

<style lang="stylus" rel="stylesheet/stylus" scoped>
  .photowall
    .big-photo
      padding-bottom: 50%
      width: 100%
      height: 0
      overflow hidden
      a
        display: block
        width: 100%
        img
          width 100%
          transition: All 0.4s ease-in-out
          transform: scale(1.0)
          zoom: 1.0
      &:hover
        img
          transition: All 0.4s ease-in-out
          transform: scale(1.05)
          zoom: 1.05
    .small-photo
      padding-bottom: 100%;
      width: 100%;
      height: 0;
      overflow hidden
      a
        display: block
        width: 100%
        img
          width 100%
          transition: All 0.4s ease-in-out
          transform: scale(1.0)
          zoom: 1.0
      &:hover
        img
          transition: All 0.4s ease-in-out
          transform: scale(1.05)
          zoom: 1.05

</style>

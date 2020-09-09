<template>
    <div style="height: 75vh;overflow-y: auto;overflow-x: hidden;">
        <pdf :src="src"></pdf>
    </div>
</template>

<script type="text/ecmascript-6">
import pdf from 'vue-pdf'
export default {
    components: {pdf},
    data(){
        return{
            src: '',
        }
    },

    methods: {
        //预览pdf
        previewPDF(url){
            this.src = pdf.createLoadingTask(url)
        },
    }
}
</script>

<style lang="stylus" rel="stylesheet/stylus">

</style>

<template>
    <div class="infoBg" v-if="showPic">
        <div class="infoImg"></div>
    </div>
    <div v-if="nowRouter === '/record'" style="margin-left:10%;margin-top:8%">
        <div style="color:#fff;text-align: left;letter-spacing: 0.23rem;">
            <p>客戶姓名：<span v-if="infoData.length > 0">{{ infoData[0] }}</span></p>
            <p>未付餘額：<span v-if="infoData.length > 0">{{ infoData[1] }}</span></p>
        </div>
    </div>
    <div v-else-if="nowRouter === '/updateRecord'" style="margin-left:10%;margin-top:8%">
        <div style="color:#fff;text-align: left;letter-spacing: 0.23rem;">
            <p>客戶姓名：<span v-if="infoData.length > 0">{{ infoData[0] }}</span></p>
            <p>維修日期：<span v-if="infoData.length > 0">{{ infoData[1] }}</span></p>
        </div>
    </div>
</template>
<script lang="ts">
import { defineComponent, onMounted, ref, watchEffect } from 'vue';
import { useRouter, useRoute } from 'vue-router';
export default defineComponent({
    components: {

    },
    setup() {
        const router = ref(useRouter());
        const route = ref(useRoute());
        const nowRouter = ref('');
        const showPic = ref(true);
        const infoData: any = ref([]);
        onMounted(() => {
            // 

        })
        watchEffect(() => {
            nowRouter.value = router.value.currentRoute.fullPath;
            if (nowRouter.value === '/record' || nowRouter.value === '/updateRecord') {
                showPic.value = false;
            } else {
                showPic.value = true;
            }
            infoData.value = route.value.params.infoData
            console.log("infoView===>", router.value.currentRoute.fullPath)
            console.log("infoData===>", infoData.value)

        })
        return {
            showPic,
            infoData,
            nowRouter
        }
    }
});
</script>
<style>
.infoBg {
    height: 100%;
    width: 100%;
    /* background: #D7D7D7;
    border-radius: 20px 20px 0 0; */
}

.infoImg {
    height: 100%;
    width: 100%;
    background-image: url('@/assets/logo2.png');
    background-position: center;
    background-size: 70%;
    background-repeat: no-repeat;
}
</style>
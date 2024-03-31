<template>
    <div class="indexBg">
        <el-container style="height:100%;">
            <el-header style="height:13%">
                <div style="width:30%;float:left;height:100%;">
                    <infoView />
                </div>
                <div style="width:70%;float:right;height:100%;display:flex;align-items:center;justify-content:right">
                    <menuBar />
                </div>

            </el-header>
            <el-main style="height:87%">
                <mainView />
            </el-main>
        </el-container>
    </div>
</template>
<script lang="ts">
import { defineComponent, reactive } from 'vue';
import menuBar from '@/views/Index/menuBar.vue';
import mainView from '@/views/Index/mainView.vue'
import infoView from '@/views/Index/infoView.vue'
export default defineComponent({
    components: {
        mainView,
        menuBar,
        infoView
    },
    setup() {

        return {

        }
    }
});
</script>
<style>
.indexBg {
    width: 100%;
    height: 100%;
    background: #454545
}
</style>
<style scoped>
.el-header {
    --el-header-padding: 0;
}

.el-main {
    --el-main-padding: 20px 30px 0 30px;
}
</style>
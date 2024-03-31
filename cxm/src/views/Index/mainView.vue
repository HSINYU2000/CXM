<template>
    <div class="mainBg">

        <router-view></router-view>
    </div>
</template>
<script lang="ts">
import { defineComponent, reactive } from 'vue';

export default defineComponent({
    components: {

    },
    setup() {

        return {

        }
    }
});
</script>
<style>
.mainBg {
    height: 100%;
    width: 100%;
    background: #D7D7D7;
    border-radius: 20px 20px 0 0;
}
</style>
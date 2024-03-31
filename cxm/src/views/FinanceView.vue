<template>
    <div>財務管理</div>
</template>
<script lang="ts">
import { defineComponent, reactive, ref, onMounted } from 'vue';
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'
import axios from '@/axios/index';
import { formContextKey, type FormInstance, type FormRules } from 'element-plus'
export default defineComponent({
    setup() {
        const store = useStore();
        const router = useRouter();
        const route = useRoute();
        onMounted(async () => {
            await getDaySum();
        })
        const getDaySum = () => {
            axios.post('/getDaySum', {}).then((res: any) => {
                console.log('getDaySum', res.RDate)
            }).catch((e) => console.log(e))
        }
        return {

        }
    }
});
</script>
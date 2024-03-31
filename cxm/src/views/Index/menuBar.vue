<template>
    <div style="display:flex;flex-direction: column;">
        <div style="text-align:right;width:98%;color:#FFF;letter-spacing: 0.3rem;font-weight: bold;">{{ userName + '，' +
            userRole }}</div>
        <div
            style="background:#1E1E1E;height:35%;display: flex;align-items: center;padding-left:1.5%;border-radius: 40px 0 0 40px">

            <div v-for="r, index in routerList" :key="index" style="display:inline-block;width:130px">
                <router-link class="routerLink" :to="r.NAME">{{ r.FUNC_NAME }}</router-link>
            </div>
            <div
                style="border-left: 1px solid #fff;height: 100%;display: flex;align-items: center;justify-content: center;margin-left:2%">
                <el-button @click="logout()"
                    style="font-size:16px;height:100%;width:70px;border-radius:0px;border:none;background:#676767;color:#fff">登出</el-button>
            </div>


        </div>
    </div>
</template>
<script lang="ts">
import { defineComponent, onMounted, reactive, ref } from 'vue';
import axios from '@/axios/index';
import { useStore } from 'vuex'
export default defineComponent({
    components: {

    },
    setup() {
        const routerList: any = ref([])
        const store = useStore();
        const userName = ref('');
        const userRole = ref('')
        onMounted(() => {
            const employee = store.getters['employee'];
            routerList.value = employee.funcList;
            userName.value = employee.NAME
            userRole.value = employee.ROLE
        })
        const logout = () => {
            axios.post('http://localhost:8777/getCustomer', {}).then((res: any) => {
                console.log(res)
            })
        }
        return {
            routerList,
            userName,
            userRole,
            logout
        }
    }
});
</script>
<style>
.routerLink {
    color: #fff;
    text-decoration: none;

}
</style>
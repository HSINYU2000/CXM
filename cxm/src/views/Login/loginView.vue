<template>
    <div class="loginImage"></div>
    <div class="loginTop">

        <div class="login">
            <div class="logo">

            </div>
            <div style="height:15%;">

            </div>
            <div class="loginWrite">
                <el-form :model="form" label-position="left" label-width="65px">
                    <div style="width:50%;margin-left:20%;margin-bottom:5%">

                        <!-- <el-col :span="10" :offset="6"> -->
                        <el-form-item label="帳號">
                            <el-input v-model="form.acc" />
                        </el-form-item>
                        <!-- </el-col> -->

                    </div>
                    <div style="width:50%;margin-left:20%;margin-top:5%">
                        <!-- <el-col :span="10" :offset="6"> -->
                        <el-form-item label="密碼">
                            <el-input v-model="form.pass" type="password" />
                        </el-form-item>
                        <!-- </el-col> -->
                    </div>
                </el-form>
            </div>

            <div style="margin-top:15%;">
                <el-button @click="login()">登入</el-button>
            </div>
        </div>

    </div>
</template>
<script lang="ts">
import { defineComponent, reactive, onMounted } from 'vue';
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import bcrypt from 'bcryptjs';
import axios from '@/axios';
import { ElMessage } from 'element-plus'

export default defineComponent({
    setup() {
        const store = useStore();
        const router = useRouter();
        const form = reactive({
            acc: '0113',
            pass: '123'
        })
        onMounted(() => {
            const salt = bcrypt.genSaltSync(10);
            const hash = bcrypt.hashSync('123', salt)
            console.log('密碼＝＝＝>', hash)
        })
        const login = () => {
            axios.post('/login', form).then((res: any) => {
                console.log('res', res.data)
                if (res.data.length > 0) {
                    const getPazz = res.data[0].PASS
                    console.log('==>', res.data)
                    if (bcrypt.compareSync(form.pass, getPazz)) {
                        ElMessage({
                            message: '登入成功',
                            type: 'success',
                        })
                        store.dispatch('actions_employee', res.data[0]);
                        store.dispatch('actions_login', true);
                        router.push({ name: 'customer' })
                    } else {
                        ElMessage({
                            message: '密碼錯誤',
                            type: 'error',
                        })
                    }
                } else {
                    ElMessage({
                        message: '帳號錯誤',
                        type: 'error',
                    })
                }

            })

        }
        return {
            form,
            login
        }
    }
});
</script>
<style>
.loginTop {
    position: absolute;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.8);
    top: 0;
    display: flex;
    /* 水平置中 */
    justify-content: center;
    /* 垂直置中 */
    align-items: center;
}

.loginImage {
    width: 100%;
    height: 100%;
    background-size: cover;
    background-image: url('@/assets/login.jpg');


}


.login {
    background: rgba(255, 255, 255, 0.5);
    width: 550px;
    height: 550px;
    border-radius: 50%;
}

.logo {
    height: 30%;
    width: 100%;
    background-position: center;
    background-size: contain;
    background-repeat: no-repeat;
    background-image: url('@/assets/login_logo.png');
}

.loginWrite {
    /* width: 100%; */
    /* display: flex; */
    /* 水平置中 */
    /* justify-content: center; */
    /* 垂直置中 */
    /* align-items: center; */
}
</style>
<style scoped>
.el-button {
    border-radius: 15px;
    width: 20%;
    border: 2px solid #000;
    background: linear-gradient(270deg, #FFF 0%, #C90202 70.93%);
    color: #fff;
    box-shadow: 4px 4px 4px 0px rgba(0, 0, 0, 0.25);
    height: 37px;
    font-size: 16px;
}

.el-button:hover {
    border: 2px solid #000;
    background: linear-gradient(270deg, #FFF 0%, #C90202 70.93%);
    color: #fff;
    opacity: 0.7
}

:deep(.el-form-item__label) {
    color: #000;
    font-weight: bold;
    font-size: 18px;
}

:deep(.el-input) {
    --el-input-border-color: transparent;
    --el-input-focus-border-color: #000;
}

:deep(.el-input__wrapper) {
    border-radius: 10px;
    box-shadow: 4px 4px 4px 0px rgba(0, 0, 0, 0.25);
}
</style>
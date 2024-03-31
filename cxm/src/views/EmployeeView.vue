<template>
    <el-row justify="center" align="middle" style="padding:20px">
        <el-table :data="employeeData" style="width:90%">
            <el-table-column prop="NAME" label="姓名">

            </el-table-column>
            <el-table-column prop="PHONE" label="手機">

            </el-table-column>
            <el-table-column prop="ACC" label="帳號">

            </el-table-column>
            <el-table-column prop="BIRTH" label="生日">

            </el-table-column>
            <el-table-column prop="ROLE" label="職等">

            </el-table-column>
            <el-table-column prop="DATE" label="入職日期">

            </el-table-column>
            <el-table-column align="right" width="280">
                <template #header>
                    <el-button class="empBtn" @click="handleAdd()" style="background-color: #6D98BA;">新增員工</el-button>
                </template>
                <template #default="scope">
                    <el-button class="empBtn" @click="handleEdit(scope.$index, scope.row)"
                        style="background-color: #5C9EAD;">編輯</el-button>
                    <el-button class="empBtn" @click="handleDelete(scope.$index, scope.row)"
                        style="background-color: #D16666;">刪除</el-button>
                    <el-button class="empBtn" @click="handleReset(scope.$index, scope.row)"
                        style="background-color: #DD6031;">修改密碼</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog v-model="dialogVisible" :title="dialogTitle" width="70%">
            <el-form :model="form" :rules="rules" label-width="88px" ref="ruleFormRef" :show-message="false">
                <el-row v-if="dialogTitle !== '修改密碼'">
                    <el-col :span="8">
                        <el-form-item label="員工姓名" prop="name" required>
                            <el-input v-model="form.name" placeholder="要填正確" :validate-event="false" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="聯絡電話" prop="phone" required>
                            <el-input v-model="form.phone" placeholder="要填正確" :validate-event="false" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="出生日期" prop="birth" required>
                            <el-date-picker v-model="form.birth" type="date" format="YYYY-MM-DD" value-format="YYYYMMDD" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row v-if="dialogTitle !== '修改密碼'">
                    <el-col :span="8">
                        <el-form-item label="員工帳號" prop="acc" required>
                            <el-input v-model="form.acc" placeholder="要填正確" :validate-event="false"
                                :disabled="dialogTitle == '編輯員工'" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="角色" prop="role" required>
                            <el-select v-model="form.role">
                                <el-option v-for="item in roleOpt" :key="item.value" :label="item.label"
                                    :value="item.value" />
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="入職日期" prop="date" required>
                            <el-date-picker v-model="form.date" type="date" format="YYYY-MM-DD" value-format="YYYYMMDD" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row v-if="dialogTitle == '修改密碼'">
                    <el-col :span="8">
                        <el-form-item label="原始密碼" prop="pass1" required>
                            <el-input v-model="form.pass1" placeholder="要填正確" type="password" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="修改密碼" prop="pass2" required>
                            <el-input v-model="form.pass2" placeholder="要填正確" type="password" :validate-event="false" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="確認密碼" prop="pass3" required>
                            <el-input v-model="form.pass3" placeholder="要填正確" type="password" :validate-event="false" />
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button class="empBtn" @click="handleSumbit(ruleFormRef)"
                        style="background-color: #5C9EAD;color:#fff">確認</el-button>
                    <el-button class="empBtn" @click="dialogVisible = false" style="background-color: #D16666;color:#fff">
                        取消
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </el-row>
</template>
<script lang="ts">
import { defineComponent, reactive, ref, onMounted } from 'vue';
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'
import axios from '@/axios/index';
import bcrypt from 'bcryptjs';
import { formContextKey, type FormInstance, type FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
export default defineComponent({
    setup() {
        const store = useStore();
        const router = useRouter();
        const route = useRoute();
        const employeeData = ref([])
        const dialogVisible = ref(false);
        const dialogTitle = ref('')
        const ruleFormRef = ref<FormInstance>()
        const roleOpt: any = ref([])
        const date = new Date();
        let day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
        let month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
        let year = date.getFullYear();
        let currentDate = `${year}${month}${day}`;
        const form = reactive({
            name: '',
            acc: '',
            pass1: '',
            pass2: '',
            pass3: '',
            birth: '',
            phone: '',
            date: '',
            role: ''
        })
        const checkPass = (rule: any, value: any, callback: any) => {
            if (value === '') {
                callback(new Error('不得為空'))
            } else {
                axios.post('/checkPass', { pass: form.pass1, acc: form.acc }).then((res: any) => {

                    if (bcrypt.compareSync(form.pass1, res.Pass)) {
                        callback()
                    } else {
                        ElMessage({
                            message: '原始密碼錯誤',
                            type: 'error'
                        })
                        callback(new Error('原始密碼錯誤'))
                    }
                }).catch((e: any) => {
                    console.log(e)
                })

            }
        }
        const rules = reactive<FormRules>({
            pass1: [{ validator: checkPass, trigger: 'blur' }]
        })

        onMounted(async () => {
            await getEmployee();
            await getRole();
        })
        const getEmployee = () => {
            axios.post('/getEmployee', {}).then((res: any) => {
                console.log('employee', res.data)
                employeeData.value = res.data
            }).catch((e: any) => {
                console.log(e)
            })
        }
        const getRole = () => {
            axios.post('/getRole', {}).then((res: any) => {
                console.log('getRole', res.data)
                roleOpt.value = res.roleOpt
            }).catch((e: any) => {
                console.log(e)
            })
        }
        const handleAdd = () => {
            dialogTitle.value = '新增員工'
            dialogVisible.value = true;
            form.name = ''
            form.acc = ''
            // form.pass = ''
            // form.pass2 = ''
            form.birth = ''
            form.phone = ''
            form.date = currentDate
            form.role = 'L02'
        }
        const handleEdit = (index: any, row: any) => {
            dialogTitle.value = '編輯員工'
            dialogVisible.value = true;
            form.name = row.NAME
            form.acc = row.ACC
            // form.pass = row.PASS
            // form.pass2 = ''
            form.birth = row.BIRTH
            form.phone = row.PHONE
            form.date = row.DATE
            form.role = row.ROLE
        }
        const handleDelete = (index: any, row: any) => {
            dialogTitle.value = '刪除'
            axios.post('/delEmployee', {
                name: form.name,
                birth: form.birth,
                acc: form.acc,
                phone: form.phone,
                date: form.date,
                role: form.role,

            })
                .then(async (res: any) => {
                    console.log(res)
                    if (res.result == '1') {
                        await getEmployee();
                        dialogVisible.value = false;
                    }
                    showMess(res.result)

                }).catch((e: any) => {
                    console.log(e)
                })
        }
        const handleReset = (index: any, row: any) => {
            dialogTitle.value = '修改密碼'
            dialogVisible.value = true;
            form.acc = row.ACC
            form.name = row.NAME
            form.phone = row.PHONE
            form.pass1 = ''
            form.pass2 = ''
            form.pass3 = ''

        }
        const handleSumbit = (formEl: FormInstance | undefined) => {
            if (!formEl) return
            formEl.validate((valid) => {
                if (valid) {
                    if (dialogTitle.value == '修改密碼') {
                        if (form.pass2 === form.pass3) {
                            const salt = bcrypt.genSaltSync(10);
                            const hash = bcrypt.hashSync(form.pass2, salt)
                            axios.post('/passReset', {
                                name: form.name,
                                acc: form.acc,
                                phone: form.phone,

                                pass: hash
                            })
                                .then(async (res: any) => {
                                    console.log(res)
                                    if (res.result == '1') {
                                        await getEmployee();
                                        dialogVisible.value = false;
                                    }
                                    showMess(res.result)

                                }).catch((e: any) => {
                                    console.log(e)
                                })
                        }
                    } else if (dialogTitle.value == '新增員工') {
                        //預設密碼
                        const salt = bcrypt.genSaltSync(10);
                        const hash = bcrypt.hashSync('123', salt)
                        // console.log('密碼＝＝＝>', hash)
                        axios.post('/addEmployee', {
                            name: form.name,
                            acc: form.acc,
                            birth: form.birth,
                            phone: form.phone,
                            date: form.date,
                            role: form.role,
                            pass: hash
                        })
                            .then(async (res: any) => {
                                console.log(res)
                                if (res.result == '1') {
                                    await getEmployee();
                                    dialogVisible.value = false;
                                }
                                showMess(res.result)


                            }).catch((e: any) => {
                                console.log(e)
                            })
                    } else if (dialogTitle.value == '編輯員工') {
                        axios.post('/editEmployee', {
                            name: form.name,
                            birth: form.birth,
                            acc: form.acc,
                            phone: form.phone,
                            date: form.date,
                            role: form.role,

                        })
                            .then(async (res: any) => {
                                console.log(res)
                                if (res.result == '1') {
                                    await getEmployee();
                                    dialogVisible.value = false;
                                }
                                showMess(res.result)

                            }).catch((e: any) => {
                                console.log(e)
                            })
                    }
                } else {
                    console.log('error submit!')
                    return false
                }
            })
        }
        const showMess = (errNum: any) => {
            console.log('showMess', errNum)
            if (errNum == '0') {
                ElMessage({
                    message: dialogTitle.value.substring(0, 2) + '失敗',
                    type: 'error',
                })
            } else if (errNum == '1') {

                ElMessage({
                    message: dialogTitle.value.substring(0, 2) + '成功',
                    type: 'success',
                })
            } else if (errNum == '2') {
                ElMessage({
                    message: '帳號重複',
                    type: 'error',
                })
            }
        }
        return {
            handleAdd,
            handleEdit,
            handleDelete,
            handleReset,
            handleSumbit,
            showMess,
            employeeData,
            dialogVisible,
            dialogTitle,
            form,
            ruleFormRef,
            roleOpt,
            rules
        }
    }
});
</script>
<style scoped>
.empBtn {
    border-radius: 10px;
    border: 1px solid #000;
    color: #fff;
    font-weight: 600;
}

.empBtn:hover {
    opacity: 0.8;
}

:deep(.el-table) {
    --el-table-header-bg-color: #EAE0D5;
    /* --el-table-border: 1px solid #000; */
}

:deep(.el-table__inner-wrapper::before) {
    border-top: 1px solid #000;
}



:deep(.el-table .el-table__cell) {
    /* border: 1px solid #000; */
    /* border-bottom: none; */
    /* border-right: none; */
    border-top: 1px solid #000;
    border-left: 1px solid #000;
    color: #000;
}

:deep(.el-table .el-table__header .el-table__cell) {
    text-align: center;
    font-size: 16px;
}

:deep(.el-table .el-table__cell.is-right) {
    border-right: 1px solid #000;
    text-align: center;
}

:deep(.el-table thead) {
    color: #000;
}
</style>
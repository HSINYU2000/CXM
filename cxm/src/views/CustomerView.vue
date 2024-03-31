<template>
    <el-scrollbar>

        <div style="height:40px">

        </div>
        <el-row justify="center" align="middle" style="padding:10px">
            <el-table :data="customerData" style="width:90%">
                <el-table-column prop="Name" label="客戶姓名">
                </el-table-column>
                <el-table-column prop="Plate" label="車牌號碼">
                </el-table-column>
                <el-table-column prop="MStyle" label="機車型號">
                </el-table-column>
                <el-table-column prop="Phone" label="聯絡電話">
                </el-table-column>
                <el-table-column align="right" width="280">
                    <template #header>
                        <el-button class="cusBtn" style="background-color: #6D98BA;"
                            @click="handleAdd(ruleFormRef)">新增客戶</el-button>
                    </template>
                    <template #default="scope">
                        <el-button class="cusBtn" style="background-color: #5C9EAD;"
                            @click="handleEdit(scope.$index, scope.row)">編輯</el-button>
                        <el-button class="cusBtn" style="background-color: #DD6031;"
                            @click="handleRecord(scope.$index, scope.row)">維修紀錄</el-button>
                        <el-button class="cusBtn" style="background-color: #D16666;"
                            @click="handleDelete(scope.$index, scope.row)">刪除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-row>

        <el-dialog v-model="dialogVisible" :title="dialogTitle" width="70%">
            <el-form :model="form" label-width="88px" ref="ruleFormRef" :show-message="false">

                <el-row>
                    <el-divider content-position="left">客戶資料</el-divider>
                </el-row>
                <el-row>
                    <el-col :span="8">
                        <el-form-item label="客戶姓名" prop="name" required>
                            <el-input v-model="form.name" placeholder="要填正確" :validate-event="false" />
                        </el-form-item>
                    </el-col>
                    <!-- <el-col :span="8">
                        <el-form-item label="車牌號碼" prop="plate" required>
                            <el-input v-model="form.plate" placeholder="要填正確" />
                        </el-form-item>
                    </el-col> -->
                    <el-col :span="8">
                        <el-form-item label="出生日期" prop="birth">
                            <!-- <el-input v-model="form.birth" /> -->
                            <el-date-picker v-model="form.birth" type="date" format="YYYY-MM-DD"
                                value-format="YYYY-MM-DD" />
                        </el-form-item>
                    </el-col>
                    <!-- </el-row>
                <el-row> -->
                    <el-col :span="8">
                        <el-form-item label="聯絡電話" prop="phone" required>
                            <el-input v-model="form.phone" placeholder="要填正確" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <!-- <el-form-item label="初訪日期">
                            <el-input v-model="form.first" />
                        </el-form-item> -->
                    </el-col>
                </el-row>

                <!-- <el-row> -->
                <el-divider content-position="left" style="margin:10px 0">機車資料

                </el-divider>
                <!-- </el-row> -->
                <div v-for="item, index in form.moto" :key="index">

                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="車牌" :prop="'moto.' + index + '.plate'" required>
                                <el-input v-model="item.plate" :disabled="item.plate_disabled" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="廠牌" :prop="'moto.' + index + '.label'">
                                <el-input v-model="item.label" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="車型" :prop="'moto.' + index + '.style'" required>
                                <el-input v-model="item.style" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="引擎號碼" :prop="'moto.' + index + '.engine'">
                                <el-input v-model="item.engine" />
                            </el-form-item>
                        </el-col>

                    </el-row>
                    <el-row>
                        <el-col :span="4">
                            <el-form-item label="顏色" :prop="'moto.' + index + '.color'">
                                <el-input v-model="item.color" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="4">
                            <el-form-item label="排氣量" :prop="'moto.' + index + '.cc'">
                                <el-input v-model="item.cc" />
                            </el-form-item>

                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="初訪日期" :prop="'moto.' + index + '.first'" required>
                                <el-date-picker v-model="item.first" type="date" format="YYYY-MM-DD"
                                    value-format="YYYY-MM-DD" />
                            </el-form-item>
                        </el-col>
                        <el-col :span="4">
                            <el-form-item label="購車" :prop="'moto.' + index + '.buy'">
                                <!-- <el-input v-model="form.buy" /> -->
                                <el-radio-group v-model="item.buy" @change="chooseRadio(item.buy, index)">
                                    <el-radio label="N">否</el-radio>
                                    <el-radio label="Y">是</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="金額" :prop="'moto.' + index + '.dollar'">
                                <el-input v-model="item.dollar" :disabled="item.dollar_disabled" />
                            </el-form-item>
                        </el-col>
                    </el-row>


                    <!-- <el-row> -->
                    <el-divider content-position="left" style="margin:12px 0">
                        <el-button class="cusBtn2" @click="plusMoto(index)"
                            style="background-color: #5C9EAD;color:#fff;border:1px #000 solid;" :icon="Plus" size="small"
                            circle />
                        <el-button class="cusBtn2" @click="minusMoto(index)"
                            style="background-color: #D16666;color:#fff;border:1px #000 solid;" :icon="Minus" size="small"
                            circle />
                    </el-divider>
                    <!-- </el-row> -->

                </div>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="備註" class="noteItem" prop="note">
                            <el-input :rows="2" type="textarea" v-model="form.note"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button class="cusBtn2" @click="handleSumbit(ruleFormRef)"
                        style="background-color: #5C9EAD;color:#fff">確認</el-button>
                    <el-button class="cusBtn2" @click="dialogVisible = false"
                        style="background-color: #D16666;color:#fff;border:none">
                        取消
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </el-scrollbar>
</template>
<script lang="ts">
import { defineComponent, onMounted, ref, reactive, nextTick } from 'vue';
import { Plus, Minus } from '@element-plus/icons-vue'
import axios from '@/axios/index';
import { useRouter } from 'vue-router';
import { formContextKey, type FormInstance, type FormRules } from 'element-plus';
import commonFun from '@/function/commonFun';

export default defineComponent({
    components: {

    },
    setup() {
        const router = useRouter();
        const customerData: any = ref([]);
        const dialogVisible = ref(false);
        const dialogTitle = ref('');
        const ruleFormRef = ref<FormInstance>()
        const form = reactive({
            name: '',
            // plate: '',
            birth: '',
            phone: '',
            note: '',
            moto: [{
                plate: '',
                first: '',
                label: '',
                style: '',
                color: '',
                engine: '',
                cc: '',
                buy: 'N',
                dollar: '',
                dollar_disabled: true,
                plate_disabled: false
            }]
        })
        onMounted(async () => {
            await getCustomer();

            console.log("customerData", customerData.value)
        })
        const getCustomer = () => {
            let ayMotoData: any = [];
            axios.post('/getCustomer', {}).then((res: any) => {
                console.log(res)
                customerData.value = res.data;
                customerData.value.forEach((data: any) => {
                    data.Plate = data.allPlate.join(',');

                    ayMotoData = [];
                    data.allPlate.forEach((plate: any) => {
                        ayMotoData.push(data.Moto.find((moto: any) => plate === moto.plate)?.style);
                    })
                    data.MStyle = ayMotoData.join(',');
                })
            })
        }
        const chooseRadio = (val: any, index: any) => {
            if (val === 'Y') {
                form.moto[index].dollar_disabled = false;
            } else {
                form.moto[index].dollar_disabled = true;
            }
        }
        const handleAdd = (formEl: any) => {
            dialogVisible.value = true;
            dialogTitle.value = '新增客戶';

            form.name = '';
            // form.plate = '';
            form.birth = '';
            form.phone = '';
            form.note = '';
            form.moto = [{
                plate: '',
                first: '',
                label: '',
                style: '',
                color: '',
                engine: '',
                cc: '',
                buy: 'N',
                dollar: '',
                dollar_disabled: true,
                plate_disabled: false
            }]

        }
        const handleEdit = (index: any, row: any) => {
            dialogVisible.value = true;
            dialogTitle.value = '編輯客戶';
            // nextTick(() => {
            form.name = row.Name;
            // form.plate = row.Plate;
            form.birth = row.Birth;
            form.phone = row.Phone;
            form.moto = row.Moto;

            ruleFormRef.value?.validate((valid: any) => {
                if (valid) {
                    console.log('submit!')
                } else {
                    console.log('error submit!')
                    return false
                }
            })
            // })

            // form.first = row.First;
            // form.label = row.MLabel;
            // form.style = row.MStyle;
            // form.color = row.MColor;
            // form.engine = row.MEngine;
            // form.cc = row.MCc;
            // form.buy = row.MHere;
            // form.dollar = row.MDollar;
            // form.note = row.Notes + row.MNotesx;
        }
        const handleRecord = async (index: any, row: any) => {
            let notPay = '？';
            notPay = await commonFun.getNotPay(row.Plate)
            console.log('handleRecord', notPay)
            router.push({ name: 'record', params: { plate: row.Plate, infoData: [row.Name, notPay] } });
        }
        const handleDelete = (index: any, row: any) => {
            console.log('delete Customer===>' + row)
            axios.post('/deleteCustomer', {
                moto: row.Moto
            }).then(async (res: any) => {
                console.log(res)
                await getCustomer();

            })
        }

        const handleSumbit = (formEl: FormInstance | undefined) => {
            if (!formEl) return
            formEl.validate((valid) => {
                if (valid) {
                    console.log('submit!')
                    axios.post('/updateCustomer', {
                        name: form.name,
                        birth: form.birth,
                        phone: form.phone,
                        note: form.note,
                        moto: form.moto

                    }).then(async (res: any) => {
                        console.log(res)
                        await getCustomer();
                        dialogVisible.value = false;
                    }).catch((e: any) => {
                        console.log(e)
                    })
                } else {
                    console.log('error submit!')
                    return false
                }
            })
        }
        const plusMoto = (index: any) => {
            const newMoto = {
                plate: '',
                first: '',
                label: '',
                style: '',
                color: '',
                engine: '',
                cc: '',
                buy: 'N',
                dollar: '',
                dollar_disabled: true,
                plate_disabled: false
            }
            form.moto.splice(index + 1, 0, newMoto);
        }
        const minusMoto = (index: any) => {
            if (form.moto.length > 1) {
                form.moto.splice(index, 1);
            }

        }
        const resetForm = (formEl: FormInstance | undefined) => {
            if (!formEl) return
            formEl.resetFields()
        }
        return {
            customerData,
            dialogVisible,
            dialogTitle,
            form,
            ruleFormRef,
            resetForm,
            chooseRadio,
            handleAdd,
            handleEdit,
            handleRecord,
            handleDelete,
            handleSumbit,
            plusMoto,
            minusMoto,
            Plus,
            Minus
        }
    }
});
</script>
<style scoped>
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

.cusBtn {
    border-radius: 10px;
    border: 1px solid #000;
    color: #fff;
    font-weight: 600;
}

.cusBtn:hover {
    opacity: 0.8;
}

.cusBtn2:hover {
    opacity: 0.8;
}

:deep(.el-radio-group) {
    flex-wrap: nowrap;
}

:deep(.noteItem .el-form-item__label) {
    line-height: 20px;
}

:deep(.el-radio-group .el-radio) {
    margin-right: 5px;
}

:deep(.el-form-item__label) {
    font-weight: bold;
}
</style>
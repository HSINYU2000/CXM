<template>
    <el-tabs v-model="activeName" class="plate-tabs" @tab-change="handleTabChange">

        <el-tab-pane v-for="plate in ayPlate" :key="plate" :label="plate" :name="plate">
            <el-table :data="recordData" border>
                <el-table-column prop="RDate" label="日期">

                </el-table-column>
                <el-table-column prop="TYPE" label="維修類別">

                </el-table-column>
                <el-table-column prop="KM" label="累積里程">

                </el-table-column>
                <el-table-column prop="payOff" label="結清">

                </el-table-column>
                <el-table-column align="right" width="280">
                    <template #header>
                        <el-button class="RecBtn" style="background-color: #6D98BA;" @click="handleAdd()">新增紀錄</el-button>
                        <el-button class="RecBtn" style="background-color: #8a8a8a;"
                            @click="handleKm(nowTab)">新增里程</el-button>
                    </template>
                    <template #default="scope">
                        <el-button class="RecBtn" style="background-color: #5C9EAD;"
                            @click="handleEdit(scope.$index, scope.row)">編輯</el-button>
                        <el-button class="RecBtn" style="background-color: #DD6031;"
                            @click="handleCheck(scope.$index, scope.row)">查看</el-button>
                        <el-button class="RecBtn" style="background-color: #D16666;"
                            @click="handleDelete(scope.$index, scope.row)">刪除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-dialog v-model="dialogVisible" :title="dialogTitle" width="30%">
                <el-form :model="form" ref="ruleFormRef2" :show-message="false">
                    <el-form-item label="選擇日期" prop="date" required>
                        <!-- <el-input v-model="form.birth" /> -->
                        <el-date-picker v-model="form.recordDate" type="date" format="YYYY-MM-DD"
                            value-format="YYYY-MM-DD" />
                    </el-form-item>
                    <div v-if="dialogTitle === '新增里程'">
                        <el-form-item label="新增里程" prop="km" :required="dialogTitle === '新增里程'">
                            <el-input v-model="form.km" style="width:67.5%" />
                        </el-form-item>
                        <el-table :data="kmData" v-if="dialogTitle = '新增里程'" class="kmTable">
                            <el-table-column prop="kmDate" label="日期">

                            </el-table-column>
                            <el-table-column prop="km" label="公里數">

                            </el-table-column>
                            <el-table-column align="right" width="70">
                                <template #default="scope">
                                    <el-button :icon="CloseBold" circle class="kmDel" size="small"
                                        @click="handleDeleteKm(scope.$index, scope.row)"></el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </el-form>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button class="RecBtn" @click="handleSumbit()"
                            style="background-color: #5C9EAD;color:#fff">確認</el-button>
                        <el-button class="RecBtn" @click="dialogVisible = false"
                            style="background-color: #D16666;color:#fff;">
                            取消
                        </el-button>
                    </span>
                </template>
            </el-dialog>
        </el-tab-pane>
    </el-tabs>
</template>
<script lang="ts">
import { defineComponent, onMounted, reactive, ref } from 'vue';
import { useStore } from 'vuex'
import { CloseBold } from '@element-plus/icons-vue'

import { useRouter, useRoute } from 'vue-router'
import { FormInstance, type TabsPaneContext } from 'element-plus'
import axios from '@/axios/index';

export default defineComponent({
    setup() {
        const store = useStore();
        const router = useRouter();
        const route = useRoute();
        const ayPlate: any = ref([]);
        const activeName = ref('')
        const recordData: any = ref([]);
        const kmData: any = ref([]);

        const date = new Date();

        let day = date.getDate() + 1 < 10 ? '0' + (date.getDate() + 1) : date.getDate() + 1;;
        let month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
        let year = date.getFullYear();
        let currentDate = year + "-" + month + "-" + day;

        const form = reactive({
            recordDate: currentDate,
            km: ''
        })

        const ruleFormRef2 = ref<FormInstance>();
        const dialogVisible = ref(false);
        const dialogTitle = ref('')
        const nowTab = ref('');
        onMounted(() => {
            console.log("RecordView==>", route.params)
            ayPlate.value = (route.params.plate as string).split(',');
            activeName.value = ayPlate.value[0]

        })
        const getRecord = (plate: any) => {
            axios.post('/getRecord', { plate: plate }).then((res: any) => {
                console.log(res);
                recordData.value = res.data;
                recordData.value.filter((re: any) => {
                    const payList = re.PAYLIST.split(',');
                    const payOff = payList.some((pay: any) => pay === '否' || pay === 'NO' || pay === 'N' || pay === "No");
                    re.payOff = payOff ? '否' : '是';
                })

            })
        }
        const handleTabChange = async (tabName: any) => {//tab
            console.log("handleTabChange===>", tabName)
            nowTab.value = tabName;
            await getRecord(tabName);
        }
        const getKm = (plate: any) => {
            axios.post('/getKm', { plate: plate }).then((res: any) => {
                console.log(res);
                kmData.value = res.data;


            })
        }
        const handleKm = async (plate: any) => {
            // 
            dialogVisible.value = true;
            dialogTitle.value = "新增里程"
            await getKm(nowTab.value);
        }
        const handleAdd = () => {
            dialogVisible.value = true;
            dialogTitle.value = "新增紀錄"
            // newRecordDate.value = row.Plate

        }
        const handleEdit = (index: any, row: any) => {

            router.push({ name: 'updateRecord', params: { type: 'edit', plate: row.RPlate, recordDate: row.RDate, infoData: [route.params.infoData[0], row.RDate], allPlate: route.params.plate } });

        }
        const handleCheck = (index: any, row: any) => {
            router.push({ name: 'updateRecord', params: { type: 'check', plate: row.RPlate, recordDate: row.RDate, infoData: [route.params.infoData[0], row.RDate], allPlate: route.params.plate } });
        }
        const handleDelete = (index: any, row: any) => {
            // 
        }
        const handleSumbit = () => {
            // 


            // if (!formEl[0]) return
            // formEl[0].validate((valid: any) => {
            //     if (valid) {
            if (dialogTitle.value === "新增紀錄") {
                router.push({ name: 'updateRecord', params: { type: 'edit', plate: nowTab.value, recordDate: form.recordDate, infoData: [route.params.infoData[0], form.recordDate] } });

            } else if (dialogTitle.value === "新增里程") {
                if (form.recordDate != '' && form.km != '' && nowTab.value != '') {
                    axios.post('/addKm', {
                        kmDate: form.recordDate, km: form.km, plate: nowTab.value

                    }).then((res: any) => {
                        getKm(nowTab.value)

                    }).catch((e: any) => {
                        console.log(e)
                    })
                }

            }
            // }
            // })

        }
        const handleDeleteKm = (index: any, row: any) => {
            axios.post('/delKm', {
                kmDate: row.kmDate, km: row.km, plate: row.plate
            }).then((res: any) => {
                getKm(row.plate)
            }).catch((e: any) => {
                console.log(e)
            })
        }
        return {
            handleDeleteKm,
            handleSumbit,
            recordData,
            ayPlate,
            nowTab,
            handleKm,
            handleTabChange,
            activeName,
            handleAdd,
            handleEdit,
            handleCheck,
            handleDelete,
            form,
            dialogTitle,
            dialogVisible,
            ruleFormRef2,
            kmData,
            CloseBold

        }
    }
});
</script>
<style>
.plate-tabs {
    margin: 0 40px;
    padding-top: 25px;
}

.plate-tabs .el-tabs__item {
    border: 2px solid #9c9c9c;
    border-radius: 10px;
    padding: 0;
    width: 130px;
    margin-right: 50px;
    letter-spacing: 0.25rem;
    font-size: 16px;
    font-weight: bold;
    background-color: #bababa;
    color: #676767;
}

.plate-tabs .el-tabs__item:hover {
    color: #ffffff8f;
}

.plate-tabs .el-tabs__nav-wrap::after {
    background-color: transparent;
}

.plate-tabs .el-tabs__item.is-active {
    color: #fff;
    border: 2px solid #fff;
    background-color: #E9C46A;
}

.plate-tabs .el-tabs__item.is-active:hover {
    color: #ffffff8f;

}

.plate-tabs .el-tabs__active-bar {
    background-color: transparent;
}

.RecBtn {
    border-radius: 10px;
    border: 1px solid #000;
    color: #fff;
    font-weight: 600;
}

.RecBtn:hover {
    color: #fff;
    border: 1px solid #000;
    opacity: 0.8;
}

.kmDel {
    background-color: #D16666;
    color: #fff;
    border-color: #fff
}

.kmDel:hover {
    background-color: #d16666c2;
    color: #fff;
    border-color: #fff
}

.kmTable .el-table__cell {
    text-align: center;
}
</style>
<style scoped>
:deep(.el-table) {
    --el-table-header-bg-color: #F4EAE0;
    /* --el-table-border: 1px solid #000; */
    --el-table-border-color: #000
}

/* :deep(.el-table__header) {
    border-top: 1px solid #000;
} */

/* :deep(.el-table__body-wrapper) {
    border-right: 1px solid #000;
    border-left: 1px solid #000;
} */

/* :deep(.el-table__inner-wrapper::before) {
    background-color: #000;
} */

:deep(.el-table .el-table__cell) {
    /* border: 1px solid #000; */
    /* border-bottom: none; */
    /* border-right: none; */
    /* border-bottom: 1px solid #000;
    border-left: 1px solid #000;
    color: #000; */
}


:deep(.el-table .el-table__header .el-table__cell) {
    text-align: center;
    font-size: 16px;
}

:deep(.el-table .el-table__cell.is-right) {
    /* border-right: 1px solid #000; */
    text-align: center;
}

:deep(.el-table thead) {
    color: #000;
}
</style>
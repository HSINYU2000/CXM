<template>
    <el-form :model="form" ref="ruleFormRef" :show-message="false">
        <el-row justify="center" style="padding-top:20px">
            <el-col :span="5">
                <el-form-item label="項目類別" required prop="item_type">
                    <el-select v-model="form.item_type" @change="getItem" filterable allow-create>
                        <el-option v-for="item in optType" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="5">
                <el-form-item label="項目" required prop="item">
                    <el-select v-model="form.item" filterable allow-create @change="filterItem(form.item_type, form.item)">
                        <el-option v-for="item in optItem" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="3">
                <el-form-item label="售價" required prop="item_price">
                    <el-input v-model="form.item_price"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="3" :offset="1">
                <el-form-item label="成本" required prop="item_cost">
                    <el-input v-model="form.item_cost"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="2" :offset="1">
                <el-form-item label="庫存" required prop="item_stock">
                    <el-input v-model="form.item_stock"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="2">
                <el-button :icon="Plus" @click="handleSumbit(ruleFormRef)" circle>
                </el-button>
            </el-col>
        </el-row>
        <el-row justify="center">
            <el-col :span="22">
                <el-table :data="fixDataFilter" @cell-dblclick="editContent">
                    <el-table-column prop="ITEM_TYPE" label="項目類別">

                    </el-table-column>
                    <el-table-column prop="ITEM" label="項目">

                    </el-table-column>
                    <el-table-column prop="ITEM_PRICE" label="售價" width="200">
                        <template #default="scope">
                            <el-input v-if="scope.row.edit === true" v-model="scope.row.ITEM_PRICE"></el-input>
                            <span v-else>{{ scope.row.ITEM_PRICE }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="成本" width="180">
                        <template #header>
                            <el-button :icon="showCostText === 'Hide' ? Hide : View" style="border:none" @click="showCost">
                                {{ showCostText === 'Hide' ? '顯示成本' : '隱藏成本' }}
                            </el-button>
                        </template>
                        <template #default="scope">

                            <el-input v-if="scope.row.edit === true && showCostText === 'View'"
                                v-model="scope.row.ITEM_COST"></el-input>
                            <span v-else> {{ showCostText === 'Hide' ? '---' : scope.row.ITEM_COST }}</span>

                        </template>
                    </el-table-column>
                    <el-table-column prop="ITEM_STOCK" label="庫存" width="100">
                        <template #default="scope">
                            <el-input v-if="scope.row.edit === true" v-model="scope.row.ITEM_STOCK"></el-input>
                            <span v-else>{{ scope.row.ITEM_STOCK }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column align="right" width="80">
                        <template #default="scope">
                            <el-button v-if="scope.row.edit === true" class="fixBtn" circle style="background:#5C9EAD"
                                @click="handleEdit(scope.row)" :icon="Check"></el-button>

                            <el-button v-else class="fixBtn" @click="handleDelete(scope.row)" circle
                                :icon="Close"></el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>

        </el-row>
    </el-form>
</template>
<script lang="ts">
import { defineComponent, reactive, ref, onMounted } from 'vue';
import { useStore } from 'vuex'
import { useRouter, useRoute } from 'vue-router'
import axios from '@/axios/index';
import { Plus, Close, Hide, View, Check } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { formContextKey, type FormInstance, type FormRules } from 'element-plus'
import bcrypt from 'bcryptjs';

export default defineComponent({
    setup() {
        const ruleFormRef = ref<FormInstance>();
        const store = useStore();
        const router = useRouter();
        const route = useRoute();

        const form = reactive({
            item_type: '',
            item: '',
            item_price: '',//售價
            item_cost: '',//成本
            item_stock: '',//庫存數量,

        })
        const optType: any = ref([])
        const optItem: any = ref([{ label: '全部', value: '' }])
        const fixData: any = ref([])
        const fixDataFilter: any = ref([])
        onMounted(async () => {
            await getFixData();
            await getType();

        })
        const getFixData = () => {
            axios.post('/getFixData', {
            }).then(async (res: any) => {
                fixData.value = res.fixData
                fixDataFilter.value = fixData.value
            }).catch((e: any) => {
                console.log(e)
            })
        }
        const getType = () => {
            axios.post('/getFixType', {
            }).then(async (res: any) => {
                optType.value = [{ label: '全部', value: '' }]
                optType.value = optType.value.concat(res.optType)
                form.item = ''
            }).catch((e: any) => {
                console.log(e)
            })
        }
        //type change
        const getItem = (type: any) => {
            console.log('type change')
            fixDataFilter.value = fixData.value.filter((f: any) => {
                if (type !== '') {
                    return f.ITEM_TYPE === type
                } else {
                    return fixDataFilter.value = fixData.value
                }
            })
            if (type == '') {
                optItem.value = [{ label: '全部', value: '' }]
                form.item = ''
            } else {
                axios.post('/getFixItem', {
                    item_type: type
                }).then(async (res: any) => {
                    optItem.value = []
                    optItem.value = [{ label: '全部', value: '' }]
                    optItem.value = optItem.value.concat(res.optItem)
                }).catch((e: any) => {
                    console.log(e)
                })
            }

        }
        //item change
        const filterItem = (type: any, item: any) => {
            console.log('item change')
            fixDataFilter.value = fixData.value.filter((f: any) => {
                if (type !== '' && item !== '') {
                    return f.ITEM_TYPE === type && f.ITEM === item
                } else if (type !== '' && item == '') {
                    return f.ITEM_TYPE === type
                } else if (type == '' && item !== '') {
                    return f.ITEM === item
                } else if (type == '' && item == '') {
                    return fixDataFilter.value = fixData.value
                }
            })
        }
        const handleSumbit = (formEl: FormInstance | undefined) => {
            if (!formEl) return
            formEl.validate((valid) => {
                if (valid) {
                    console.log('submit!')
                    axios.post('/updateFix', {
                        item_type: form.item_type,
                        item: form.item,
                        item_price: form.item_price,
                        item_cost: form.item_cost,
                        item_stock: form.item_stock,
                        status: 'insert'
                    }).then(async (res: any) => {
                        ElMessage({
                            message: res.mess,
                            type: res.type === "1" ? 'success' : 'error'
                        })
                        await getFixData();
                        form.item_type = ''
                        form.item = ''
                        form.item_price = ''
                        form.item_cost = ''
                        form.item_stock = ''
                        await filterItem(form.item_type, form.item)
                        await getType();
                        await getItem(form.item_type)
                    }).catch((e: any) => {
                        console.log(e)
                    })
                } else {
                    console.log('error submit!')
                    return false
                }
            })
        }
        const handleDelete = (row: any) => {
            axios.post('/delFix', {
                item_type: row.ITEM_TYPE,
                item: row.ITEM,
                item_price: row.ITEM_PRICE,
                item_cost: row.ITEM_COST,
                id: row.ITEM_ID
            }).then(async (res: any) => {
                ElMessage({
                    message: res.mess,
                    type: res.res > 0 ? 'success' : 'error'
                })
                await getFixData();
            }).catch((e: any) => {
                console.log(e)
            })
        }

        const showCostText = ref('Hide');
        const showCost = () => {
            if (showCostText.value === 'Hide') {
                console.log('輸入密碼')
                ElMessageBox.prompt('', '請輸入密碼', {
                    confirmButtonText: '確認',
                    cancelButtonText: '取消',
                    inputType: 'password'
                })
                    .then(({ value }) => {
                        const acc = store.getters['employee'].ACC;
                        axios.post('/login', { acc: acc, pass: value }).then((res: any) => {
                            if (res.data.length > 0) {
                                if (bcrypt.compareSync(value, res.data[0].PASS)) {
                                    ElMessage({
                                        type: 'success',
                                        message: '成功',
                                    })
                                    showCostText.value = showCostText.value === 'Hide' ? 'View' : 'Hide';

                                } else {
                                    ElMessage({
                                        type: 'error',
                                        message: '密碼錯誤',
                                    })
                                }
                            }
                        })

                    })
                    .catch(() => {
                        ElMessage({
                            type: 'error',
                            message: '密碼錯誤',
                        })
                    })
            } else {
                showCostText.value = showCostText.value === 'Hide' ? 'View' : 'Hide';

            }
        }
        const editContent = (row: any, column: any, cell: any, event: any) => {
            console.log('editContent', row, column, cell, event)
            row.edit = true;
        }
        const handleEdit = (row: any) => {

            row.edit = false;
            row.status = 'update'
            console.log('handleEdit', {
                item_type: row.ITEM_TYPE,
                item: row.ITEM,
                item_price: row.ITEM_PRICE,
                item_cost: row.ITEM_COST,
                item_stock: row.ITEM_STOCK,
                id: row.ITEM_ID,
                status: 'update'
            });
            axios.post('/updateFix', {
                item_type: row.ITEM_TYPE,
                item: row.ITEM,
                item_price: row.ITEM_PRICE,
                item_cost: row.ITEM_COST,
                item_stock: row.ITEM_STOCK,
                id: row.ITEM_ID,
                status: 'update'
            }).then(async (res: any) => {
                ElMessage({
                    message: res.mess,
                    type: res.type === "1" ? 'success' : 'error'
                })
                await getFixData();
            }).catch((e: any) => {
                console.log(e)
            })
        }
        return {
            handleEdit,
            editContent,
            showCost,
            showCostText,
            form,
            Plus,
            Close,
            Hide,
            View,
            Check,
            optType,
            optItem,
            ruleFormRef,
            handleSumbit,
            handleDelete,
            getItem,
            fixData,
            fixDataFilter,
            filterItem
        }
    }
});
</script>
<style>
.fixBtn {
    background-color: #D16666;
    color: #fff
}

.fixBtn:hover {
    background-color: #D16666;
    color: #fff;
    opacity: 0.8;
}
</style>
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
</style>
<template>
    <el-form :model="form" ref="ruleFormRef" :show-message="false">
        <el-row style="padding:10px">
            <el-col :span="1">
                <el-button :icon="Back" @click="back"
                    style="background-color: transparent;border:none;font-size: 18px;"></el-button>
            </el-col>
            <el-col :span="4">
                <el-form-item label="類型" required>
                    <el-select v-model="form.fileType" v-if="recordType === 'edit'">
                        <el-option label="維修單" value="維修單" />
                        <el-option label="估價單" value="估價單" />
                    </el-select>
                    <span v-else>：{{ form.fileType }}</span>
                </el-form-item>
            </el-col>
            <el-col :offset="1" :span="4">
                <el-form-item label="維修人員" required>
                    <el-select v-model="form.employee" v-if="recordType === 'edit'">
                        <el-option v-for="item in employeeOpt" :key="item.value" :label="item.label"
                            :value="item.value" />
                    </el-select>
                    <span v-else>：{{ showEmployee }}</span>
                </el-form-item>
            </el-col>
            <el-col :offset="1" :span="4">
                <el-form-item label="維修類別" required>
                    <el-select v-model="form.fixType" v-if="recordType === 'edit'" allow-create filterable>
                        <el-option v-for="item in fixTypeOpt" :key="item.value" :label="item.label"
                            :value="item.value" />
                    </el-select>
                    <span v-else>：{{ form.fixType }}</span>
                </el-form-item>
            </el-col>
            <el-col :offset="7" :span="2" v-if="recordType === 'edit'">

                <el-popover :visible="visible" placement="right-start" :width="250">
                    <el-row>
                        <el-input v-model=form.note placeholder="輸入註記"></el-input>

                    </el-row>
                    <el-row justify="end" style="margin:5px">
                        <el-button size="small" @click="visible = false"
                            style="background-color:#5C9EAD;color:#fff">confirm</el-button>

                    </el-row>


                    <template #reference>
                        <el-button @click="visible = true">註記</el-button>

                    </template>
                </el-popover>
            </el-col>
            <el-col :offset="5" :span="2" v-if="recordType === 'check'">
                <el-button @click="exportPDF()">PDF</el-button>
            </el-col>
            <el-col :span="2" v-if="recordType === 'check'">
                <el-button @click="exportExcel()">EXCEL</el-button>
            </el-col>
        </el-row>
        <el-row v-if="recordType === 'edit'">
            <el-col :offset="1" :span="4">
                <el-form-item label="項目類別" class="recordForm">
                    <el-select v-model="form.itemType" filterable allow-create @change="getItem(form.itemType)">
                        <el-option v-for="item in itemTypeOpt" :key="item.value" :label="item.label"
                            :value="item.value" />
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="4">
                <el-form-item label="項目" class="recordForm" required prop="item">
                    <el-select v-model="form.item" filterable allow-create>
                        <el-option v-for="item in itemOpt" :key="item.value" :label="item.label" :value="item.value"
                            @click="getPrice(item.price)" />
                    </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="3">
                <el-form-item label="單價" class="recordForm" required prop="price">
                    <el-input v-model="form.price" style="width:70%"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="3">
                <el-form-item label="數量" class="recordForm" required prop="amount">
                    <el-input v-model="form.amount" style="width:70%"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="3">
                <el-form-item label="工資" class="recordForm" required prop="salary">
                    <el-input v-model="form.salary" style="width:70%"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="4">
                <el-form-item label="小計" class="recordForm" required prop="total">
                    <el-input v-model="form.total" style="width:80%"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="1" style="display:flex;justify-content: center;">
                <el-form-item label="" class="recordForm" required>
                    <el-button class="addRecordBtn" :icon="Plus" circle style="background-color: #5C9EAD;color:#fff"
                        @click="addRecord(ruleFormRef)"></el-button>
                </el-form-item>

            </el-col>
        </el-row>
        <div style="display:flex;justify-content: center;">
            <el-table :data="recordData" style="width:91.5%;" class="recordTable"
                :height="recordType === 'edit' ? '350' : '420'">
                <el-table-column label="編號" type="index" width="80"> </el-table-column>
                <el-table-column prop="Item" label="維修項目" width="400"></el-table-column>
                <el-table-column prop="Price" label="單價"></el-table-column>
                <el-table-column prop="Quantity" label="數量"></el-table-column>
                <el-table-column prop="Salary" label="工資"></el-table-column>
                <el-table-column prop="Dollar" label="小計"></el-table-column>
                <el-table-column align="right" width="100" v-if="recordType === 'edit'">
                    <template #default="scope">
                        <el-button class="recDel" style="background-color: #D16666;"
                            @click="handleDelete(scope.$index, scope.row)" :icon="Close" circle size="small">
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <br>
        <!-- <div style="display:flex;justify-content: center;"> -->
        <el-row justify="center">
            <el-col :span="recordType === 'edit' ? 20 : 22">
                <el-table :data="recordSum" style="width:100%;" class="recordSum" :cell-class-name="cellClass" border>
                    <el-table-column prop="total" label="小計(+)"></el-table-column>
                    <el-table-column prop="discount" label="折扣(-)">
                        <!-- eslint-disable-next-line vue/no-unused-vars -->
                        <template #default="scope">
                            <el-input v-model="form.discount" v-if="recordType === 'edit'"></el-input>
                            <span v-else>{{ form.discount }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="salary_sum" label="工資(+)">
                        <!-- <template #default="scope">
                            <el-input v-model="scope.row.salary_sum" v-if="recordType === 'edit'"></el-input>
                            <span v-else>{{ scope.row.salary_sum }}</span>
                        </template> -->
                    </el-table-column>
                    <el-table-column prop="sum" label="總額(=)"></el-table-column>
                    <el-table-column prop="payOff" label="已收">
                        <!-- eslint-disable-next-line vue/no-unused-vars -->
                        <template #default="scope">
                            <el-input v-model="form.payOff" v-if="recordType === 'edit'"></el-input>
                            <span v-else>{{ form.payOff }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="notPay" label="未收"></el-table-column>

                </el-table>
            </el-col>
            <el-col :span="2" style="display:flex;justify-content: center;align-items: center;"
                v-if="recordType === 'edit'">
                <el-button :icon="Check" round style="background-color: #5C9EAD;color:#fff" @click="saveNote()">
                    儲存
                </el-button>
            </el-col>
        </el-row>
        <el-row v-if="recordType === 'check'" justify="center">
            <el-col :span="22" style="text-align: left;">
                <p>註記：<span>{{ form.note }}</span></p>
            </el-col>

        </el-row>

    </el-form>
</template>
<script lang="ts">
import { computed, defineComponent, onMounted, reactive, ref, watchEffect } from 'vue';
import { useStore } from 'vuex';
import { useRouter, useRoute } from 'vue-router';
import axios from '@/axios/index';
import { Back, Plus, Close, Check } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { colorPickerContextKey, formContextKey, type FormInstance, type FormRules } from 'element-plus'
import commonFun from '@/function/commonFun';
import ExcelJs from 'exceljs';
import JSPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

export default defineComponent({
    setup() {
        const store = useStore();
        const router = useRouter();
        const route = useRoute();
        const ruleFormRef = ref<FormInstance>();
        const employeeOpt: any = ref([]);
        const itemTypeOpt: any = ref([])
        const itemOpt: any = ref([])
        const fixTypeOpt: any = ref([])
        const recordType = ref('');
        const recordData: any = ref([])
        const visible = ref(false);
        const showEmployee: any = ref('')
        const form = reactive({
            fixType: '',
            employee: '',
            itemType: '',
            item: '',
            price: '',
            salary: '',
            amount: '',
            total: '',
            discount: '',
            payOff: '',
            note: '',
            fileType: ''

        })
        const recordSum = ref([
            {
                total: 0,
                discount: '',
                salary_sum: 0,
                sum: 0,
                payOff: '',
                notPay: 0
            }
        ])

        const getRecordData = () => {
            console.log(route.params.plate, route.params.recordDate)
            axios.post('/getRecordDetails', { plate: route.params.plate, recordDate: route.params.recordDate })
                .then((res: any) => {
                    //
                    recordData.value = res.data
                    if (res.data2.length > 0) {
                        console.log("recordNote==>" + JSON.stringify(res.data2))
                        form.employee = res.data2[0].EMPLOYEE;
                        form.discount = res.data2[0].DISCOUNT;
                        form.payOff = res.data2[0].PAYOFF;
                        form.fixType = res.data2[0].FIX_TYPE;
                        form.note = res.data2[0].NOTE;
                        form.fileType = res.data2[0].FILE_TYPE
                        if (recordType.value === 'check') {
                            showEmployee.value = employeeOpt.value.filter((e: any) => e.value === form.employee)[0]?.label
                            console.log('showEmployee', showEmployee.value)
                        }
                    }
                    console.log("recordDetails==>" + recordData.value)

                }).catch((e: any) => {
                    console.log(e)
                })
        }
        const getType = () => {
            axios.post('/getFixType', {
            }).then(async (res: any) => {
                itemTypeOpt.value = res.optType

            }).catch((e: any) => {
                console.log(e)
            })
        }
        const getItem = (type: any) => {
            axios.post('/getFixItem', {
                item_type: type
            }).then(async (res: any) => {

                itemOpt.value = res.optItem
            }).catch((e: any) => {
                console.log(e)
            })
        }
        const getPrice = (price: any) => {
            form.price = price;
        }





        watchEffect(() => {
            let sum = 0;
            if (recordData.value) {
                recordData.value.forEach((re: any) => {

                    sum = sum + Number(re.Price) * Number(re.Quantity)
                })
            }
            let sum2 = 0;
            if (recordData.value) {
                recordData.value.forEach((re: any) => {
                    sum2 = sum2 + Number(re.Salary)
                })
            }
            recordSum.value[0].total = sum
            recordSum.value[0].salary_sum = sum2
            recordSum.value[0].sum = Number(recordSum.value[0].total) - Number(form.discount) + recordSum.value[0].salary_sum;
            recordSum.value[0].notPay = Number(recordSum.value[0].sum) - Number(form.payOff)
        })
        watchEffect(() => {
            form.total = String(Number(form.price) * Number(form.amount) + Number(form.salary))
        })
        const getEmployeeOpt = () => {
            axios.post('/getEmployee', {}).then((res: any) => {
                console.log('getEmployee', res)
                employeeOpt.value = res.employeeOpt;
            })
        }
        const getFixTypeOpt = () => {
            axios.post('/getRecFixType', {}).then((res: any) => {
                console.log(res)
                fixTypeOpt.value = res.fixTypeOpt;
            })
        }

        onMounted(async () => {
            console.log('updateRecord', route.params)
            await getEmployeeOpt();
            await getFixTypeOpt();
            recordType.value = route.params.type as string;
            await getRecordData()
            await getType();

        })
        const back = async () => {
            let notPay = '？';
            notPay = await commonFun.getNotPay(route.params.allPlate as string)
            console.log('handleRecord', notPay)
            router.push({ name: 'record', params: { plate: route.params.allPlate, infoData: [route.params.infoData[0], notPay] } });

        }

        const handleDelete = (index: any, row: any) => {
            const data = {
                plate: route.params.plate,
                recordDate: route.params.recordDate,
                item: row.Item,
                price: row.Price,
                quantity: row.Quantity,
                salary: row.Salary,
                dollar: row.Dollar
            }
            ElMessageBox.confirm('項目==>  ' + row.Item, '刪除', {
                confirmButtonText: '確定',
                cancelButtonText: '取消'
            }).then(() => {
                axios.post('/delRecord', data)
                    .then(async (res: any) => {
                        await getRecordData()
                    }).catch((e: any) => {
                        console.log(e)
                    })
            }).catch(() => {
                console.log('取消刪除==>   ' + row.Item)
            })


        }
        const cellClass = (column: any) => {
            // console.log("column=>", column)
            if (column.columnIndex == 5 && Number(column.row.notPay) > 0) {
                return 'notPay';
            }
        }
        const addRecord = (formEl: FormInstance | undefined) => {
            if (!formEl) return
            formEl.validate((valid) => {
                if (valid) {
                    const data = {
                        plate: route.params.plate,
                        recordDate: route.params.recordDate,
                        item: form.item,
                        price: form.price,
                        quantity: form.amount,
                        salary: form.salary,
                        dollar: form.total
                    }
                    axios.post('/addRecord', data)
                        .then(async (res: any) => {
                            await getRecordData()
                        }).catch((e: any) => {
                            console.log(e)
                        })
                }
            })

        }
        const saveNote = () => {
            const data = {
                plate: route.params.plate,
                recordDate: route.params.recordDate,
                employee: form.employee,
                fixType: form.fixType,
                note: form.note,
                discount: form.discount,
                payOff: form.payOff,
                total: recordSum.value[0].sum,
                fileType: form.fileType
            }
            axios.post('/saveNote', data)
                .then(async (res: any) => {
                    await getRecordData()
                }).catch((e: any) => {
                    console.log(e)
                })
        }

        const exportExcel = () => {
            // console.log('exportExcel', recordData.value)
            const workbook = new ExcelJs.Workbook(); // 新增活頁簿
            const sheet = workbook.addWorksheet('工作表1'); // 新增工作表

            const titleCell = sheet.getCell('A1');
            sheet.mergeCells('A1:F1')
            titleCell.value = '浱興車業' + form.fileType;
            titleCell.font = { size: 14, bold: true }
            titleCell.alignment = { vertical: 'middle', horizontal: 'center' }

            const cusNameTitle = sheet.getCell("A2")
            cusNameTitle.value = '客戶姓名'
            cusNameTitle.font = { size: 12, bold: true }
            cusNameTitle.alignment = { vertical: 'middle', horizontal: 'center' }

            const cusName = sheet.getCell("B2")
            cusName.value = route.params.infoData[0]
            cusName.alignment = { vertical: 'middle', horizontal: 'center' }

            const plateTitle = sheet.getCell("C2")
            plateTitle.value = '車牌號碼'
            plateTitle.font = { size: 12, bold: true }
            plateTitle.alignment = { vertical: 'middle', horizontal: 'center' }

            const plate = sheet.getCell("D2")
            plate.value = route.params.plate as string
            plate.alignment = { vertical: 'middle', horizontal: 'center' }

            const dateTitle = sheet.getCell("E2")
            dateTitle.value = '日期'
            dateTitle.font = { size: 12, bold: true }
            dateTitle.alignment = { vertical: 'middle', horizontal: 'center' }

            const RDate = sheet.getCell("F2")
            RDate.value = route.params.infoData[1]
            RDate.alignment = { vertical: 'middle', horizontal: 'center' }

            const sheetHeader: any = reactive(['編號', '維修項目', '單價', '數量', '工資', '小計']);
            sheet.addRow(sheetHeader);
            for (let i = 0; i < recordData.value.length; i++) {
                let aData = [];
                aData.push(String(i + 1))
                aData.push(recordData.value[i].Item)
                aData.push(recordData.value[i].Price)
                aData.push(recordData.value[i].Quantity)
                aData.push(recordData.value[i].Salary)
                aData.push(Number(recordData.value[i].Price) * Number(recordData.value[i].Quantity) + Number(recordData.value[i].Salary))
                sheet.addRow(aData);

                sheet.getRow(i + 1).eachCell((cell: any, cellNumber: any) => {
                    console.log('0214==>', cell, cellNumber)
                    sheet.getCell(cell.address).border = {//邊線
                        top: { style: 'thin' },
                        left: { style: 'thin' },
                        bottom: { style: 'thin' },
                        right: { style: 'thin' }
                    };
                })

            }
            sheet.addRow([1, 1, 1, 1, 1, 1])
            sheet.getRow(recordData.value.length + 4).eachCell((cell: any, cellNumber: any) => {
                console.log(cell, cellNumber)
                // if (i === recordData.value.length + 1) { //最後一列(合計)
                sheet.getCell(cell.address).fill = {//填滿
                    type: 'pattern',
                    pattern: 'solid',
                    fgColor: { argb: '9D9D9D' }
                };
                sheet.getCell(cell.address).border = {//邊線
                    top: { style: 'double' },
                    left: { style: 'thin' },
                    bottom: { style: 'thin' },
                    right: { style: 'thin' }
                };
                // }
                // else if (i === 1) {//表投
                //     sheet.getCell(cell.address).fill = {//填滿
                //         type: 'pattern',
                //         pattern: 'solid',
                //         fgColor: { argb: '9D9D9D' }
                //     };
                //     sheet.getCell(cell.address).border = {//邊線
                //         top: { style: 'thin' },
                //         left: { style: 'thin' },
                //         bottom: { style: 'thick' },
                //         right: { style: 'thin' }
                //     };
                // } else {//資料列
                //     sheet.getCell(cell.address).border = {//邊線
                //         top: { style: 'thin' },
                //         left: { style: 'thin' },
                //         bottom: { style: 'thin' },
                //         right: { style: 'thin' }
                //     };
                // }

            });

            workbook.xlsx.writeBuffer().then((content: any) => {
                const link = document.createElement('a');
                const blobData = new Blob([content], {
                    type: 'application/vnd.ms-excel;charset=utf-8;'
                });
                link.download = 'test.xlsx';
                link.href = URL.createObjectURL(blobData);
                link.click();
            });
        }
        const exportPDF = () => {
            //
        }

        return {
            exportExcel,
            exportPDF,
            handleDelete,
            recordData,
            recordType,
            back,
            form,
            ruleFormRef,
            employeeOpt,
            itemTypeOpt,
            itemOpt,
            recordSum,
            fixTypeOpt,
            Back,
            Plus,
            Close,
            Check,
            cellClass,
            getItem,
            getPrice,
            addRecord,
            saveNote,
            visible,
            showEmployee
        }
    }
});
</script>
<style scoped>
 :deep(.notPay) .cell {
     color: #D16666;
     font-weight: bold;
     font-size: 16px;
 }

 .recordForm {
     flex-direction: column;
     align-items: flex-start;
 }

 .addRecordBtn:hover {
     opacity: 0.8;
 }

 .recordTable {
     --el-table-bg-color: transparent;
     --el-table-header-bg-color: #D7D7D7;
     --el-table-tr-bg-color: #D7D7D7;
     --el-table-border-color: #000;
     --el-table-text-color: #000;
     --el-table-header-text-color: #000;
 }

 .recordSum {
     --el-table-header-bg-color: #b1b1b1;
     --el-table-tr-bg-color: #D7D7D7;
     --el-table-border-color: #000;
     --el-table-text-color: #000;
     --el-table-header-text-color: #fff;
 }

 .recDel {
     color: #fff;
 }
</style>
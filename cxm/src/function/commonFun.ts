import { computed, defineComponent, onMounted, reactive, ref, watchEffect } from 'vue';
import { useStore } from 'vuex';
import { useRouter, useRoute } from 'vue-router';
import axios from '@/axios/index';

const getNotPay = (allPlate: string): any => {
    let notPay = '';
    return axios.post('/getNotPay', { plate: allPlate })
        .then((res: any) => {
            console.log('getNotPay', res)
            notPay = res.result
            return notPay;
        }).catch((e) => {
            console.log(e)
        })

}
export default {
    getNotPay
}
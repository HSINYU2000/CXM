import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8777';
axios.defaults.timeout = 10000

axios.interceptors.request.use(
    config => {

        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

axios.interceptors.response.use(
    response => {
        if (response.status === 200) {
            // 200是成功
            return Promise.resolve(response);
        } else {
            return Promise.reject(response);
        }
    },
    error => {
        console.log(error);
        // alert(`異常請求:${JSON.stringify(error.message)}`)
        return Promise.resolve(error);
    }
);
function get(url: string, data: any) {
    return new Promise((resolve, reject) => {
        axios.get(url, data).then((res) => {
            resolve(res.data);
        }).catch((error) => {
            reject(error);
        });
    });
}
function post(url: string, data: any) {
    return new Promise((resolve, reject) => {
        axios.post(url, data).then((res) => {
            resolve(res.data);
        }).catch((error) => {
            reject(error);
        });
    });
}
export default {
    get, post
};
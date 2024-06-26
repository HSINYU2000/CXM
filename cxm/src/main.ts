import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// import bcrypt from 'bcryptjs';

const app = createApp(App);

app.use(ElementPlus)
app.use(store)
// app.use(bcrypt)
app.use(router)
app.mount('#app')

import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import loginView from '../views/Login/loginView.vue'
import store from '@/store'
const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'login',
    component: loginView
  },
  {
    path: '/customer',
    name: 'customer',
    component: () => import(/* webpackChunkName: "about" */ '../views/CustomerView.vue')
  },
  {
    path: '/record',
    name: 'record',
    component: () => import(/* webpackChunkName: "about" */ '../views/RecordView.vue')
  },
  {
    path: '/updateRecord',
    name: 'updateRecord',
    component: () => import(/* webpackChunkName: "about" */ '../views/UpdateRecord.vue')
  },
  {
    path: '/stock',
    name: 'stock',
    component: () => import(/* webpackChunkName: "about" */ '../views/StockView.vue')
  },
  {
    path: '/fixItem',
    name: 'fixItem',
    component: () => import(/* webpackChunkName: "about" */ '../views/FixItemView.vue')
  },
  {
    path: '/employee',
    name: 'employee',
    component: () => import(/* webpackChunkName: "about" */ '../views/EmployeeView.vue')
  },
  {
    path: '/finance',
    name: 'finance',
    component: () => import(/* webpackChunkName: "about" */ '../views/FinanceView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach(async (to, from) => {
  console.log("router from:" + from + "to:", to)
  if (store.getters['login'] === true) {
    return true;
  } else {
    return false;
  }
})

export default router

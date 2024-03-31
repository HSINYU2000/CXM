import { createStore } from 'vuex'

export default createStore({
  state: {
    login: false,
    employee: {}
  },
  getters: {
    login(state) {
      return state.login
    },
    employee(state) {
      return state.employee
    }
  },
  mutations: {
    mutations_login(state, param) {
      state.login = param;
    },
    mutations_employee(state, param) {
      state.employee = param;
    }
  },
  actions: {
    actions_login(context, param) {
      context.commit('mutations_login', param)
    },
    actions_employee(context, param) {
      context.commit('mutations_employee', param)
    }
  },
  modules: {
  }
})

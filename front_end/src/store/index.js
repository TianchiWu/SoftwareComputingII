import Vue from 'vue'
import Vuex from 'vuex'
import comment from './modules/comment'
import coupon from './modules/coupon'
import credit from './modules/credit'
import enterprise from "./modules/enterprise";
import hotel from './modules/hotel'
import order from './modules/order'
import room from './modules/room'
import user from './modules/user'
import getters from './getters'
Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    comment,
    coupon,
    credit,
    enterprise,
    hotel,
    order,
    room,
    user,
  },
  state: {
  },
  mutations: {
  },
  actions: {
  },
  getters
})

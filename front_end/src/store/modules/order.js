import {
    reserveHotelAPI,
    getAllOrdersAPI,
    finishOrderAPI,
    checkoutAPI,
    getUserOrderAPI,
    getUserOrdersAPI,
    cancelOrderAPI,
    getAbnormalOrderListAPI,
}from '@/api/order'
import {message} from "ant-design-vue";

const order = {
    state: {
        ManagerId:0,
        orderModalVisible: false,
        currentOrderRoom: {

        },
        orderList: [],
        userOrderList: [

        ],
        orderVisible: false,
        activeOrderId:0,
        activeOrderData:{

        },

        totalPrice:'',
        finalPrice:'',
        abnormalOrderList:[

        ],
    },
    mutations: {
        set_orderModalVisible: function(state, data) {
            state.orderModalVisible = data
        },
        set_currentOrderRoom: function(state, data) {
            state.currentOrderRoom = {
                ...state.currentOrderRoom,
                ...data,
            }
        },
        set_orderList: function(state, data) {
            state.orderList = data
        },
        set_userOrderList: (state, data) => {
            state.userOrderList = data
        },
        set_orderVisible: (state,data) => {
            state.orderVisible = data
        },
        set_activeOrderId: (state, data)=> {
            state.activeOrderId = data
        },
        set_activeOrderData: function(state, data) {
            state.activeOrderData = {
                ...state.activeOrderData,
                ...data
            }
        },
        set_abnormalOrderList(state,data){
            state.abnormalOrderList = data
        },
        reset_price: (state) => {
            state.totalPrice = ''
            state.finalPrice = ''
        },
        set_ManagerId(state,data){
            state.ManagerId=data
        }
    },

    actions: {
        addOrder: async({ state, commit }, data) => {
            const res = await reserveHotelAPI(data)
            if(res){
                message.success('预定成功')
                commit('set_orderModalVisible', false)
            }
        },
        getAllOrders: async({ state, commit }) => {
            const res = await getAllOrdersAPI(state.ManagerId)
            if(res){
                commit('set_orderList', res)
            }
        },
        FinishOrder: async({ state, commit,dispatch }, orderId) => {
            const res = await finishOrderAPI(orderId)
            if(res) {
                dispatch("getAllOrders")
                message.success('执行成功')
            }else{
                message.error('执行失败')
            }
        },
        checkout: async({state,commit,dispatch},orderId) =>{
            const res=await checkoutAPI(orderId)
            if(res) {
                dispatch("getAllOrders")

                message.success('执行成功')
            }else{
                message.error('执行失败')
            }
        },
        getUserOrders: async({ state,dispatch ,commit },userId) => {
            const data = {
                userId: Number(userId)
            }
            const res = await getUserOrdersAPI(data)
            if(res){
                commit('set_userOrderList', res)
                console.log(state.userOrderList)
            }
        },
        cancelOrder: async({ state, commit,dispatch }, orderId) => {
            const res = await cancelOrderAPI(orderId)
            if(res) {
                dispatch('getAbnormalOrderList')
                message.success('撤销成功，信用值已更新')
            }else{
                message.error('撤销失败')
            }
        },
        getUserOrder: async({ state, commit }) => {
            return new Promise((resolve, reject) => {
                getUserOrderAPI(state.activeOrderId).then(response => {
                    const data = response
                    commit('set_activeOrderData', data)
                    resolve(data)
                }).catch(error => {
                    reject(error)
                })
            })
        },
        getTotalPrice: async () => {
            return this.totalPrice
        },
        setTotalPrice: async ({state},data) => {
            state.totalPrice=data
        },
        getFinalPrice: async () => {
            return this.finalPrice
        },
        setFinalPrice: async ({state},data) => {
            state.finalPrice=data
        },
        getAbnormalOrderList: async({commit})=>{
            const res=await getAbnormalOrderListAPI();
            if(res){
                commit('set_abnormalOrderList',res)
            }
        },
    }
}

export default order
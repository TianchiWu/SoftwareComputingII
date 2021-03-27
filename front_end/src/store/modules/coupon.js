import {
    salesAllCouponsAPI,
    orderMatchCouponsAPI,
    hotelAllCouponsAPI,
    addCouponAPI,
    deleteCouponAPI,
}from '@/api/coupon'
import {message} from "ant-design-vue";
const credit = {
    state: {

        salesCouponList:[

        ],
        orderMatchCouponList: [

        ],
        currentCoupon: [

        ],

        couponList: [],
        couponVisible: false,
        addCouponVisible: false,
        selectCouponVisible: false,
        activeCouponHotelId:0,
    },
    mutations: {
        set_salesCouponList(state,data){
            state.salesCouponList = data
        },
        set_orderMatchCouponList: function(state, data) {
            state.orderMatchCouponList = data
        },
        set_couponList: function(state, data) {
            state.couponList = data
        },
        set_addCouponVisible: function(state, data) {
            state.addCouponVisible =data
        },
        set_couponVisible: function(state, data) {
            state.couponVisible = data
        },
        set_selectCouponVisible: (state,data) =>{
            state.selectCouponVisible=data
        },
        set_currentCoupon: function(state,data){
            state.currentCoupon = data
        },
        set_activeCouponHotelId:function(state,data){
            state.activeCouponHotelId=data
        }
    },

    actions: {

        getAllSalesCoupon: async ({commit}) => {
            const res = await salesAllCouponsAPI();
            if(res){
                commit('set_salesCouponList',res)
            }
        },
        getOrderMatchCoupons: async({ state, commit }, data) => {
            const res = await orderMatchCouponsAPI(data)
            if(res){
                commit('set_orderMatchCouponList', res)
            }
        },
        getHotelCoupon: async({ state, commit }) => {
            const res = await hotelAllCouponsAPI(state.activeCouponHotelId)
            if(res) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList）
                commit('set_couponList',res)
            }
        },
        addHotelCoupon: async({ commit, dispatch }, data) => {
            const res=await addCouponAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                if(data.hotelId===-1)
                    dispatch('getAllSalesCoupon')
                else
                    dispatch('getHotelCoupon')
                commit('set_addCouponVisible', false)
                commit('set_couponVisible',true)
                message.success('添加策略成功')
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        DeleteCoupon: async ({state,dispatch},record)=>{
            const res = await deleteCouponAPI(record.id)
            if(res){
                if(record.hotelId===-1)
                    dispatch('getAllSalesCoupon')
                else
                    dispatch('getHotelCoupon')
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        }
    }
}

export default credit
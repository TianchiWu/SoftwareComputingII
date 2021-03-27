import { message } from 'ant-design-vue'
import {
    SearchHotelsAPI,
    getHotelByIdAPI,
    getHotelsByManagerIdAPI,
    addHotelAPI,
    deleteHotelAPI,
    setManagerAPI,
    updateHotelInfoAPI,
} from '@/api/hotel'
const hotel = {
    state: {
        allHotelData:{
          userId:Number(0),
          minrate:Number(0),
          maxrate:Number(5),
          address:"",
          hotelName:"",
          hotelStar:"",
          orderState:"",
        },
        hotelList: [],
        hotelListParams: {
            pageNo: 0,
            pageSize: 12
        },
        hotelListLoading: true,
        currentHotelId: '',
        currentHotelInfo: {},
        currentMangerId:0,
        searchHotel:false,
        managerId:0,
        addHotelParams: {
            hotelName: '',
            address: '',
            hotelStar:'',
            rate: 0,
            description:'',
            phoneNum:'',
            managerId:'',
        },
        activeHotelId: 0,
        updateHotelInfo:{

        },
        addHotelModalVisible: false,
        setManagerVisible: false,
        updateHotelVisible: false,
        setManagerId: 0,
        updateHotelId: 0,
    },
    mutations: {
        set_hotelList: function(state, data) {
            state.hotelList = data
        },
        set_hotelListParams: function(state, data) {
            state.hotelListParams = {
                ...state.hotelListParams,
                ...data,
            }
        },
        set_hotelListLoading: function(state, data) {
            state.hotelListLoading = data
        },
        set_currentHotelId: function(state, data) {
            state.currentHotelId = data
        },
        set_currentHotelInfo: function(state, data) {
            state.currentHotelInfo = {
                ...state.currentHotelInfo,
                ...data,
            }
        },
        set_searchHotel:function(state,data){
            state.searchHotel = data;
        },
        set_allHotelData:function (state,data) {
            state.allHotelData = data;
        },
        set_addHotelParams: function(state, data) {
            state.addHotelParams = {
                ...state.addHotelParams,
                ...data,
            }
        },
        set_activeHotelId: function(state, data) {
            state.activeHotelId = data
        },

        set_managerId: function(state,data){
            state.managerId = data
        },
        set_addHotelModalVisible: function(state, data) {
            state.addHotelModalVisible = data
        },
        set_setManagerVisible: function(state,data){
            state.setManagerVisible = data
        },

        set_updateHotelVisible:function(state,data){

            state.updateHotelVisible = data
        },
        set_setManagerId:function(state,data){
            state.setManagerId = data
        },

        set_updateHotelId:function(state,data){
            state.updateHotelId = data;
        },
        set_updateHotelInfo:function(state,data){
            state.updateHotelInfo = data;
        },

    },

    actions: {
        getHotelListByManagerId:async({commit,state},userId) => {
            const res=await getHotelsByManagerIdAPI(userId)
            if(res){
                commit('set_hotelList',res)
            }
        },
        getHotelById: async({commit, state}) => {
            const res = await getHotelByIdAPI({
                hotelId: state.currentHotelId
            })
            if(res){
                commit('set_currentHotelInfo', res)
            }
        },
        SearchHotels:async({state,commit})=>{
            const res =await SearchHotelsAPI(state.allHotelData)
            if(res){
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
            else{
                message.error("没有找到你搜索的酒店")
            }
        },
        addHotel: async({ state, dispatch, commit }) => {
            const res = await addHotelAPI(state.addHotelParams)
            if(res){
                dispatch('SearchHotels')
                commit('set_addHotelParams', {
                    hotelName: '',
                    address: '',
                    hotelStar:'',
                    rate: 0,
                    description:'',
                    phoneNum:'',
                    managerId:'',
                    information:'',
                })
                commit('set_addHotelModalVisible', false)
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
        DeleteHotel: async({ state,dispatch }) => {
            const res = await deleteHotelAPI(state.activeHotelId)
            if(res) {
                dispatch('SearchHotels')
                message.success('删除成功')
            }else{
                message.error('删除失败')
            }
        },
        setManager:async({state,commit,dispatch},hotelid)=>{
            const res= await setManagerAPI(hotelid,state.setManagerId)
            if(res){
                message.success('设置成功')
                dispatch('SearchHotels')
                commit('set_setManagerVisible',false)
            }
            else{
                message.error('设置失败')
            }
        },

        UpdateHotelInfo:async({state,commit,dispatch},data) =>{
            const params = {
                id: state.updateHotelId,
                ...data,
            }
            const res = await updateHotelInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('SearchHotels')
                commit('set_updateHotelVisible',false)
            }
        },
    }
}

export default hotel
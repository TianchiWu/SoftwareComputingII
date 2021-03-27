import { message } from 'ant-design-vue'
import {
    getActiveRoomListAPI,
    deleteRoomAPI,
    updateRoomNumAPI,
    getActiveRoomTypeAPI,
    deleteRoomsAPI,
    addRoomAPI,
} from '@/api/room'

const room = {
    state: {

        activeRoomList: [],
        activeRooms: [],
        activeRoomHotelId:0,
        activeRoomId:0,
        deleteNum: 0,
        addRoomParams: {
            roomType: '',
            hotelId: '',
            price: '',
            total: 0,
            curNum: 0,
        },
        addRoomModalVisible: false,
        roomVisible:false,
        deleteRoomVisible: false,
        deleteRoomNumVisible:false,
    },
    mutations: {
        set_activeRoomList: function(state,data){
            state.activeRoomList = data
        },
        set_activeRoomHotelId:function(state,data){
            state.activeRoomHotelId=data
        },
        set_activeRoomId:function(state,data){
            state.activeRoomId = data
        },
        set_deleteNum:function(state,data){
            state.deleteNum = data
        },
        set_addRoomParams: function(state, data) {
            state.addRoomParams = {
                ...state.addRoomParams,
                ...data,
            }
        },
        set_addRoomModalVisible: function(state, data) {
            state.addRoomModalVisible = data
        },
        set_activeRooms:function(state,data){
            state.activeRooms = data
        },
        set_roomVisible: function(state,data){
            state.roomVisible = data
        },
        set_deleteRoomVisible: function(state,data){
            state.deleteRoomVisible = data
        },
        set_deleteRoomNumVisible:function(state,data){
            state.deleteRoomNumVisible = data
        },
    },

    actions: {
        deleteRoom:async ({state,dispatch},id) =>{
            const res=await deleteRoomAPI(id)
            if(res){
                dispatch('getActiveRooms')
                message.success('删除成功')
            }
            else{
                message.error('删除失败')
            }
        },
        getActiveRooms: async({state,commit})=>{

            const res = await getActiveRoomListAPI(state.activeRoomHotelId)
            if(res){
                commit('set_activeRoomList',res)
            }
        },
        updateRoomNum:async ({state})=>{
            const res=await updateRoomNumAPI(state.activeRoomId,state.deleteNum)
        },
        getActiveRoomType:async ({state,commit})=>{
            const res=await getActiveRoomTypeAPI(state.activeRoomHotelId)
            if(res){
                commit('set_activeRooms',res)
            }
        },
        DeleteRooms:async({state,dispatch}) =>{
            const res =await deleteRoomsAPI(state.activeRoomId,state.deleteNum);
            if(res){
                dispatch('getActiveRooms')
                dispatch('getActiveRoomType')
                message.success('删除成功')
            }
            else{
                message.error('删除失败')
            }
        },
        addRoom: async({ state, dispatch, commit }) => {
            const res = await addRoomAPI(state.addRoomParams)
            if(res){
                commit('set_addRoomModalVisible', false)
                dispatch('getActiveRooms')
                commit('set_addRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                })
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
    }
}

export default room
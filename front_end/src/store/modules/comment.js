import { message } from 'ant-design-vue'
import {
    getCommentsAPI,
    addCommentAPI,
    deleteCommentAPI,
    updateCommentAPI,
    enableAPI
} from '@/api/comment'
const comment = {
    state: {
        comments:[],
        currentCommentHotelId:0,
        activeComment:{},
        addText:false,
        showText:false,
    },
    mutations: {
        set_comments:function (state,data) {
            state.comments = data
        },
        set_currentCommentHotelId:function(state,data){
            state.currentCommentHotelId=data
        },
        set_activeComment:function(state,data){
            state.activeComment = data;
        },
        set_addText:function(state,data){
            state.addText = data;
        },
        set_showText:function (state,data){
            state.showText = data
        },
    },

    actions: {
        setAddText:async({state,commit},userId) =>{
            const res=await enableAPI(state.currentCommentHotelId,userId)
            if(res){
                commit('set_addText',true)
            }
            else{
                message.error("你没有完成过该酒店的订单，不能评论")
            }
        },
        getCommentList: async({state,commit})=>{

            const res=await getCommentsAPI(state.currentCommentHotelId)
            if(res){
                commit('set_comments',res)
            }
        },
        updateComment:async({state,dispatch},data)=>{
            const res=await updateCommentAPI(data)
            if(res){
                dispatch("getCommentList")
                dispatch("getHotelById")
                message.success("修改成功")
            }
            else{
                message.error("修改失败")
            }
        },
        deleteComment:async({state,dispatch},id)=>{
            const res=await deleteCommentAPI(id)
            if(res){
                dispatch("getCommentList")
                dispatch("getHotelById")
                message.success("删除成功")
            }
            else{
                message.error("删除失败")
            }
        },
        addComment:async ({state,dispatch},data)=>{
            const res=await addCommentAPI(data)
            if(res){
                dispatch('getCommentList')
                dispatch('getHotelById')
                message.success("评论成功")
            }
            else{
                message.error("评论失败")
            }
        },
    }
}

export default comment
import { message } from 'ant-design-vue'
import {
    addCreditAPI,
    getCreditListAPI,
} from '@/api/credit'
const credit = {


    state: {
        addCreditVisible:false,

        userCreditList: [

        ],
    },
    mutations: {
        set_addCreditVisible(state,data){
           state.addCreditVisible = data
        },

        set_userCreditList: (state, data)=>{
            state.userCreditList = data
        },

    },

    actions: {
        addCredit: async({state,commit,dispatch},data)=>{


            const res = await addCreditAPI(data)
            if(res){
                commit('set_addCreditVisible',false)
                dispatch('getClientList')
                message.success('充值成功')
            }
            else{
                message.error('充值失败')
            }
        },


        getCreditList: async ({ state, commit},userId)=>{
            const res=await getCreditListAPI(userId)
            if(res){
                commit('set_userCreditList',res)
                console.log(state.userCreditList)
            }
        },
    }
}

export default credit
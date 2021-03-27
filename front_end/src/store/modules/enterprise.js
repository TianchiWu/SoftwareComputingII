import { message } from 'ant-design-vue'
import {
    getEnterpriseListAPI,
    addEnterpriseAPI,
    deleteEnterpriseAPI
} from '@/api/enterprise'
const enterprise = {
    state: {
        enterpriseList:[

        ],
        addEnterpriseParams:{
            name:''
        },
        addEnterpriseVisible:false,

    },
    mutations: {
        set_enterpriseList:function (state,data) {
            state.enterpriseList=data;
        },
        set_addEnterpriseParams: function(state, data){
            state.addEnterpriseParams = {
                ...state.addEnterpriseParams,
                ...data,
            }
        },
        set_addEnterpriseVisible: function(state,data){
            state.addEnterpriseVisible=data;
        },

    },

    actions: {
        getEnterpriseList:async({commit}) =>{
            const res=await getEnterpriseListAPI()
            if(res) {
                commit('set_enterpriseList',res)
            }
        },
        addEnterprise: async ({ state,commit,dispatch}) => {
            const res = await addEnterpriseAPI(state.addEnterpriseParams)
            if(res) {
                commit('set_addEnterpriseParams',{
                    name:''
                })
                commit('set_addEnterpriseVisible',false)
                message.success('添加成功')
                dispatch('getEnterpriseList')
            }else{
                message.error('添加失败')
            }
        },
        DeleteEnterprise: async({state,dispatch},name) => {

            const res = await deleteEnterpriseAPI(name)
            if(res){
                message.success('删除成功')
                dispatch('getEnterpriseList')
            }
            else{
                message.error('删除失败')
            }
        },
    }
}

export default enterprise
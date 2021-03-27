import router from '@/router'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import { message } from 'ant-design-vue'
import {
    loginAPI,
    registerAPI,
    getUserInfoAPI,
    updateUserInfoAPI,
    updatePasswordAPI,
    registerNormalAPI,
    getManagerListAPI,
    getClientListAPI,
    addClientAPI,
    updateAllUserInfoAPI,
    updateManagerInfoAPI,
    addManagerAPI,
    deleteManagerAPI,
    deleteClientAPI,
    getSaleListAPI,
    addSaleAPI,
    cancelCreditAPI,
    initializeAPI
} from '@/api/user'

import {registerEnterpriseAPI, updateEnterpriseAPI} from "../../api/user";

const getDefaultState = () => {
    return {
        managerList: [

        ],
        clientList: [

        ],
        saleList:[

        ],
        email:'',
        userId: '',
        userInfo: {

        },


        addManagerModalVisible: false,
        addClientVisible: false,
        registerVisible: false,
        updateClientVisible: false,
        updateManagerVisible: false,
        addSaleVisible:false,
        userType:0,
        addManagerParams: {
            email:'',
            password:''
        },
        addClientParams:{
            email:'',
            password:'',
            username:'',
            phonenumber:'',
            credit:'',
            birthday:'',
            membertype:''
        },
        activeUserInfo:{

        },
        activeManagerInfo:{

        },
        activeUserId: 0,
        activeManagerId: 0,
    }
}

const user = {
    state: getDefaultState(),



    mutations: {
        reset_state: function(state) {
            state.token = '',
            state.userId = '',
            state.birthday ='',
            state.userInfo = {
                
            }
        },
        set_token: function(state, token){
            state.token = token
        },
        set_email: (state, data) => {
            state.email = data
        },
        set_userId: (state, data) => {
            state.userId = data
        },
        set_userInfo: (state, data) => {
            state.userInfo = {
                ...state.userInfo,
                ...data
            }
        },

        set_userType:function(state,data){
            state.userType=data
        },

        set_registerVisible: (state,data)=>{
            state.registerVisible=data
        },
        set_managerList: function(state, data) {
            state.managerList = data
        },
        set_clientList: function(state, data) {
            state.clientList = data
        },
        set_saleList: function(state,data){
            state.saleList = data
        },

        set_addManagerModalVisible: function(state, data) {
            state.addManagerModalVisible = data
        },
        set_addClientVisible: function(state, data) {
            state.addClientVisible = data
        },
        set_addManagerParams: function(state, data) {
            state.addManagerParams = {
                ...state.addManagerParams,
                ...data,
            }
        },
        set_addClientParams: function(state,data){
            state.addClientParams = {
                ...state.addClientParams,
                ...data,
            }
        },
        set_updateClientVisible: function(state,data){
            state.updateClientVisible = data
        },
        set_updateManagerVisible: function(state,data){
            state.updateManagerVisible = data
        },
        set_activeUserId:function(state,data){
            state.activeUserId = data;
        },
        set_activeManagerId:function(state,data){
            state.activeManagerId = data;
        },
        set_activeUserInfo:function(state,data){
            state.activeUserInfo = data;
        },
        set_activeManagerInfo:function(state,data){
            state.activeManagerInfo = data;
        },
        set_addSaleVisible:function(state,data){
            state.addSaleVisible=data;
        },
    },

    actions: {
        login: async ({ state,dispatch, commit }, userData) => {
            const res = await loginAPI(userData)
            if(res){
                setToken(res.id)
                commit('set_email',res.email)
                commit('set_userId', res.id)
                commit('set_userType',res.userType)
                dispatch('getUserInfo')
                router.push('/hotel/hotelList')
            }

        },
        register: async({ commit }, data) => {
            const res = await registerAPI(data)
            if(res){
                message.success('注册成功')
            }
        },
        getUserInfo({ state, commit }) {
            return new Promise((resolve, reject) => {
              getUserInfoAPI(state.userId).then(response => {
                const data = response
                if (!data) {
                  reject('登录已过期，请重新登录')
                }
                commit('set_userInfo', data)
                commit('set_userId', data.id)
                resolve(data)
              }).catch(error => {
                reject(error)
              })
            })
        },
        updateUserInfo: async({ state, dispatch }, data) => {
            const params = {
                id: state.userId,
                ...data,
            }
            const res = await updateUserInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
        updatePassword: async({ state,dispatch},data)=>{
            const params = {
                id: state.userId,
                ...data,
            }
            const res=await updatePasswordAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },



        logout: async({ commit }) => {
            removeToken()
            resetRouter()
            commit('reset_state')
        },
          // remove token
        resetToken({ commit }) {
            return new Promise(resolve => {
                removeToken() // must remove  token  first
                commit('reset_state')
                resolve()
            })
        },

        

        registerNormal: async ({state,commit,dispatch})=>{
            const res=await registerNormalAPI(state.userId)
            if(res){
                commit('set_registerVisible',false)
                dispatch('getUserInfo')
                message.success('注册成功')
            }
        },
        registerEnterprise: async ({state,dispatch})=>{
            const res=await registerEnterpriseAPI(state.userId)
            if(res){
                message.success('注册成功')
                dispatch('getUserInfo')
            }
        },
        updateEnterprise: async ({state,commit,dispatch},data)=>{
            const params={
                id:state.userId,
                enterprisename:data,
            }

            const res=await updateEnterpriseAPI(params)
            if(res){
                commit("set_registerVisible",false)
                message.success('恭喜你成为企业会员')
                dispatch('getUserInfo')
            }
        },
        getManagerList: async({ commit }) => {
            const res = await getManagerListAPI()
            if(res){
                commit('set_managerList', res)
            }
        },
        getClientList: async({commit }) => {
            const res = await getClientListAPI()
            if(res){
                commit('set_clientList', res)
            }
        },
        getSaleList:async({commit}) =>{
            const res= await getSaleListAPI()
            if(res){
                commit('set_saleList',res)
            }
        },

        addManager: async({ state, commit, dispatch }) => {
            const res = await addManagerAPI(state.addManagerParams)
            if(res) {
                commit('set_addManagerParams',{
                    email:'',
                    password:'',

                })
                commit('set_addManagerModalVisible', false)
                message.success('添加成功')
                dispatch('getManagerList')
            }else{
                message.error('添加失败')
            }
        },
        addSale:async({ state, commit, dispatch }) => {
            const res = await addSaleAPI(state.addManagerParams)
            if(res) {
                commit('set_addManagerParams',{
                    email:'',
                    password:'',

                })
                commit('set_addSaleVisible', false)
                message.success('添加成功')
                dispatch('getSaleList')
            }else{
                message.error('添加失败')
            }
        },
        addClient: async({ state, commit, dispatch }) => {
            const res = await addClientAPI(state.addClientParams)
            if(res) {
                commit('set_addClientParams',{
                    email:'',
                    password:'',
                    username:'',
                    phonenumber:'',
                    credit:'',
                    birthday:'',
                    memberlevel:'',
                    enterprisename:''
                })
                commit('set_addClientVisible', false)
                message.success('添加成功')
                dispatch('getClientList')
            }else{
                message.error('添加失败')
            }
        },

        DeleteManager: async({state,dispatch},id)=>{
            const res = await deleteManagerAPI(id)
            if(res){
                message.success('删除成功')
                dispatch('SearchHotels')
                dispatch('getManagerList')
                dispatch('getSaleList')
            }
            else{
                message.error('删除失败')
            }
        },
        DeleteClient: async({state,dispatch},id)=>{

            const res = await deleteClientAPI(id)
            if(res){
                message.success('删除成功')
                dispatch('getClientList')
            }
            else{
                message.error('删除失败')
            }
        },
        updateAllUserInfo:async({state,commit,dispatch},data) =>{
            const params = {
                id: state.activeUserId,
                ...data,
            }
            const res = await updateAllUserInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getClientList')
                commit('set_updateClientVisible',false)
            }
        },
        updateManagerInfo:async({state,commit,dispatch},data) =>{
            const params = {
                id: state.activeManagerId,
                ...data,
            }
            const res = await updateManagerInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getManagerList')
                dispatch('getSaleList')
                commit('set_updateManagerVisible',false)
            }
        },
        cancelCredit:async({state,dispatch},userId)=>{
            const res = await cancelCreditAPI(userId)
            if(res){
                dispatch('getUserInfo')
                message.success('退出会员成功')
            }
            else{
                message.error('退出会员失败')
            }
        },
        initialization:async()=>{
            const res=await initializeAPI();
            if(res){
                message.success('初始化成功')
            }
        }
    }
}

export default user
import { axios } from '@/utils/request'

const api = {
    userPre: '/api/user'
}
export function loginAPI(data){
    return axios({
        url:`${api.userPre}/login`,
        method: 'POST',
        data
    })
}
export function registerAPI(data){
    return axios({
        url: `${api.userPre}/register`,
        method: 'POST',
        data
    })
}
export function getUserInfoAPI(id){
    return axios({
        url: `${api.userPre}/${id}/getUserInfo`,
        method: 'GET'
    })
}
export function updateUserInfoAPI(data) {
    return axios({
        url: `${api.userPre}/${data.id}/userInfo/update`,
        method: 'POST',
        data
    })
}
export function updatePasswordAPI(data) {
    return axios({
        url: `${api.userPre}/${data.id}/userInfo/password`,
        method: 'POST',
        data
    })
}

export function registerNormalAPI(id){
    return axios({
        url: `${api.userPre}/${id}/registerNormal`,
        method: 'POST',
    })
}
export function registerEnterpriseAPI(id) {
    return axios({
        url: `${api.userPre}/${id}/registerEnterprise`,
        method: 'POST',
    })
}
export function updateEnterpriseAPI(data) {
    return axios({
        url:`${api.userPre}/userInfo/enterprisename`,
        method: 'POST',
        data
    })
}
export function getManagerListAPI(){
    return axios({
        url: `${api.userPre}/getAllManagers`,
        method: 'POST'
    })
}
export function getClientListAPI(){
    return axios({
        url: `${api.userPre}/getAllClients`,
        method: 'POST'
    })
}
export function getSaleListAPI(){
    return axios({
        url:`${api.userPre}/getAllSales`,
        method: 'POST'
    })
}
export function addManagerAPI(data) {
    return axios({
        url: `${api.userPre}/addManager`,
        method: 'POST',
        data
    })
}
export function addSaleAPI(data) {
    return axios({
        url: `${api.userPre}/addSale`,
        method: 'POST',
        data
    })
}
export function addClientAPI(data) {
    return axios({
        url: `${api.userPre}/addClient`,
        method: 'POST',
        data
    })
}
export function updateAllUserInfoAPI(data){
    return axios({
        url: `${api.userPre}/${data.id}/userInfo/managerupdate`,
        method: 'POST',
        data
    })
}
export function updateManagerInfoAPI(data){
    return axios({
        url: `${api.userPre}/${data.id}/managerInfo/update`,
        method: 'POST',
        data
    })
}

export function deleteManagerAPI(id){
    return axios({
        url:`${api.userPre}/${id}/deleteManager`,
        method:'GET'
    })
}
export function deleteClientAPI(id){
    return axios({
        url:`${api.userPre}/${id}/deleteClient`,
        method:'GET'
    })
}

export function initializeAPI() {
    return axios({
        url: `${api.userPre}/initialize`,
        method:'GET',
    })
}
export function cancelCreditAPI(userId){
    return axios({
        url:`${api.userPre}/${userId}/cancelCredit`,
        method:'POST',
    })
}





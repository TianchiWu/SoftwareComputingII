import { axios } from '@/utils/request'
const api = {
    orderPre: '/api/order'
}
export function reserveHotelAPI(data) {
    return axios({
        url: `${api.orderPre}/addOrder`,
        method: 'POST',
        data,
    })
}
export function getAllOrdersAPI(userId) {
    return axios({
        url: `${api.orderPre}/${userId}/getAllOrders`,
        method: 'GET',
    })
}

export function getUserOrdersAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.userId}/getUserOrders`,
        method: 'GET',
    })
}
export function getUserOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/getOrderById`,
        method: 'GET',
    })
}
export function cancelOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/annulOrder`,
        method: 'GET',
    })
}
export function finishOrderAPI(orderId){
    return axios({
        url: `${api.orderPre}/${orderId}/checkin`,
        method: 'GET',
    })
}
export function checkoutAPI(orderId){
    return axios({
        url:`${api.orderPre}/${orderId}/checkout`,
        method:'GET',
    })
}
export function getAbnormalOrderListAPI(){
    return axios({
        url: `${api.orderPre}/getAbnormalOrder`,
        method: 'GET'
    })
}

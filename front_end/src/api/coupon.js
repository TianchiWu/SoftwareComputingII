import { axios } from '@/utils/request'
const api = {
    couponPre: '/api/coupon'
}

export function hotelAllCouponsAPI(hotelId) {
    return axios({
        url: `${api.couponPre}/hotelAllCoupons`,
        method: 'GET',
        params: {hotelId: hotelId},
    })
}

export function orderMatchCouponsAPI(params) {
    return axios({
        url: `${api.couponPre}/orderMatchCoupons`,
        method: 'GET',
        params,
    })
}

export function addCouponAPI(data){
    return axios({
        url: `${api.couponPre}/addCoupon`,
        method: 'POST',
        data,
    })
}


export function deleteCouponAPI(id) {
    return axios({
        url: `${api.couponPre}/${id}/deleteCoupon`,
        method: 'GET',
    })
}
export function salesAllCouponsAPI() {
    return axios({
        url: `${api.couponPre}/salesAllCoupons`,
        method: 'GET',
    })
}
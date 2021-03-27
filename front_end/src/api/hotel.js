import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function SearchHotelsAPI(data) {
    return axios({
        url: `${api.hotelPre}/hotelSearch/search`,
        method: 'POST',
        data,
    })
}
export function getHotelsByManagerIdAPI(userId){
    return axios({
        url: `${api.hotelPre}/${userId}/getHotelsByManagerId`,
        method: 'get'
    })
}
export function getHotelByIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/detail`,
        method: 'GET',
    })
}

export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/addHotel`,
        method: 'POST',
        data,
    })
}
export function deleteHotelAPI(hotelId) {
    return axios({
        url: `${api.hotelPre}/${hotelId}/deleteHotel`,
        method: 'GET',
    })
}
export function setManagerAPI(hotelid,managerId) {
    return axios({
        url:`${api.hotelPre}/${hotelid}/${managerId}/setManager`,
        method: 'GET'
    })
}
export function updateHotelInfoAPI(data){
    return axios({
        url: `${api.hotelPre}/${data.id}/hotelInfo/update`,
        method: 'POST',
        data
    })
}
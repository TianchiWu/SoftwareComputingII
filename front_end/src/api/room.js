import { axios } from '@/utils/request'
const api = {
    roomPre: '/api/room'
}
export function getActiveRoomListAPI(hotelId){
    return axios({
        url:`${api.roomPre}/${hotelId}/roomlist`,
        method: 'GET'
    })
}
export function deleteRoomAPI(roomListId){
    return axios({
        url:`${api.roomPre}/${roomListId}/deleteRoom`,
        method:'GET'
    })
}
export function updateRoomNumAPI(roomId,num){
    return axios({
        url:`${api.roomPre}/${roomId}/${num}/updateRoomNum`,
        method: 'GET'
    })
}
export function getActiveRoomTypeAPI(hotelId){
    return axios({
        url:`${api.roomPre}/${hotelId}/getActiveRoomType`,
        method:'GET'
    })

}
export function deleteRoomsAPI(roomId,num){
    return axios({
        url:`${api.roomPre}/${roomId}/${num}/deleteRooms`,
        method:'GET'
    })
}

export function addRoomAPI(data) {
    return axios({
        url: `${api.roomPre}/addroom`,
        method: 'POST',
        data,
    })
}
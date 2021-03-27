import { axios } from '@/utils/request'
const api = {
    commentPre: '/api/comment'
}
export function getCommentsAPI(hotelId) {
    return axios({
        url:`${api.commentPre}/${hotelId}/getComments`,
        method:'GET',
    })
}
export function addCommentAPI(data){
    return axios({
        url:`${api.commentPre}/addComment`,
        method:'POST',
        data
    })
}
export function enableAPI(hotelId,userId){
    return axios({
        url:`${api.commentPre}/${hotelId}/${userId}/enable`,
        method:'GET',
    })
}

export function deleteCommentAPI(id){
    return axios({
        url:`${api.commentPre}/${id}/deleteComment`,
        method:'POST',
    })
}
export function updateCommentAPI(data) {
    return axios({
        url:`${api.commentPre}/${data.id}/${data.rate}/${data.text}/updateComment`,
        method:'GET',
    })
}
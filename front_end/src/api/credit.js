import { axios } from '@/utils/request'
const api = {
    creditPre: '/api/credit'
}
export function addCreditAPI(data) {
    return axios({
        url: `${api.creditPre}/${data.id}/${data.credit}/addCredit`,
        method: 'GET',
    })
}


export function getCreditListAPI(id) {
    return axios({
        url: `${api.creditPre}/${id}/getCreditList`,
        method: 'Get',
    })
}

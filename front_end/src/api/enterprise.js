import { axios } from '@/utils/request'
const api = {
    enterprisePre: '/api/enterprise'
}
export function getEnterpriseListAPI() {
    return axios({
        url: `${api.enterprisePre}/getAllEnterprises`,
        method: 'POST',
    })
}
export function addEnterpriseAPI(data) {
    return axios({
        url: `${api.enterprisePre}/addEnterprise`,
        method: 'POST',
        data,
    })
}
export function deleteEnterpriseAPI(name) {
    return axios({
        url: `${api.enterprisePre}/${name}/deleteEnterprise`,
        method: 'GET',
        name
    })
}
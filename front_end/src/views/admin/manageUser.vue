<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addHotel"><a-icon type="plus" />添加酒店</a-button>
                </div>
                <a-table
                        :columns="columns1"
                        :dataSource="hotelList"
                        bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="updateHotel(record)">查看酒店信息</a-button>
                        <a-button type="primary" size="small" style="margin-left: 10px" @click="setManager(record)">设置酒店管理员</a-button>
                        <a-popconfirm
                                title="确定想删除该酒店吗？"
                                @confirm="deleteHotel(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small" style="margin-left: 10px">删除酒店</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="查看酒店管理员信息" key="2">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary"  @click="addManager()"><a-icon type="plus" />添加酒店管理员</a-button>
                </div>
                <a-table
                        :columns="columns2"
                        :dataSource="managerList"
                        bordered
                >

                <span slot="action" slot-scope="record">
                    <a-button type="primary" size="small" @click="updateManager(record)">查看用户信息</a-button>
                        <a-popconfirm
                                style="margin-left: 15px"
                                title="确定想删除该管理员吗？"
                                @confirm="deleteManager(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除管理员</a-button>
                        </a-popconfirm>

                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="查看用户信息" key="3">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addClient()"><a-icon type="plus" />添加用户</a-button>
                </div>
                <a-table
                        :columns="columns3"
                        :dataSource="clientList"
                        bordered
                >

                <span slot="action" slot-scope="record">
                            <a-button type="primary" size="small" @click="updateClient(record)">查看用户信息</a-button>
                        <a-popconfirm
                                style="margin-left: 15px"
                                title="确定想删除该用户吗？"
                                @confirm="deleteClient(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除用户</a-button>
                        </a-popconfirm>

                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="查看网站营销人员信息" key="4">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addSale()"><a-icon type="plus" />添加酒店管理员</a-button>
                </div>
                <a-table
                        :columns="columns4"
                        :dataSource="saleList"
                        bordered
                >

                <span slot="action" slot-scope="record">
                    <a-button type="primary" size="small" @click="updateManager(record)">查看网站营销人员信息</a-button>
                        <a-popconfirm
                                style="margin-left: 25px"
                                title="确定想删除该网站营销人员吗？"
                                @confirm="deleteManager(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除网站营销人员</a-button>
                        </a-popconfirm>

                    </span>
                </a-table>
            </a-tab-pane>

            <a-tab-pane tab="合作事业单位" key="5">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addEnterprise()"><a-icon type="plus" />添加合作单位</a-button>
                </div>

                <a-table
                    :columns="columns5"
                    :dataSource="enterpriseList"
                    bordered
                    >
                    <span slot="action" slot-scope="record">
                        <a-popconfirm
                                title="确定想删除该企业单位？"
                                @confirm="deleteEnterprise(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除企业</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddManagerModal></AddManagerModal>
        <AddHotelModal></AddHotelModal>
        <SetManager></SetManager>
        <UpdateClient></UpdateClient>
        <AddClient></AddClient>
        <UpdateManager></UpdateManager>
        <UpdateHotel></UpdateHotel>
        <AddSale></AddSale>
        <AddEnterprise></AddEnterprise>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddManagerModal from './components/addManagerModal'
import AddClient from './components/addClient'
import AddHotelModal from './components/addHotelModal'
import SetManager from './components/setManager'
import UpdateClient from './components/updateClient'
import UpdateManager from './components/updateManager'
import UpdateHotel from './components/updateHotel'
import AddSale from './components/addSale'
import AddEnterprise from './components/addEnterprise'
const moment = require('moment')
const columns1 = [
    {
        title: '酒店名',
        dataIndex: 'hotelName',
    },
    {
        title: '地址',
        dataIndex: 'address',
    },
    {
        title: '酒店星级',
        sorter: (a,b) => {
            if(a.hotelStar=="一星级"){
                a="1"
            }
            else if(a.hotelStar=="二星级"){
                a="2"
            }
            else if(a.hotelStar=="三星级")[
                a="3"
            ]
            else if(a.hotelStar=="四星级"){
                a="4"
            }
            else if(a.hotelStar=="五星级"){
                a="5"
            }
            if(b.hotelStar=="一星级"){
                b="1"
            }
            else if(b.hotelStar=="二星级"){
                b="2"
            }
            else if(b.hotelStar=="三星级")[
                b="3"
            ]
            else if(b.hotelStar=="四星级"){
                b="4"
            }
            else if(b.hotelStar=="五星级"){
                b="5"
            }
            return a-b
        },
        filters: [{ text: '一星级', value: '一星级' }, { text: '二星级', value: '二星级' }, { text: '三星级', value: '三星级' }, { text: '四星级', value: '四星级' }, { text: '五星级', value: '五星级' },],
        onFilter: (value, record) => record.hotelStar.includes(value),
        dataIndex: 'hotelStar'
    },
    {
        title: '评分',
        sorter:(a,b)=>a.rate-b.rate,
        dataIndex: 'rate',
    },
    {
        title: '联系电话',
        dataIndex: 'phoneNum'
    },
    {
        title: '简介',
        dataIndex: 'description',
    },
    {
       title: '酒店管理员id',
        sorter:(a,b)=>a.managerId-b.managerId,
        dataIndex: 'managerId',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
const columns2 = [
    {
        title: '管理员id',
        sorter:(a,b)=>a.id-b.id,
        dataIndex: 'id',
    },
    {
        title: '邮箱',
        dataIndex: 'email',
    },
    {
        title: '密码',
        dataIndex: 'password',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
const columns3 = [
    {
        title: '客户id',
        sorter:(a,b)=>a.id-b.id,
        dataIndex: 'id',
    },
    {
        title: '邮箱',
        sorter:(a,b)=>a.email.localeCompare(b.email),
        dataIndex: 'email',
    },
    {
        title: '密码',
        dataIndex: 'password',
    },
    {
        title: '用户名',
        sorter:(a,b)=>a.userName.localeCompare(b.userName),
        dataIndex: 'userName',
    },
    {
        title: '电话号码',
        dataIndex: 'phoneNumber',
    },
    {
        title: '信用值',
        sorter:(a,b)=>a.credit-b.credit,
        dataIndex: 'credit',
    },
    {
        title: '生日',
        sorter:(a,b)=>moment(a.birthday).diff(moment(b.birthday)),
        dataIndex: 'birthday',
    },
    {
        title: '会员类型',
        filters: [{ text: '普通会员', value: '普通会员' }, { text: '企业会员', value: '企业会员' }, { text: '非会员', value: null } ],
        onFilter: (value, record) => record.membertype.includes(value),
        dataIndex: 'membertype',
    },
    {
        title:'会员级别',
        filters: [{ text: 'VIP1', value: 'VIP1' }, { text: 'VIP2', value: 'VIP2' }, { text: 'VIP3', value: 'VIP3' } ,{ text: 'VIP4', value: 'VIP4' },{ text: 'VIP5', value: 'VIP5' },{ text: '非普通会员', value: null }   ],
        onFilter: (value, record) => record.memberlevel.includes(value),
        dataIndex:'memberlevel',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
const columns4 = [
    {
        title: '网站营销人员id',
        sorter:(a,b)=>a.id-b.id,
        dataIndex: 'id',
    },
    {
        title: '邮箱',
        dataIndex: 'email',
    },
    {
        title: '密码',
        dataIndex: 'password',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
const columns5 = [
    {
        title: '单位id',
        sorter:(a,b)=>a.id-b.id,
        dataIndex: 'id',
    },
    {
        title: '单位名称',
        dataIndex: 'name',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
export default {
    name: 'manageUser',
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns1,
            columns2,
            columns3,
            columns4,
            columns5,
            form: this.$form.createForm(this, { name: 'manageUser' }),
        }
    },
    components: {
        AddClient,
        AddManagerModal,
        AddHotelModal,
        SetManager,
        UpdateClient,
        UpdateManager,
        UpdateHotel,
        AddSale,
        AddEnterprise
    },
    computed: {
        ...mapGetters([
            'managerList',
            'clientList',
            'saleList',
            'userId',
            'hotelList',
            'currentHotelInfo',
            'currentHotelId',
            'enterpriseList',
            'allHotelData',
        ])
    },
     async mounted() {
         await this.SearchHotels()
         await this.getManagerList()
         await this.getClientList()
         await this.getSaleList()
         await this.getEnterpriseList()
     },
    methods: {
        ...mapActions([
            'getManagerList',
            'getClientList',
            'SearchHotels',
            'getSaleList',
            'DeleteHotel',
            'DeleteManager',
            'DeleteClient',
            'getEnterpriseList',
            'DeleteEnterprise'
        ]),
        ...mapMutations([
            'set_addManagerModalVisible',
            'set_addClientVisible',
            'set_activeUserId',
            'set_activeManagerId',
            'set_updateHotelId',
            'set_addHotelModalVisible',
            'set_activeHotelId',
            'set_currentHotelId',
            'set_setManagerVisible',
            'set_updateClientVisible',
            'set_updateManagerVisible',
            'set_updateHotelVisible',
            'set_updateHotelInfo',
            'set_activeUserInfo',
            'set_activeManagerInfo',
            'set_addSaleVisible',
            'set_addEnterpriseVisible',
        ]),
        setManager(record){
            this.set_activeHotelId(record.id)
            this.set_setManagerVisible(true)
        },
        addManager(){
            this.set_addManagerModalVisible(true)
        },
        addSale(){
            this.set_addSaleVisible(true)
        },
        addClient(){
            this.set_addClientVisible(true)
        },
        addHotel(){
            this.set_addHotelModalVisible(true)
        },
        addEnterprise(){
            this.set_addEnterpriseVisible(true)
        },
        updateClient(record){
            this.set_activeUserId(record.id)
            this.set_activeUserInfo(record)
            this.set_updateClientVisible(true)
        },
        updateManager(record){
            this.set_activeManagerId(record.id)
            this.set_activeManagerInfo(record)
            this.set_updateManagerVisible(true)
        },
        updateHotel(record){
            this.set_updateHotelId(record.id)
            this.set_updateHotelInfo(record)
            this.set_updateHotelVisible(true)
        },
        deleteHotel(record){
            this.set_activeHotelId(record.id)
            this.DeleteHotel()
        },
        deleteManager(record){
            this.DeleteManager(record.id);
        },
        deleteClient(record){
            this.DeleteClient(record.id);
        },
        deleteEnterprise(record){
            this.DeleteEnterprise(record.name)
        }
    }
}
</script>
<style scoped lang="less">
    .manageUser-wrapper {
        padding: 50px;
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageUser-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>
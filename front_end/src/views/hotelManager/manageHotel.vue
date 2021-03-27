<template>
    <div class="manageHotel-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店管理" key="1">
                 <a-table
                    :columns="columns1"
                    :dataSource="hotelList"
                    bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showRoom(record)">查看房间</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="info" size="small" @click="showCoupon(record)">优惠策略</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="danger" size="small" @click="updateHotel(record)">修改酒店信息</a-button>
                        <a-divider type="vertical"></a-divider>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="订单管理" key="2">
                <a-table
                    :columns="columns2"
                    :dataSource="orderList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showOrder(record.id)">订单详情</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="你确定执行该笔订单吗？"
                                @confirm="confirmFinishOrder(record)"
                                @cancel="cancelFinishOrder"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState==='已预订'"
                        >
                            <a-button type="primary" size="small">执行订单</a-button>
                        </a-popconfirm>
                        <a-popconfirm
                                title="你确定补充执行该笔订单吗？"
                                @confirm="confirmFinishOrder(record)"
                                @cancel="cancelFinishOrder"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState === '入住异常'"
                        >
                            <a-button type="danger" size="small">补充执行订单</a-button>
                        </a-popconfirm>
                        <a-popconfirm
                                title="你确定退房吗？"
                                @confirm="confirmCheckout(record)"
                                @cancel="cancelCheckout"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState === '已入住'"
                        >
                            <a-button type="danger" size="small">退房</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>

        </a-tabs>
        <Coupon></Coupon>
        <ShowManageOrder></ShowManageOrder>
        <ShowRooms></ShowRooms>
        <UpdateHotel></UpdateHotel>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import Coupon from './components/coupon'
import ShowManageOrder from './components/showManageOrder'
import ShowRooms from './components/showRooms'
import UpdateHotel from './components/updateHotel'
const moment = require('moment')
const columns1 = [
    {
      title:'酒店id',
      sorter:(a,b)=>a.id-b.id,
      dataIndex:'id'
    },
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
        dataIndex: 'hotelStar',
        sorter: (a,b) => {
            if(a.hotelStar==="一星级"){
                a="1"
            }
            else if(a.hotelStar==="二星级"){
                a="2"
            }
            else if(a.hotelStar==="三星级")[
                a="3"
            ]
            else if(a.hotelStar==="四星级"){
                a="4"
            }
            else if(a.hotelStar==="五星级"){
                a="5"
            }
            if(b.hotelStar==="一星级"){
                b="1"
            }
            else if(b.hotelStar==="二星级"){
                b="2"
            }
            else if(b.hotelStar==="三星级")[
                b="3"
            ]
            else if(b.hotelStar==="四星级"){
                b="4"
            }
            else if(b.hotelStar==="五星级"){
                b="5"
            }
            return a-b
        },
        filters: [{ text: '一星级', value: '一星级' }, { text: '二星级', value: '二星级' }, { text: '三星级', value: '三星级' }, { text: '四星级', value: '四星级' }, { text: '五星级', value: '五星级' },],
        onFilter: (value, record) => record.hotelStar.includes(value),
        scopedSlots: { customRender: 'hotelStar' }
    },
    {
        title: '评分',
        sorter:(a,b)=>a.rate-b.rate,
        dataIndex: 'rate',
    },
    {
        title: '简介',
        dataIndex: 'description',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
const columns2 = [
    {  
        title: '订单号',
        sorter:(a,b)=>a.id-b.id,
        dataIndex: 'id',
    },
    {  
        title: '酒店名',
        dataIndex: 'hotelName',
    },
    {
        title: '房型',
        dataIndex: 'roomType',
        filters: [{ text: '大床房', value: 'BigBed' }, { text: '双床房', value: 'DoubleBed' }, { text: '家庭房', value: 'Family' },],
        onFilter: (value, record) => record.roomType.includes(value),
        scopedSlots: { customRender: 'roomType' }
    },
    {
        title: '入住时间',
        dataIndex: 'checkInDate',
        sorter:(a,b)=> moment(a.checkInDate).diff(moment(b.checkInDate)),
        scopedSlots: { customRender: 'checkInDate' }
    },
    {
        title: '离店时间',
        dataIndex: 'checkOutDate',
        sorter:(a,b)=> moment(a.checkOutDate).diff(moment(b.checkOutDate)),
        scopedSlots: { customRender: 'checkOutDate' }
    },
    {
        title: '入住人数',
        sorter:(a,b)=>a.peopleNum-b.peopleNum,
        dataIndex: 'peopleNum',
    },
    {
        title: '房价',
        sorter:(a,b)=>a.price-b.price,
        dataIndex: 'price',
    },
    {
        title: '状态',
        filters: [{ text: '已预订', value: '已预订' }, { text: '已撤销', value: '已撤销' }, { text: '已入住', value: '已入住' }, { text: '已退房', value: '已退房' }, { text: '入住异常', value: '入住异常' }, { text: '退房异常', value: '退房异常' }],
        onFilter: (value, record) => record.orderState.includes(value),
        dataIndex: 'orderState',
        scopedSlots: { customRender: 'orderState' }
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
export default {
    name: 'manageHotel',
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns1,
            columns2,
            form: this.$form.createForm(this, { name: 'manageHotel' }),
        }
    },
    components: {
        Coupon,
        ShowManageOrder,
        ShowRooms,
        UpdateHotel,
    },
    computed: {
        ...mapGetters([
            'userId',
            'userType',
            'orderList',
            'hotelList',
            'addRoomModalVisible',
            'couponVisible',
            'activeOrderData',
            'currentHotelInfo',
            'currentHotelId',
        ]),
    },
    async mounted() {
        await this.set_managerId(this.userId)
        await this.getHotelListByManagerId(this.userId)
        await this.set_ManagerId(this.userId)
        await this.getAllOrders()
    },
    methods: {
        ...mapMutations([
            'set_couponVisible',
            'set_activeHotelId',
            'set_activeOrderId',
            'set_orderVisible',
            'set_currentHotelId',
            'set_managerId',
            "set_ManagerId",
            'set_roomVisible',
            'set_activeRoomId',
            'set_updateHotelId',
            'set_updateHotelInfo',
            'set_updateHotelVisible',
            "set_activeCouponHotelId",
            "set_activeRoomHotelId"
        ]),
        ...mapActions([
            'getHotelListByManagerId',
            'getAllOrders',
            'getHotelCoupon',
            'getUserOrder',
            'FinishOrder',
            'DeleteHotel',
            'getActiveRooms',
            'checkout',
        ]),
        showOrder(orderId){
            this.set_activeOrderId(orderId)
            this.set_orderVisible(true)
            this.getUserOrder(this.userId)
        },
        showRoom(record){
            this.set_activeRoomHotelId(record.id)
            this.set_roomVisible(true);
            this.getActiveRooms();
        },

        showCoupon(record) {
            this.set_activeCouponHotelId(record.id)
            this.set_couponVisible(true)
            this.getHotelCoupon()

        },
         confirmFinishOrder(record){
             this.set_ManagerId(this.userId)
            this.FinishOrder(record.id)

        },
        cancelFinishOrder() {

        },
        async confirmCheckout(record){
            this.set_ManagerId(this.userId)
            this.checkout(record.id)
        },
        cancelCheckout(){

        },
        updateHotel(record){
            this.set_updateHotelId(record.id)
            this.set_updateHotelInfo(record)
            this.set_updateHotelVisible(true)
        },
    }
}
</script>
<style scoped lang="less">
    .manageHotel-wrapper {
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
    .manageHotel-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>
<template>
    <div class="sale-wrapper">
        <a-tabs>
            <a-tab-pane tab="用户管理" key="1">
                <a-table
                        :columns="columns1"
                        :dataSource="clientList"
                        bordered
                >
                    <span slot="action" slot-scope="state">
                        <a-button type="primary" size="small" @click="setAddCredit(state)">信用充值</a-button>
                        <a-divider type="vertical"></a-divider>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="异常订单管理" key="2">
                <a-table
                        :columns="columns2"
                        :dataSource="abnormalOrderList"
                        bordered
                >
                    <span slot="action" slot-scope="record">

                        <a-popconfirm
                                title="你确定撤销该笔订单吗？"
                                @confirm="confirmCancelOrder(record)"
                                @cancel="cancelCancelOrder"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState === '入住异常' "
                        >
                            <a-button type="danger" size="small">撤销</a-button>
                        </a-popconfirm>

                        <a-popconfirm
                                title="你确定撤销该笔订单吗？"
                                @confirm="confirmCancelOrder(record)"
                                @cancel="cancelCancelOrder"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState === '退房异常' "
                        >
                            <a-button type="danger" size="small">撤销</a-button>
                        </a-popconfirm>

                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="优惠策略" key="3">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addCoupon"><a-icon type="plus" />添加优惠策略</a-button>
                </div>
                <a-table
                        :columns="columns3"
                        :dataSource="salesCouponList"
                >
                    <a-tag color="red" slot="couponName" slot-scope="text">
                        {{text}}
                    </a-tag>
                    <span slot="couponType" slot-scope="text">
                <span v-if="text === 1">生日特惠</span>
                <span v-if="text === 2">多间特惠</span>
                <span v-if="text === 3">限时优惠</span>
            </span>
                    <span slot="property" slot-scope="text">
                <span v-if="text === 0">打折券</span>
                <span v-if="text === 1">满减券</span>
            </span>
                    <span slot="targetMoney" slot-scope="text">
                <span v-if="text === -1">无</span>
                <span v-else>{{text}}</span>
            </span>
                    <span slot="discountMoney" slot-scope="text">
                <span v-if="text === -1">无</span>
                <span v-else>{{text}}</span>
            </span>
                    <span slot="discount" slot-scope="text">
                <span v-if="text === -1">无</span>
                <span v-else>{{text}}</span>
            </span>
                    <span slot="action" slot-scope="record">
                        <a-popconfirm
                                title="确定想删除该优惠策略？"
                                @confirm="deleteCoupon(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除</a-button>
                        </a-popconfirm>
            </span>
                </a-table>
            </a-tab-pane>

        </a-tabs>
        <AddCredit></AddCredit>
        <AddCoupon></AddCoupon>
    </div>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddCredit from './components/addCredit'
import AddCoupon from './components/addCoupon'
    const moment = require('moment')
    const columns1 = [
        {
            title: '客户id',
            sorter:(a,b)=>a.id-b.id,
            dataIndex: 'id',
        },
        {
            title: '邮箱',
            dataIndex: 'email',
        },
        {
            title: '信用值',
            sorter:(a,b)=>a.credit-b.credit,
            dataIndex: 'credit',
        },
        {
            title: '会员类型',
            filters: [{ text: '企业会员', value: '企业会员' }, { text: '普通会员', value: '普通会员' }],
            onFilter: (value, record) => record.membertype.includes(value),
            dataIndex: 'membertype',
        },
        {
            title: '会员级别',
            sorter:(a,b)=>a.memberlevel.charAt(3)-b.memberlevel.charAt(3),
            dataIndex:'memberlevel'
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
            filters: [{text:'异常订单',value:'异常订单'},{ text: '已撤销', value: '已撤销' }],
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
    const columns3 = [
        {
          title:'优惠券id',
          dataIndex:'id',
          sorter:(a,b)=>a.id-b.id,
          scopedSlots: {customRender: 'id'}
        },
        // 这里定义列表头
        {
            title: '优惠券名称',
            dataIndex: 'couponName',
            scopedSlots: {customRender: 'couponName'}
        },
        {
            title: '优惠券类型',
            dataIndex: 'couponType',
            key:'couponType',
            filters: [{ text: '生日特惠', value: 1 }, { text: '多间特惠', value: 2 },{ text: '限时优惠', value: 3 }],
            onFilter: (value, record) => record.couponType,
            scopedSlots: {customRender: 'couponType'}
        },
        {
            title: '优惠券属性',
            dataIndex: 'property',
            key:'property',
            filters: [{ text: '打折券', value: 0 }, { text: '满减券', value: 1 }],
            onFilter: (value, record) => record.property,
            scopedSlots: {customRender: 'property'}
        },
        {
            title: '使用门槛',
            dataIndex: 'targetMoney',
            sorter:(a,b)=>a.targetMoney-b.targetMoney,
            scopedSlots: {customRender: 'targetMoney'}
        },
        {
            title: '优惠⾦额',
            dataIndex: 'discountMoney',
            sorter:(a,b)=>a.discountMoney-b.discountMoney,
            scopedSlots: {customRender: 'discountMoney'}
        },
        {
            title: '折扣',
            dataIndex: 'discount',
            sorter:(a,b)=>a.discount-b.discount,
            scopedSlots: {customRender: 'discount'}
        },
        {
            title: '优惠简介',
            dataIndex: 'description',
        },
        {
            title: '开始时间',
            sorter:(a,b)=> moment(a.startTime).diff(moment(b.startTime)),
            dataIndex: 'startTime',
        },
        {
            title: '结束时间',
            sorter:(a,b)=> moment(a.endTime).diff(moment(b.endTime)),
            dataIndex: 'endTime'
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: { customRender: 'action' },
        }
    ];

    export default {
        name: 'sale',
        data(){
            return {
                columns1,
                columns2,
                columns3,
                formLayout: 'horizontal',
                pagination: {},

                form: this.$form.createForm(this, { name: 'sale' }),
            }
        },
        components: {
            AddCredit,
            AddCoupon

        },
        computed: {
            ...mapGetters([
                'userId',
                'clientList',
                'abnormalOrderList',
                'addCreditVisible',
                'salesCouponList'
            ]),
        },
        async mounted() {
                await this.getClientList();
                await this.getAbnormalOrderList();
                await this.getAllSalesCoupon();
        },
        methods: {
            ...mapMutations([
                "set_activeUserId",
                "set_addCreditVisible",
                'set_activeUserInfo',
                'set_addCouponVisible',
            ]),
            ...mapActions([
                'getClientList',
                'getAbnormalOrderList',
                'cancelOrder',
                'getAllSalesCoupon',
                'DeleteCoupon',
                "getUserInfo",
                "getCreditList",
            ]),
            setAddCredit(state){

                this.set_activeUserId(state.id);
                this.set_activeUserInfo(state);
                this.set_addCreditVisible(true);
            },
            confirmCancelOrder(record){
                this.cancelOrder(record.id)
            },
            cancelCancelOrder() {
            },
            addCoupon(){
                this.set_addCouponVisible(true)
            },
            deleteCoupon(record){
                this.DeleteCoupon(record)
            },
        }
    }
</script>
<style scoped lang="less">
    .sale-wrapper {
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
    .sale-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>
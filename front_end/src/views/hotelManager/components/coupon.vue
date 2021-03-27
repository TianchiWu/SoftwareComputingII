np<template>

    <a-modal
        :visible="couponVisible"
        title="优惠策略"
        width="900px"
        :footer="null"
        @cancel="cancel"
    >
        <!-- 这里是模态框内容区域，请编写列表代码与添加策略按钮 -->
        <div style="width: 100%; text-align: right; margin:20px 0">
            <a-button type="primary" @click="addCoupon"><a-icon type="plus" />添加优惠策略</a-button>
        </div>
        <div class="list">
        <a-table
                :columns="columns"
                :dataSource="couponList"
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
        </div>
        <AddCoupon></AddCoupon>
    </a-modal>

</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddCoupon from './addCoupon'
const moment = require('moment')

const columns = [
    {
        title:'优惠券id',
        dataIndex:'id',
        sorter:(a,b)=>a.id-b.id,
        scopedSlots: {customRender: 'id'}
    },
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
    name: 'coupon',
    data() {
        return {
            data:[],
            columns,
            formItemLayout: {
                labelCol: {
                    xs: { span: 12 },
                    sm: { span: 6 },
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 16 },
                },
            },
        }
    },
    components: {
        AddCoupon,
    },
    computed: {
        ...mapGetters([
            'couponVisible',
            'couponList',
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'coupon' });
    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible',
            'set_couponVisible',
        ]),
        ...mapActions([
            'DeleteCoupon'
        ]),
        cancel() {
            this.set_couponVisible(false)
        },

        addCoupon() {
            this.set_addCouponVisible(true)

        },
        deleteCoupon(record){
            this.DeleteCoupon(record)
        },
    },
}
</script>
<style scoped>

</style>
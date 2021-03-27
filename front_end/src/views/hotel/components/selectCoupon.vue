<template>

    <a-modal
        :visible="selectCouponVisible"
        cancelText="取消"
        @cancel="cancel"
        width="1000px"
        footer=""
        >
        <a-table
            style="margin-top: 30px"
            :columns="columns"
            :dataSource="orderMatchCouponList"
            bordered
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
                <a-button type="primary" size="small" @click="chooseOneCoupon(record)">选择</a-button>
            </span>
        </a-table>
    </a-modal>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
const moment = require('moment')
const columns = [
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
    name: "selectCoupon",
    data() {
        return {
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
            columns,
        }
    },
    computed: {
        ...mapGetters([
            'selectCouponVisible',
            'orderMatchCouponList',
            'currentCoupon',
            'totalPrice',
            'finalPrice',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'selectCoupon' });
        },
        mounted() {
        },
        methods: {
            ...mapMutations([
                'set_selectCouponVisible',
                'set_currentCoupon'
            ]),
            ...mapActions([
                'getTotalPrice',
                'getFinalPrice',
                'setTotalPrice',
                'setFinalPrice',
            ]),
            chooseOneCoupon(record){
                let list = []
                list.push(record)
                this.set_currentCoupon(list)
                this.set_selectCouponVisible(false)
                this.calculate()
            },
            cancel(){
                this.set_selectCouponVisible(false)
            },
            calculate(){
                this.setFinalPrice(this.currentCoupon[0].finalPrice)
            }
        },
    }

    </script>
<style>
    .customWidth{
        width:100px;
    }
</style>
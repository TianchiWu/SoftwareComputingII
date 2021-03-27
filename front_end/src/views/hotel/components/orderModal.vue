<template>
    <a-modal
        :visible="orderModalVisible"
        title="预定详情"
        cancelText="取消"
        okText="下单"
        @cancel="cancelOrder"
        @ok="handleSubmit"
    >
        <a-form :form="form">

            <a-form-item v-bind="formItemLayout" label="当前日期">
                {{getDate()}}
            </a-form-item>

            <a-form-item v-bind="formItemLayout" label="房型信息">
                <span>{{ currentOrderRoom.roomType }}</span>
            </a-form-item>

            <a-form-item v-bind="formItemLayout" label="入住人姓名">
                <a-input
                    v-decorator="[
                        'clientName',
                        { rules: [{required: true, message: '请填写入住人姓名', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="手机号">
                <a-input
                    v-decorator="[
                        'phoneNumber',
                        { rules: [{required: true, message: '请填写入住人联系手机', },{validator:phoneCheck.bind(this)}] }
                    ]"
                    placeholder="请填写入住人联系手机"/>
            </a-form-item>
            
            <a-form-item v-bind="formItemLayout" label="入住日期">
                <a-range-picker
                    format="YYYY-MM-DD"
                    @change="changeDate"
                    v-decorator="[
                        'date', 
                        {
                            rules: [{ required: true, message: '请选择入住时间' },{validator:dateCheck.bind(this)}]
                        }
                    ]"
                    :placeholder="['入住日期','退房日期']"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="入住人数">
                <a-input
                    v-decorator="[
                        'peopleNum',
                        { rules: [{ required: true, message: '请输入入住人数' }] },
                    ]"
                    placeholder="请输入入住人数"
                >
                </a-input>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="有无儿童">
                <a-radio-group
                    v-decorator="[
                        'haveChild',
                        { rules: [{required: true, message: '请选择有无儿童入住', }] }
                    ]"
                >
                    <a-radio :value="1">有</a-radio>
                    <a-radio :value="0">无</a-radio>
                </a-radio-group>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="房间数">
                <a-input
                    v-decorator="[
                        'roomNum',
                        { rules: [{ required: true, message: '请输入房间数' }] },
                    ]"
                    placeholder="请输入房间数"
                    @change="changeRoomNum"
                >
                </a-input>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="房间单价">
                <span>{{ currentOrderRoom.price }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="总价">
                <span>￥{{ totalPrice }}</span>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" v-if="userInfo.membertype!=null" label="VIP专属折扣">
                <span style="margin-left: 20px">
                    专属折扣: {{this.VIPdiscount*10}}折
                </span>
            </a-form-item>
            <a-divider></a-divider>
            <h2 v-if="orderMatchCouponList.length>0||salesCouponList.length>0" style="margin-left: 30px">
                优惠
                <a-button type="primary" size="small" style="margin-left: 275px" @click="selectCoupon">添加优惠券</a-button>
            </h2>
            <a-table
                :columns="columns"
                :dataSource="currentCoupon"
                :showHeader="false"
                bordered
                v-if="currentCoupon.length>0"
            >
                <span slot="property" slot-scope="text">
                    <span v-if="text === 0">打折券</span>
                    <span v-if="text === 1">满减券</span>
                </span>
                <span slot="id">
                    <a-button type="primary" size="small" @click="deleteCoupon">取消选择</a-button>
                </span>
            </a-table>

             <a-form-item v-bind="formItemLayout" label="结算后总价">

                <span>￥{{(finalPrice*this.VIPdiscount).toFixed(2) }}</span>

            </a-form-item>
            <SelectCoupon></SelectCoupon>
        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import SelectCoupon from "./selectCoupon"
import {orderMatchCouponsAPI} from "../../../api/coupon";
const moment = require('moment')
const columns = [
    {
        title: '优惠类型',
        dataIndex: 'property',
        scopedSlots: {customRender: 'property'}
    },
    {
        title: '优惠券名称',
        dataIndex: 'couponName'
    },
    {
        title: '优惠简介',
        dataIndex: 'description',

    },
    {
        title: 'id',
        dataIndex: 'id',
        scopedSlots: {customRender: 'id'}
    }
  ];
export default {
    name: 'orderModal',
    data() {
        return {
            VIPdiscount:1,
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
    components: {
        SelectCoupon,
    },
    computed: {
        ...mapGetters([
            'orderModalVisible',
            'currentOrderRoom',
            'currentHotelId',
            'currentHotelInfo',
            'userId',
            'orderMatchCouponList',
            'userInfo',
            'currentCoupon',
            'totalPrice',
            'finalPrice',
            'salesCouponList',
        ]),

    },
    async mounted() {
        await this.getUserInfo()

        if(this.userInfo.membertype==="企业会员"){
            this.VIPdiscount=0.85
        }
        else if(this.userInfo.membertype==="普通会员"){
            if(this.userInfo.memberlevel==="VIP1"){
                this.VIPdiscount=0.95
            }
            else if(this.userInfo.memberlevel==="VIP2"){
                this.VIPdiscount=0.90
            }
            else if(this.userInfo.memberlevel==="VIP3"){
                this.VIPdiscount=0.85
            }
            else if(this.userInfo.memberlevel==="VIP4"){
                this.VIPdiscount=0.80
            }
            else if(this.userInfo.memberlevel==="VIP5"){
                this.VIPdiscount=0.75
            }
        }
        else{
            this.VIPdiscount=1
        }
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'orderModal' });
        this.form.setFieldsValue({
            'date':'',
            'roomType':'',
            'roomNum':'',
            'peopleNum':'',
            'haveChild':'',
            'phoneNumber':'',
            'clientName':'',
        })
        this.setTotalPrice(0)
        this.setFinalPrice(0)
    },
    methods: {
        ...mapMutations([
            'set_orderModalVisible',
            'set_selectCouponVisible',
            'reset_price',
            'set_currentCoupon',
            "set_salesCouponList",
            "set_orderMatchCouponList"
        ]),
        ...mapActions([
            'addOrder',
            'getOrderMatchCoupons',
            'getUserInfo',
            'getTotalPrice',
            'getFinalPrice',
            'setTotalPrice',
            'setFinalPrice',
        ]),
        selectCoupon(){
            this.set_selectCouponVisible(true)
        },
        cancelOrder() {
            this.set_orderModalVisible(false)
            this.form.setFieldsValue({
                'date':'',
                'roomType':'',
                'roomNum':'',
                'peopleNum':'',
                'haveChild':'',
                'phoneNumber':'',
                'clientName':'',
            })
            this.setTotalPrice(0)
            this.setFinalPrice(0)
            this.set_currentCoupon([])
            this.set_salesCouponList([])
            this.set_orderMatchCouponList([])
        },
        confirmOrder() {

        },
         async changeDate(v) {

            if(this.form.getFieldValue('roomNum')!=''){
                this.setTotalPrice(this.totalPrice = this.form.getFieldValue('roomNum') * moment(v[1]).diff(moment(v[0]), 'day') * Number(this.currentOrderRoom.price))
                await this.deleteCoupon()
                await this.getCoupon()
         }
        },
        changePeopleNum(v){

        },
         async changeRoomNum(v) {
            if(this.form.getFieldValue('date')!='') {
                this.setTotalPrice(Number(v.target.value) * Number(this.currentOrderRoom.price) * moment(this.form.getFieldValue('date')[1]).diff(moment(this.form.getFieldValue('date')[0]), 'day'))
                await this.deleteCoupon()
                await this.getCoupon()
            }
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        hotelId: this.currentHotelId,
                        hotelName: this.currentHotelInfo.hotelName,
                        userId: Number(this.userId),
                        checkInDate: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                        checkOutDate: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                        roomType: this.currentOrderRoom.roomType,
                        roomNum: this.form.getFieldValue('roomNum'),
                        peopleNum: this.form.getFieldValue('peopleNum'),
                        haveChild: this.form.getFieldValue('haveChild'),
                        phoneNumber:this.form.getFieldValue('phoneNumber'),
                        clientName:this.form.getFieldValue('clientName'),
                        createDate: '',
                        price: this.currentCoupon.length === 1 ? this.finalPrice*this.VIPdiscount: this.totalPrice*this.VIPdiscount,
                        roomId:this.currentOrderRoom.id,
                    }

                    if(data.haveChild==1&&data.peopleNum==1){
                        alert("儿童不得独自入住房间")
                    }
                    else if(data.roomType=="大床房"&&data.roomNum*3<data.peopleNum) {
                        alert('预订房间数不能满足入住人数')
                    }
                    else if(data.roomType=='双床房'&&data.roomNum*2<data.peopleNum){
                        alert('预订房间数不能满足入住人数')
                    }
                    else if(data.roomType=='家庭房'&&data.roomNum*5<data.peopleNum){
                        alert('预订房间数不能满足入住人数')
                    }
                    else
                    {
                        this.addOrder(data)
                        this.reset_price()
                        this.form.setFieldsValue({
                            'date':'',
                            'roomType':'',
                            'roomNum':'',
                            'peopleNum':'',
                            'haveChild':'',
                            'phoneNumber':'',
                            'clientName':'',
                        })
                        this.setTotalPrice(0)
                        this.setFinalPrice(0)
                        this.set_currentCoupon([])
                        this.set_salesCouponList([])
                        this.set_orderMatchCouponList([])
                    }

                }
            });
        },
        getDate(){
            var date=new Date();
            var seperator1 = "-";
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            return year + seperator1 + month + seperator1 + strDate;
        },

        //校验手机号码
        phoneCheck(rule,value,callbackFn){
            const validatePhoneReg=/^1\d{10}$/
            if(value!=null&&!validatePhoneReg.test(value)){
                callbackFn('手机号码格式有误')
                return
            }
            callbackFn();
        },
        //校验预定日期
        dateCheck(rule,value,callbackFn){
            var date=this.getDate()
            if(date>value[0].format('YYYY-MM-DD')){
                callbackFn('不能选择'+date+'之前的房间')
                return
            }
            else if(moment(value[1]).diff(moment(value[0]),"day")>30){
                callbackFn('最多只能预订30天')
                return
            }
            callbackFn()
        },
        async getCoupon(){
            let data = {
                userId: this.userId,
                hotelId: this.currentHotelId,
                orderPrice: this.totalPrice,
                roomNum: this.form.getFieldValue('roomNum'),
                checkIn: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                checkOut: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                birthday: this.userInfo.birthday,
            }
            if(this.form.getFieldValue('roomNum')!=''&&this.form.getFieldValue('date')!='') {
               await this.getOrderMatchCoupons(data)
            }
        },
        deleteCoupon(){
            this.set_currentCoupon([])
            this.setFinalPrice(this.totalPrice)
        }
    },
    
}
</script>
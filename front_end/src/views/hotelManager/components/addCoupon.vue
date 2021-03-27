<template>
    <a-modal
        :visible="addCouponVisible"
        title="添加优惠策略"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
        <!-- 这里是添加策略模态框区域，请编写表单 -->
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="优惠券类型" v-bind="formItemLayout">
                <a-select
                        @change="onchange"
                    v-decorator="[
                    'type',
                    {rules: [{ required: true, message:'请选择类型'}] }]"

                    >
                    <a-select-option value="1">生日特惠</a-select-option>
                    <a-select-option value="2">多间特惠</a-select-option>
                    <a-select-option value="3">限时特惠</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="活动日期" v-if="signal3">
                <a-range-picker
                        format="YYYY-MM-DD"
                        v-decorator="[
                        'date',
                        {
                            rules: [{ required: true, message: '请选择活动开始时间' },{ validator: this.dateCheck }]
                        }
                    ]"
                        :placeholder="['开始时间','结束时间']"
                />
            </a-form-item>
            <a-form-item label="优惠券属性" v-bind="formItemLayout">
                <a-select @change="handleChange"
                        v-decorator="[
                    'property',
                    {rules: [{ required: true, message:'请选择类型'}] }]"

                >
                    <a-select-option value="0" >折扣券</a-select-option>
                    <a-select-option value="1" >满减券</a-select-option>
                </a-select>

            </a-form-item>
            <a-form-item label="券名" v-bind="formItemLayout">
                <a-input
                    placeholder="请填写券名"
                    v-decorator="['name',{ rules: [{ required:true, message:'请输入券名' }] }]"
                    />
            </a-form-item>
            <a-form-item label="优惠简介" v-bind="formItemLayout">
                <a-input
                    type="textarea"
                    :row="4"
                    placeholder="请填写优惠简介"
                    v-decorator="['description',{rules: [{ required: true, message: '请填写优惠简介' }] }]"
                    />
            </a-form-item>
            <a-form-item label="达标金额" v-bind="formItemLayout" v-if="signal1">
                <a-input
                    placeholder="请填写达标金额"
                    v-decorator="['targetMoney',{ rules: [{ required: true, message: '请填写达标金额' },{ validator: this.handletarget }] }]"
                    />
            </a-form-item>
            <a-form-item label="优惠金额" v-bind="formItemLayout" v-if="signal1">
                <a-input
                    placeholder="请填写优惠金额"
                    v-decorator="['discountMoney',{rules: [{ required:true, message:'请填写优惠金额' },{ validator: this.handleless }] }]"
                    />
            </a-form-item>
            <a-form-item label="折扣" v-bind="formItemLayout" v-if="signal2">
                <a-input
                        placeholder="请填写折扣"
                        v-decorator="['discount',{rules: [{ required:true, message:'请填写优惠折扣' },{ validator: this.handlediscount }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
const moment = require('moment')
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
    name: 'addCouponModal',
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
            signal1: false,
            signal2: false,
            signal3: false,//限时特惠
        }
    },
    computed: {
        ...mapGetters([
            'activeCouponHotelId',
            'addCouponVisible',
            'orderMatchCouponList'
        ])
    },
    beforeCreate() {
        // 表单名默认为“form”
        this.form = this.$form.createForm(this, { name: 'addCouponModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible'
        ]),
        ...mapActions([
            // addHotelCoupon：添加酒店策略接口
            'addHotelCoupon'
        ]),
        cancel() {
            this.set_addCouponVisible(false)
            this.reset()
        },
        dateCheck(rule,value,callback){
            var date=this.getDate()
            if(date>value[0].format('YYYY-MM-DD')){
                callback('不能添加'+date+'之前开始的优惠活动')
                return
            }
            callback()
        },
        handletarget(rule,value,callback){
            if(value<0){
                callback('达标金额不得是负数')
            }
            callback()
        },
        handleless(rule,value,callback){
            const total=this.form.getFieldValue('targetMoney')
            if(total>value){
                callback('优惠金额不得超过达标金额')
            }
            callback()
        },
        handlediscount(rule,value,callback){
            if(value>=10||value<=0){
                callback('折扣应在0到1之间')
            }
            callback()
        },
        handleChange(v){
            this.signal1 = Number(v) === 1
            this.signal2 = Number(v) === 0
        },
        handleSubmit(e) {
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        // 这里添加接口参数
                        couponName: this.form.getFieldValue('name'),
                        description: this.form.getFieldValue('description'),
                        couponType: Number(this.form.getFieldValue('type')),
                        targetMoney: this.signal1 ? Number(this.form.getFieldValue('targetMoney')) : -1,
                        discountMoney: this.signal1 ? Number(this.form.getFieldValue('discountMoney')) : -1,
                        discount: this.signal2 ? Number(this.form.getFieldValue('discount')) : -1,
                        hotelId: Number(this.activeCouponHotelId),
                        status: 1,
                        property:Number(this.form.getFieldValue('property')),
                        startTime: this.signal3 ? moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD') : null,
                        endTime: this.signal3 ? moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD') : null,
                    }
                    this.addHotelCoupon(data)
                    this.reset()
                }
            });
        },
        reset(){
            this.form.setFieldsValue({
                'name': '',
                'description':'',
                'type':'',
                'targetMoney':'',
                'discountMoney':'',
                'discount':'',
                'hotelId':'',
                'status':'',
                'property':'',
                'date':'',
            })
            this.signal3=false
        },
        onchange(v){
            this.signal3 = Number(v) === 3
        }
    },
}
</script>
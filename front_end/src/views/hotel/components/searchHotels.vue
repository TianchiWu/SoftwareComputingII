<template>
    <a-modal
            :visible="searchHotel"
            title="按条件搜索酒店"
            okText="提交"
            cancelText="取消"
            @cancel="close"
            @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="评分大于(0-5)" v-bind="formItemLayout">
                <a-input
                        placeholder="请输入评分下区间"
                        v-decorator="['minrate', { rules: [{validator: this.checkRate}] }]"
                />
            </a-form-item>
            <a-form-item label="评分小于(0-5)" v-bind="formItemLayout">
                <a-input
                    placeholder="请输入评分上区间"
                    v-decorator="['maxrate', { rules: [{validator: this.checkRate}] }]"
                    />
            </a-form-item>
            <a-form-item label="评价" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'hotelStar',
                    { rules: [{  message: '请选择酒店星级' }] }]"
                        @change="changeStar"
                >   <a-select-option value="">全部</a-select-option>
                    <a-select-option value="一星级">一星级</a-select-option>
                    <a-select-option value="二星级">二星级</a-select-option>
                    <a-select-option value="三星级">三星级</a-select-option>
                    <a-select-option value="四星级">四星级</a-select-option>
                    <a-select-option value="五星级">五星级</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="地址" v-bind="formItemLayout">
                <a-input
                        placeholder="请输入地址"
                        v-decorator="['address', { rules: [{ message: '请输入地址' }] }]"
                        />
            </a-form-item>
            <a-form-item label="酒店名称" v-bind="formItemLayout">
                <a-input
                        placeholder="酒店名称"
                        v-decorator="['hotelName', { rules: [{ message: '请输入酒店名称' }] }]"
                        />
            </a-form-item>
            <a-form-item label="订单状态" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'orderState',
                    { rules: [{  message: '根据订单状态搜索' }] }]"
                        @change="changeOrder"
                >   <a-select-option value="">全部</a-select-option>
                    <a-select-option value="已退房">预订过</a-select-option>
                    <a-select-option value="无订单">未预订过</a-select-option>
                    <a-select-option value="已入住">正在入住中</a-select-option>
                    <a-select-option value="异常">有异常订单</a-select-option>
                </a-select>
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import {message} from "ant-design-vue";
    export default {
        name: 'searchHotels',
        components: {},

        data() {
            return {
                data:[],
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
        computed: {
            ...mapGetters([
                'searchHotel',
                'userId'
            ])
        },
        async beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'searchHotels' });
            await this.form.setFieldsValue({
                'minrate':Number(0),
                'maxrate':Number(5),
                'hotelStar': "",
                'address': "",
                'hotelName': "",
                'orderState':"",
            })
        },
        mounted() {

         },
        methods: {
            ...mapMutations([
                'set_searchHotel',
                "set_allHotelData",

            ]),
            ...mapActions([
                'SearchHotels'
            ]),
            close() {
                this.form.setFieldsValue({
                    'minrate':Number(0),
                    'maxrate':Number(5),
                    'hotelStar':"",
                    'address':"",
                    'hotelName':"",
                    'orderState':"",
                })
                this.set_searchHotel(false)
            },
            handleSubmit(e) {
                e.preventDefault();

                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            userId:Number(this.userId),
                            minrate: Number(this.form.getFieldValue('minrate')),
                            maxrate:Number(this.form.getFieldValue('maxrate')),
                            hotelStar:this.form.getFieldValue('hotelStar'),
                            address: this.form.getFieldValue('address'),
                            hotelName: this.form.getFieldValue('hotelName'),
                            orderState: this.form.getFieldValue('orderState'),
                        }
                        this.set_allHotelData(data)
                        this.SearchHotels()
                        message.success("搜索成功")
                        this.set_searchHotel(false)
                        this.form.setFieldsValue({
                            'minrate':Number(0),
                            'maxrate':Number(5),
                            'hotelStar':"",
                            'address':"",
                            'hotelName':"",
                            'orderState':"",
                        })
                    }
                });
            },
            checkRate(rule, value, callback) {
                if (0<=value &&value<=5) {
                    callback();
                }
                else if(value==null){
                    callback();
                }
                else {
                    callback(new Error('评分超出范围'));
                }
            },


        }
    }
</script>
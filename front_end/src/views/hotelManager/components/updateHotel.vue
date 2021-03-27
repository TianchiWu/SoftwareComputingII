<template>
    <a-modal
            :visible="updateHotelVisible"
            @cancel="cancel"
    >
        <a-form :form="form" style="margin-top: 30px">
            <a-form-item label="酒店名称" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                <a-input
                        placeholder="请填写酒店名称"
                        v-decorator="['hotelName', { rules: [{ required: true, message: '请输入酒店名称' }] }]"
                        v-if="modify"
                />
                <span v-else>{{ updateHotelInfo.hotelName }}</span>
            </a-form-item>

            <a-form-item label="地址" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                <a-input
                        placeholder="请填写地址"
                        v-decorator="['address', { rules: [{ required: true, message: '请输入地址' }] }]"
                        v-if="modify"
                />
                <span v-else>{{ updateHotelInfo.address}}</span>
            </a-form-item>
            <a-form-item label="星级" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                <a-select
                        v-decorator="[
                    'hotelStar',
                    { rules: [{ required: true, message: '请选择酒店星级' }] }]"
                        v-if="modify"
                        @change="changeStar"
                >
                    <a-select-option value="一星级">一星级</a-select-option>
                    <a-select-option value="二星级">二星级</a-select-option>
                    <a-select-option value="三星级">三星级</a-select-option>
                    <a-select-option value="四星级">四星级</a-select-option>
                    <a-select-option value="五星级">五星级</a-select-option>
                </a-select>
                <span v-else>{{ updateHotelInfo.hotelStar}}</span>
            </a-form-item>
            <a-form-item label="电话号码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                <a-input
                        placeholder="请填写电话号码"
                        v-decorator="['phoneNum', { rules: [{required: true,  message: '请输入电话号码' },{ validator: this.phoneCheck}] }]"
                        v-if="modify"
                />
                <span v-else>{{ updateHotelInfo.phoneNum}}</span>
            </a-form-item>
            <a-form-item label="描述" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                <a-input
                        placeholder="请填写酒店描述"
                        type="textarea"
                        :rows="4"
                        v-decorator="['description', { rules: [{ required: true, message: '请输入酒店描述' }] }]"
                        v-if="modify"
                />
                <span v-else>{{ updateHotelInfo.description}}</span>
            </a-form-item>
            <a-form-item label="详情" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                <a-input
                        placeholder="请填写酒店详情"
                        type="textarea"
                        :rows="10"
                        v-decorator="['information', { rules: [{ required: true, message: '请输入酒店详情' }] }]"
                        v-if="modify"
                />
                <span v-else>{{ updateHotelInfo.information}}</span>
            </a-form-item>



            <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                <a-button type="primary" @click="handleSubmit(e)">
                    保存
                </a-button>
                <a-button type="default" style="margin-left: 30px" @click="cancel">
                    取消
                </a-button>
            </a-form-item>
            <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                <a-button type="primary" @click="modifyInfo">
                    修改信息
                </a-button>
            </a-form-item>

        </a-form>

        <div slot="footer"></div>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: 'updateHotel',
        data() {
            return {
                modify:false,
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

                form: this.$form.createForm(this, { name: 'updateHotel' }),
            }
        },
        computed: {
            ...mapGetters([
                'updateHotelVisible',
                'updateHotelInfo',
            ])
        },

        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_updateHotelVisible',
            ]),
            ...mapActions([
                'UpdateHotelInfo',
            ]),
            cancel() {
                this.set_updateHotelVisible(false)
                this.modify=false
            },
            phoneCheck(rule,value,callbackFn){
                const validatePhoneReg=/^1\d{10}$/
                if(value!=null&&!validatePhoneReg.test(value)){
                    callbackFn('手机号码格式有误')
                    return
                }
                callbackFn();
            },
            modifyInfo() {
                setTimeout(() => {
                    this.form.setFieldsValue({
                        'hotelName': this.updateHotelInfo.hotelName,
                        'description':this.updateHotelInfo.description,
                        'address':this.updateHotelInfo.address,
                        'hotelStar':this.updateHotelInfo.hotelStar,
                        'phoneNum':this.updateHotelInfo.phoneNum,
                        'information':this.updateHotelInfo.information,
                    })
                }, 0)
                this.modify = true
            },
            handleSubmit(e) {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            hotelName: this.form.getFieldValue('hotelName'),
                            description: this.form.getFieldValue('description'),
                            address: this.form.getFieldValue('address'),
                            phoneNum: this.form.getFieldValue('phoneNum'),
                            hotelStar: this.form.getFieldValue('hotelStar'),
                            information: this.form.getFieldValue('information')
                        }
                        this.UpdateHotelInfo(data).then(()=>{

                            this.modify = false
                        })
                    }
                });
            },

        }
    }
</script>
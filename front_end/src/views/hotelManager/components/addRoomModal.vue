<template>
    <a-modal
        :visible="addRoomModalVisible"
        title="录入客房"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="房型" v-bind="formItemLayout">
                <a-select
                    v-decorator="[
                    'roomType', 
                    { rules: [{ required: true, message: '请选择房型' }] }]"
                >
                  <a-select-option value="大床房">大床房</a-select-option>
                  <a-select-option value="双床房">双床房</a-select-option>
                  <a-select-option value="家庭房">家庭房</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="房间数量" v-bind="formItemLayout">
                <a-input
                    placeholder="请填写房间数量"
                    v-decorator="['roomNum', { rules: [{ required: true, message: '请输入房间数量' }, { validator: this.handleNum }] }]"
                />
            </a-form-item>
            <a-form-item label="原始价格" v-bind="formItemLayout">
            <a-input
                    placeholder="请填写原始价格"
                    v-decorator="['price', { rules: [{ required: true, message: '请输入原始价格' },{ validator: this.handle }] }]"
            />
            </a-form-item>
            <a-form-item label="早餐" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写早餐信息"
                        v-decorator="['breakfast', { rules: [{ required: true, message: '请输入早餐' }] }]"
                />
            </a-form-item>
            <a-form-item label="设施" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写设施信息"
                        v-decorator="['facility', { rules: [{ required: true, message: '请输入设施' }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
    name: 'addRoomModal',
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
        }
    },
    computed: {
        ...mapGetters([
            'addRoomModalVisible',
            'activeRoomHotelId'
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'addRoomModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addRoomModalVisible',
            'set_addRoomParams'
        ]),
        ...mapActions([
            'addRoom'
        ]),
        cancel() {
            this.set_addRoomModalVisible(false)
            this.form.setFieldsValue({
                'roomType': '',
                'price': '',
                'total':'',
                'roomNum':'',
                'breakfast':'',
                'facility':'',
            })
        },
        handleNum(rule, value, callback) {
            if (value>100) {
                callback(new Error('一次最多添加100间房'))
            }
            else if(value<=0){
                callback(new Error('房间数量必须为正数'))
            }
            callback()
        },
        handle(rule,value,callback){
            if(value<=0){
                callback(new Error('价格必须为正数'))
            }
            callback()
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        roomType: this.form.getFieldValue('roomType'),
                        price: Number(this.form.getFieldValue('price')),
                        total: Number(this.form.getFieldValue('roomNum')),
                        curNum: Number(this.form.getFieldValue('roomNum')),
                        breakfast: this.form.getFieldValue('breakfast'),
                        facility: this.form.getFieldValue('facility'),
                        hotelId: this.activeRoomHotelId,
                    }
                    this.set_addRoomParams(data)
                    this.addRoom()
                    this.form.setFieldsValue({
                        'roomType': '',
                        'price': '',
                        'total':'',
                        'roomNum':'',
                        'breakfast':'',
                        'facility':'',
                    })
                }
            });
        },
    }
}
</script>
<template>
    <a-modal
            :visible="addText"
            title="添加评论"
            okText="提交"
            cancelText="取消"
            @cancel="close"
            @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
           <a-form-item label="评分" v-bind="formItemLayout">
            <a-input
                    placeholder="请打分（0-5）"
                    v-decorator="['rate', { rules: [{ required: true, message: '请打分' },{validator: this.checkRate}] }]"
            />
            </a-form-item>
        <a-form-item label="评价" v-bind="formItemLayout">
        <a-input
                type="textarea"
                :rows="4"
                placeholder="请填写评价"
                v-decorator="['text', { rules: [{ required: true, message: '请填写评价' }] }]"
        />
    </a-form-item>
        </a-form>
            </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: 'addText',
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
                'addText',
                'email',
                'currentHotelId',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'addText' });
        },
        async mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addText',

            ]),
            ...mapActions([
                'addComment'
            ]),
            close() {
                this.form.setFieldsValue({
                    'rate': '',
                    'text':'',
                })
                this.set_addText(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            userEmail: this.email,
                            hotelId: this.currentHotelId,
                            rate: Number(this.form.getFieldValue('rate')),
                            text: this.form.getFieldValue('text')
                        }
                        this.addComment(data)
                        this.set_addText(false)
                        this.form.setFieldsValue({
                            'rate': '',
                            'text':'',
                        })
                    }
                });
            },
            checkRate(rule, value, callback) {
                if (0<=value &&value<=5) {
                    callback();
                } else {
                    callback(new Error('评分超出范围'));
                }
                callback()
            },

        }
    }
</script>
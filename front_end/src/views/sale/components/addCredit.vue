<template>
    <a-modal
            :visible="addCreditVisible"
            title="信用充值"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-Form :form="form">
            <a-form-item v-bind="formItemLayout" label="信用值">
                <a-input
                        v-decorator="[
                        'credit',
                        { rules: [{required: true, message: '请输入要增加的信用值', },{ validator: this.handlecredit }] }
                    ]"
                />
            </a-form-item>
        </a-Form>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: 'addCredit',
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
               'addCreditVisible',
                'activeUserId',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'addCredit' });
            this.form.setFieldsValue({
                'credit':''
            })
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addCreditVisible'
            ]),
            ...mapActions([
                'addCredit',
            ]),

            cancel(){
                this.set_addCreditVisible(false)
                this.form.setFieldsValue({
                    'credit':''
                })
            },
            handlecredit (rule, value, callback) {
                const credit = this.form.getFieldValue('credit')
                if (value <=0) {
                    callback(new Error('充值的信用值必须大于0'))
                }
                callback()
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data={
                            id: this.activeUserId,
                            credit:Number(this.form.getFieldValue("credit"))
                        }
                        this.addCredit(data);
                    }
                    this.form.setFieldsValue({
                        'credit':''
                    })
                });
            },
        }

    }
</script>
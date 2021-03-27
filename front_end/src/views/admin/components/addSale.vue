<template>
    <a-modal
            :visible="addSaleVisible"
            title="添加网站营销人员"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-Form :form="form">
            <a-form-item v-bind="formItemLayout" label="网站营销人员邮箱">
                <a-input
                        v-decorator="[
                        'email',
                        { rules: [{required: true, message: '请输入网站营销人员邮箱', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="密码">
                <a-input
                        v-decorator="[
                        'password',
                        { rules: [{required: true, message: '请输入密码', }] }
                    ]"
                />
            </a-form-item >
            <a-form-item v-bind="formItemLayout" label="确认密码">
                <a-input
                        size="large"
                        type="password"
                        autocomplete="false"
                        placeholder="密码"
                        v-decorator="[
                        'checkpassword',
                        { rules: [{required: true, message: '请输入密码'},{ validator: this.handlePasswordCheck }] }
                    ]"
                />
            </a-form-item >
        </a-Form>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: 'addSale',
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
                'addSaleVisible',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'addSale' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addSaleVisible',
                'set_addManagerParams',
            ]),
            ...mapActions([
                'addSale',
            ]),
            cancel() {
                this.set_addSaleVisible(false)
                this.form.setFieldsValue({
                    'email': '',
                    'password': '',
                })
            },
            handlePasswordCheck (rule, value, callback) {
                const password = this.form.getFieldValue('password')
                if (value === undefined) {
                    callback(new Error('请输入密码'))
                }
                if (value && password && value.trim() !== password.trim()) {
                    callback(new Error('两次密码不一致'))
                }
                callback()
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            email: this.form.getFieldValue('email'),
                            password: this.form.getFieldValue('password')
                        }
                        this.set_addManagerParams(data)
                        this.addSale()
                        this.form.setFieldsValue({
                            'email': '',
                            'password': '',
                        })
                    }
                });
            },
        }
    }
</script>
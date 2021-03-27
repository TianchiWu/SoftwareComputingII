<template>
    <a-modal
            :visible="addClientVisible"
            title="添加用户"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-Form :form="form">
            <a-form-item v-bind="formItemLayout" label="邮箱">
                <a-input
                        size="large"
                        placeholder="邮箱"
                        v-decorator="[
                        'email',
                        { rules: [{required: true, message: '请输入邮箱'},{ validator: this.checkEmail }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="密码">
                <a-input
                        size="large"
                        type="password"
                        placeholder="密码"
                        v-decorator="[
                        'password',
                        { rules: [{required: true, message: '请输入密码'},{ validator: this.handlePassword }], validateTrigger: 'blur'}
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
            <a-form-item v-bind="formItemLayout" label="用户名">
                <a-input
                        size="large"
                        placeholder="用户名"
                        v-decorator="[
                        'username',
                        { rules: [{required: true, message: '请输入用户名', }] }
                    ]"
                />
            </a-form-item >
            <a-form-item v-bind="formItemLayout" label="电话号码">
                <a-input
                        size="large"
                        placeholder="手机号"
                        v-decorator="[
                        'phonenumber',
                        { rules: [{required: true, message: '请输入电话号码'},{ validator: this.phoneCheck}] }
                    ]"
                />
            </a-form-item >
            <a-form-item v-bind="formItemLayout" label="生日">
                <a-input
                        size="large"
                        placeholder="生日 YYYY-MM-DD"
                        v-decorator="[
                        'birthday',
                        { rules: [{required: true,message: '请输入生日', }] }
                    ]"
                />
            </a-form-item >
        </a-Form>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: 'addClient',
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
                'addClientVisible',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'addClient' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addClientVisible',
                'set_addClientParams',
            ]),
            ...mapActions([
                'addClient',
            ]),
            cancel() {
                this.set_addClientVisible(false)
                this.form.setFieldsValue({
                    'email': '',
                    'password': '',
                    'checkpassword':'',
                    'username': '',
                    'phonenumber': '',
                    'credit': '',
                    'birthday': '',
                    'membertype': ''
                })
            },
            checkEmail(rule, value, callback) {
                const re = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
                if (re.test(value)) {
                    callback();
                } else {
                    callback(new Error('邮箱格式不符'));
                }
                callback()
            },
            phoneCheck(rule,value,callbackFn){
                const validatePhoneReg=/^1\d{10}$/
                if(value!=null&&!validatePhoneReg.test(value)){
                    callbackFn('手机号码格式有误')
                    return
                }
                callbackFn();
            },
            handlePassword(rule, value, callback) {
                if (value.length < 6) {
                    callback(new Error('密码长度至少6位'))
                }
                callback()
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
            changeType(v){

            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            email: this.form.getFieldValue('email'),
                            password: this.form.getFieldValue('password'),
                            userName:this.form.getFieldValue('username'),
                            phoneNumber:this.form.getFieldValue('phonenumber'),
                            credit:100,
                            birthday:this.form.getFieldValue('birthday'),
                        }
                        this.set_addClientParams(data)
                        this.addClient()
                        this.form.setFieldsValue({
                            'email': '',
                            'password': '',
                            'checkpassword':'',
                            'username': '',
                            'phonenumber': '',
                            'birthday': '',
                        })
                    }
                });
            },
        }
    }
</script>
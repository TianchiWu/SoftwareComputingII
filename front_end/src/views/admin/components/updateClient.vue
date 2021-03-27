<template>
    <a-modal
            :visible="updateClientVisible"
            @cancel="cancel"
    >
        <a-form :form="form" style="margin-top: 30px">

            <a-form-item label="用户名" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1  }">
                <a-input
                        placeholder="请填写用户名"
                        v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                        v-if="modify"
                />
                <span v-else>{{ activeUserInfo.userName }}</span>
            </a-form-item>
            <a-form-item label="邮箱" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1 }">
                <a-input
                        placeholder="请填写邮箱"
                        v-decorator="['email', { rules: [{ required: true, message: '请输入邮箱' }] }]"
                        v-if="modify"
                />
                <span v-else>{{ activeUserInfo.email }}</span>
            </a-form-item>
            <a-form-item label="密码" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1 }">
                <a-input
                        type="password"
                        autocomplete="false"
                        placeholder="请填写密码"
                        v-decorator="['password', { rules: [{ required: true, message: '请输入密码' }] }]"
                        v-if="modify"
                />
                <span v-else>{{ activeUserInfo.password}}</span>
            </a-form-item>
            <a-form-item  label="确认密码"  v-if="modify" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1 }">
                <a-input
                        type="password"
                        autocomplete="false"
                        placeholder="密码"
                        v-decorator="[
                        'checkpassword',
                        { rules: [{required: true, message: '请输入密码'},{ validator: this.handlePasswordCheck }] }
                    ]"

                />
            </a-form-item >
            <a-form-item label="手机号" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1 }">
                <a-input
                        placeholder="请填写手机号"
                        v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                        v-if="modify"
                />
                <span v-else>{{ activeUserInfo.phoneNumber}}</span>
            </a-form-item>
            <a-form-item label="信用值" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1 }">
                <span>{{ activeUserInfo.credit }}</span>
            </a-form-item>
            <a-form-item label="生日" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1 }">
                <a-input
                        placeholder="请填写生日"
                        v-decorator="['birthday', { rules: [{ required: true, message: '请输入生日' }] }]"
                        v-if="modify"
                />
                <span v-else>{{ activeUserInfo.birthday }}</span>
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
        name: 'updateClient',
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

                form: this.$form.createForm(this, { name: 'updateClient' }),
            }
        },
        computed: {
            ...mapGetters([
                'updateClientVisible',
                'activeUserInfo',
            ])
        },

         mounted() {

        },
        methods: {
            ...mapMutations([
                'set_updateClientVisible',
            ]),
            ...mapActions([
                'updateAllUserInfo',
            ]),
            cancel() {
                this.set_updateClientVisible(false)
                this.modify=false
            },
            changeType(v){

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
            modifyInfo() {
                setTimeout(() => {
                    this.form.setFieldsValue({
                        'email': this.activeUserInfo.email,
                        'userName': this.activeUserInfo.userName,
                        'phoneNumber': this.activeUserInfo.phoneNumber,
                        'password': this.activeUserInfo.password,
                        'birthday':this.activeUserInfo.birthday,
                        'usertype': this.activeUserInfo.usertype,
                    })
                }, 0)
                this.modify = true
            },
            handleSubmit(e) {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            email: this.form.getFieldValue('email'),
                            password: this.form.getFieldValue('password'),
                            userName: this.form.getFieldValue('userName'),
                            phoneNumber: this.form.getFieldValue('phoneNumber'),
                            usertype: this.form.getFieldValue('usertype'),
                            birthday: this.form.getFieldValue('birthday'),
                        }

                        this.updateAllUserInfo(data).then(()=>{

                            this.modify = false
                        })
                    }
                });
            },

        }
    }
</script>
<template>
    <a-modal
            :visible="setManagerVisible"
            title="设置酒店管理员"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-Form :form="form">
            <a-form-item v-bind="formItemLayout" label="管理员id">
                <a-input
                        v-decorator="[
                        'id',
                        { rules: [{required: true, message: '请输入要设置的管理员id', },{ validator: this.handleid }] }
                    ]"
                />
            </a-form-item>
        </a-Form>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: 'setManager',
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
                'setManagerVisible',
                'setManagerId',
                'activeHotelId',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'setManager' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                "set_setManagerVisible",
                "set_setManagerId",
            ]),
            ...mapActions([
                "setManager"
            ]),
            handleid (rule, value, callback) {
                const { state } = this
                if (value<0) {
                    callback(new Error('请输入有效id'))
                }
                callback()
            },
            cancel(){
                this.set_setManagerVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data={
                            id:this.form.getFieldValue("id")
                        }
                        this.set_setManagerId(data.id)
                        this.setManager(this.activeHotelId)
                        this.form.setFieldsValue({
                            'id':'',
                        })
                    }
                });
            },
        }

    }
</script>
<template>
    <a-modal
            :visible="addEnterpriseVisible"
            title="添加单位"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-Form :form="form">
            <a-form-item v-bind="formItemLayout" label="单位名称">
                <a-input
                        v-decorator="[
                        'name',
                        { rules: [{required: true, message: '请输入合作单位名称', }] }
                    ]"
                />
            </a-form-item>
        </a-Form>

    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "addEnterprise",
        data(){
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
                'addEnterpriseVisible',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'addEnterprise' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addEnterpriseVisible',
                'set_addEnterpriseParams'
            ]),
            ...mapActions([
                'addEnterprise'
            ]),
            cancel() {
                this.set_addEnterpriseVisible(false)
                this.form.setFieldsValue({
                    'name': '',
                })
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            name: this.form.getFieldValue('name'),
                        }
                        this.set_addEnterpriseParams(data)
                        this.addEnterprise()
                        this.form.setFieldsValue({
                            'name': '',
                        })
                    }
                });
            },
        }
    }
</script>

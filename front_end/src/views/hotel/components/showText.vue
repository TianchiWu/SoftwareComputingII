<template>
    <a-modal
            :visible="showText"
            title="评论内容"
            :footer="null"
            @cancel="close"
    >
        <a-form :form="form" style="margin-top: 30px">
        <a-form-item label="评分"  v-bind="formItemLayout">
            <a-input
                    placeholder="请填写评分"
                    v-decorator="['rate', { rules: [{ required: true, message: '请输入评分' },{validator: this.checkRate}] }]"
                    v-if="modify"
            />
            <span v-else>{{ activeComment.rate}}</span>
        </a-form-item>
        <a-form-item label="内容" v-bind="formItemLayout">
            <a-input
                    type="textarea"
                    placeholder="请填写内容"
                    :rows="4"
                    v-decorator="['text', { rules: [{ required: true, message: '请输入内容' }] }]"
                    v-if="modify"
            />
            <span v-else>{{activeComment.text}}</span>
        </a-form-item>
        <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
            <a-button type="primary" @click="handleSubmit(e)">
                保存
            </a-button>
            <a-button type="default" style="margin-left: 30px" @click="cancel">
                取消
            </a-button>
        </a-form-item>
        <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else-if="activeComment.userEmail == email">
            <a-button type="primary" @click="modifyInfo">
                修改信息
            </a-button>
        </a-form-item>
        </a-form>
        <a-button type="primary" style="margin-left: 400px" @click="close" >确定</a-button>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: 'showText',
        data() {
            return {
                data:[],
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
            }
        },
        computed: {
            ...mapGetters([
                'showText',
                'activeComment',
                'email',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'showText' });
        },
        async mounted() {

        },
        methods: {
            ...mapMutations([
                'set_showText',

            ]),
            ...mapActions([
                'updateComment',
            ]),
            close() {
                this.set_showText(false)
                this.modify=false
            },
            cancel() {
                this.modify=false
            },
            modifyInfo() {
                setTimeout(() => {
                    this.form.setFieldsValue({
                        'rate': this.activeComment.rate,
                        'text': this.activeComment.text,
                    })
                }, 0)
                this.modify = true
            },
            handleSubmit(e) {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            id:this.activeComment.id,
                            rate: this.form.getFieldValue('rate'),
                            text: this.form.getFieldValue('text'),

                        }

                        this.updateComment(data).then(()=>{
                            this.modify = false
                            this.set_showText(false)
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
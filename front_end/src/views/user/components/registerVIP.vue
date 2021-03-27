<template>
    <a-modal
        :visible="registerVisible"
        :footer="null"
        title="会员注册"
        @cancel="close"
    >
        <div class="info-wrapper">
            <a-tabs>
                <a-tab-pane tab="普通会员" key="1">
                    <a-form :form="form" style="margin-top: 30px">
                        <a-form-item label="注册规则" :label-col="{ span: 4 }" :wrapper-col="{ span: 12 , offset: 1 }">
                            信用积分超过120可以注册普通会员
                        </a-form-item>
                    </a-form>
                    <a-form :form="form">
                        <a-form-item label="您的当前积分" :label-col="{ span: 5}" :wrapper-col="{ span: 8, offset: 1}">
                            <span>{{userInfo.credit}}</span>
                        </a-form-item>
                    </a-form>
                    <a-button type="primary" style="margin-left: 400px" @click="handleNormalSubmit">注册</a-button>
                </a-tab-pane>

                    <a-tab-pane tab="企业会员" key="2">
                        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
                            <a-form-item label="企业名称" :label-col="{ span: 4  }" :wrapper-col="{ span: 12 , offset: 1 }">


                            <a-input
                                    style="margin-bottom: 20px"
                                    placeholder="请填写与我们合作的企业名称"
                                    v-decorator="['enterprisename', { rules: [{ required: true, message: '请输入企业名称' }] }]"

                            />
                            </a-form-item>
                            <a-button type="primary" style="margin-left: 400px" @click="handleEnterpriseSubmit">注册</a-button>
                        </a-form>
                    </a-tab-pane>
            </a-tabs>
        </div>
    </a-modal>
    
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import {registerAPI} from "../../../api/user";
    export default {
        name: "registerVIP",
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
        computed:{
            ...mapGetters([
                'registerVisible',
                'userInfo',
            ])
        },
        beforeCreate() {
            this.form=this.$form.createForm(this,{name: 'registerVIP'})
        },
        methods:{
            ...mapMutations([
                'set_registerVisible',
            ]),
            ...mapActions([
                'registerNormal',
                'registerEnterprise',
                'updateEnterprise'
            ]),
            close(){
                this.set_registerVisible(false);
            },
            handleNormalSubmit(e){
                this.registerNormal()
            },
            handleEnterpriseSubmit(e){
                e.preventDefault();

                this.form.validateFields((err,values)=>{
                    if(!err){
                        const data={
                            enterprisename:this.form.getFieldValue('enterprisename')
                        }

                        this.updateEnterprise(data.enterprisename)
                    }
                })
            }
        }
    }

</script>

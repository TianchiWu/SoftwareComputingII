<template>
    <a-modal
            :visible="deleteRoomNumVisible"
            title="请输入删除的房间的数量"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >   <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
        <a-form-item label="数量" v-bind="formItemLayout">
            <a-input
                    placeholder="请填写数量"
                    v-decorator="['Num', { rules: [{ required: true, message: '请输入数量' }] }]"
            />
        </a-form-item>
    </a-form>
    </a-modal>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";

    export default {
        name: "deleteRoomsNum",
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
        components: {
        },
        computed: {
            ...mapGetters([
                'deleteRoomNumVisible',
                'activeRoomCurNum'
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'deleteRoomsNum' });
        },
        async mounted() {
        },
        methods: {
            ...mapMutations([
                'set_deleteRoomNumVisible',
                'set_deleteNum',
            ]),
            ...mapActions([
                'updateRoomNum',
                'DeleteRooms'
            ]),
            cancel(){
                this.set_deleteRoomNumVisible(false)
                this.form.setFieldsValue({
                    'Num':''
                })
            },
            handleNum(rule,value,callback){
                const validateNum=/^d?$/
                if(value!=null&&!validateNum.test(value)&&!value<=0){
                    callback('只允许删除正整数数量的房间')
                    return
                }
                callback();
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data={
                            Num: this.form.getFieldValue('Num')
                        }
                        this.set_deleteNum(data.Num);
                        this.DeleteRooms();
                        this.updateRoomNum();

                        this.set_deleteRoomNumVisible(false)
                        this.form.setFieldsValue({
                            'Num':''
                        })
                    }
                });
            },

        }
    }
</script>

<style scoped>

</style>
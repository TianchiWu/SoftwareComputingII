<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane tab="我的信息" key="1">
                <a-form :form="form" style="margin-top: 30px">
                    
                    <a-form-item label="用户名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                            placeholder="请填写用户名"
                            v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                            v-if="modify"
                        />
                        <span v-else>{{ userInfo.userName }}</span>
                    </a-form-item>
                    <a-form-item label="邮箱" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.email }}</span>
                    </a-form-item>
                    
                    <a-form-item label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                            placeholder="请填写手机号"
                            v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' },{ validator: this.phoneCheck}] }]"
                            v-if="modify"
                        />
                        <span v-else>{{ userInfo.phoneNumber}}</span>
                    </a-form-item>
                    <a-form-item label="信用值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.credit }}</span>
                    </a-form-item>
                    <a-form-item label="生日" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.birthday }}</span>
                    </a-form-item>
                    <a-form-item label="会员类型" :label-col="{ span: 3 }" :wrapper-col="{ span:8, offset: 1}">
                        <span v-if="userInfo.membertype == null">
                            <a-button type="primary" size="small" @click="showRegister">注册会员</a-button>
                        </span>
                        <span v-else>
                            {{userInfo.membertype}}
                        <a-popconfirm
                                title="你确定退出会员吗？"
                                @confirm="confirmCancelCredit"
                                @cancel="cancelCancelCredit"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">退出会员</a-button>
                        </a-popconfirm>


                        </span>
                    </a-form-item>
                    <a-form-item v-if="userInfo.membertype ==='普通会员' " label="会员等级" :label-col="{ span: 3 }" :wrapper-col="{ span:8, offset: 1}">
                        <span>{{userInfo.memberlevel}}</span>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                        <a-button type="primary" @click="saveModify">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                            取消
                        </a-button>
                    </a-form-item>
                     <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                        <a-button type="primary" @click="modifyInfo">
                            修改信息
                        </a-button>
                    </a-form-item>
                </a-form>
            </a-tab-pane>
            <a-tab-pane tab="我的订单" key="2">
                <a-table
                    :columns="columns1"
                    :dataSource="userOrderList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <a-tag slot="orderState" color="blue" slot-scope="text">
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showOrder(record.id)">查看</a-button>
                        <a-divider type="vertical" v-if="record.orderState === '已预订'"></a-divider>
                        <a-popconfirm
                            title="你确定撤销该笔订单吗？"
                            @confirm="confirmCancelOrder(record)"
                            @cancel="cancelCancelOrder"
                            okText="确定"
                            cancelText="取消"
                            v-if="record.orderState === '已预订'"
                        >
                            <a-button type="danger" size="small">撤销</a-button>
                        </a-popconfirm>
                        
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="信用变更记录" key="3">
                <a-table
                    :columns="columns2"
                    :dataSource="userCreditList"
                    bordered>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="修改密码" key="4">
                <a-form :form="form" style="margin-top: 30px">
                    <a-form-item label="原密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="modify_0">
                        <a-input
                                size="large"
                                type="password"
                                placeholder="请输入原密码"
                                v-decorator="[
                'prepassword',
                {rules: [{ required: true, message: '请输入原密码' }, { validator: this.handlePasswordCheck }], validateTrigger: 'blur'}]">
                            <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
                        </a-input>

                    </a-form-item>

                        <a-form-item label="新密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="modify_0">
                            <a-input
                                    size="large"
                                    type="password"
                                    placeholder="请输入新密码"
                                    v-decorator="[
                'password',
                {rules: [{ required: true, message: '请输入新密码' }, { validator: this.handlePassword }], validateTrigger: 'blur'}]">
                                <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
                            </a-input>

                        </a-form-item>
                    <a-form-item  label="新密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="modify_0" >
                        <a-input
                                size="large"
                                type="password"
                                placeholder="确认密码"
                                v-decorator="[
                'passwordconfirm',
                {rules: [{ required: true, message: '请输入密码' }, { validator: this.handlePasswordconfirm }], validateTrigger: 'blur'}]">
                            <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
                        </a-input>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify_0">
                        <a-button type="primary" @click="savePassword">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelPassword">
                            取消
                        </a-button>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                        <a-button type="primary" @click="passwordInfo">
                            修改信息
                        </a-button>
                    </a-form-item>
                </a-form>
            </a-tab-pane>
        </a-tabs>
        <ShowOrder></ShowOrder>
        <registerVIP></registerVIP>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
const moment = require('moment')
import ShowOrder from './components/showOrder.vue'
import registerVIP from "./components/registerVIP";
const columns1 = [
    {  
        title: '订单号',
        sorter:(a,b)=>a.id-b.id,
        dataIndex: 'id',
    },
    {  
        title: '酒店名',
        dataIndex: 'hotelName',
    },
    {
        title: '房型',
        dataIndex: 'roomType',
        filters: [{ text: '大床房', value: 'BigBed' }, { text: '双床房', value: 'DoubleBed' }, { text: '家庭房', value: 'Family' },],
        onFilter: (value, record) => record.roomType.includes(value),
        scopedSlots: { customRender: 'roomType' }
    },
    {
        title: '入住时间',
        dataIndex: 'checkInDate',
        sorter:(a,b)=> moment(a.checkInDate).diff(moment(b.checkInDate)),
        scopedSlots: { customRender: 'checkInDate' }
    },
    {
        title: '离店时间',
        dataIndex: 'checkOutDate',
        sorter:(a,b)=> moment(a.checkOutDate).diff(moment(b.checkOutDate)),
        scopedSlots: { customRender: 'checkOutDate' }
    },
    {
        title: '入住人数',
        sorter:(a,b)=>a.peopleNum-b.peopleNum,
        dataIndex: 'peopleNum',
    },
    {
        title: '房价',
        sorter:(a,b)=>a.price-b.price,
        dataIndex: 'price',
    },
    {
        title: '状态',
        filters: [{ text: '已预订', value: '已预订' }, { text: '已撤销', value: '已撤销' }, { text: '已入住', value: '已入住' }],
        onFilter: (value, record) => record.orderState.includes(value),
        dataIndex: 'orderState',
        scopedSlots: { customRender: 'orderState' }
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
    
  ];

const columns2=[
    {
        title: '时间',
        sorter:(a,b)=> moment(a.curDate).diff(moment(b.curDate)),
        dataIndex: 'curDate'
    },
    {
        title: '订单号',
        dataIndex: 'orderId'
    },
    {
        title: '操作',
        filters: [{ text: '完成订单', value: '完成订单' },{ text: '信用充值', value: '信用充值' },{ text: '撤销订单', value: '撤销订单' },{ text: '网站营销人员撤销异常订单', value: '网站营销人员撤销异常订单' },{ text: '酒店管理员补执行订单', value: '酒店管理员补执行订单' },{ text: '入住异常', value: '入住异常' },{ text: '退房异常', value: '退房异常' }],
        onFilter: (value, record) => record.action.includes(value),
        dataIndex: 'action',
        scopedSlots: { customRender: 'action' }
    },
    {
        title: '变更前信用',
        dataIndex: 'creditBefore'
    },
    {
        title: '变更后信用',
        dataIndex: 'creditAfter'
    }
];
export default {
    name: 'info',
    data(){
        return {
            modify: false,
            modify_0: false,
            formLayout: 'horizontal',
            pagination: {},
            columns1,
            columns2,
            data: [],
            form: this.$form.createForm(this, { name: 'coordinated' }),
        }
    },
    components: {
        ShowOrder,
        registerVIP
    },
    computed: {
        ...mapGetters([
            'userId',
            'userInfo',
            'userOrderList',
            'orderVisible',
            'activeOrderId',
            'activeOrderData',
            'userCreditList',
        ])
    },
    async mounted() {
        await this.getUserInfo()
        await this.getUserOrders(this.userId)
        await this.getUserInfo()
        await this.getCreditList(this.userId)
    },
    methods: {
        ...mapMutations([
            'set_orderVisible',
            'set_activeOrderId',
            'set_registerVisible'
        ]),
        ...mapActions([
            'getUserInfo',
            'getUserOrders',
            'updateUserInfo',
            'updatePassword',
            'cancelOrder',
            'cancelCredit',
            'getUserOrder',
            'getCreditList'
        ]),
        showOrder(orderId){
            this.set_activeOrderId(orderId)
            this.set_orderVisible(true)
            this.getUserOrder()
        },
        //handeler
        handlePassword(rule, value, callback) {
            const password=this.userInfo.password
            if (value.length < 6) {
                callback(new Error('密码长度至少6位'))
            }
            if(value===password){
                callback(new Error('不能与原密码相同'))
            }
            callback()
        },
        handlePasswordCheck (rule, value, callback) {
            const password = this.userInfo.password
            console.log(password)
            if (value === undefined) {
                callback(new Error('请输入密码'))
            }
            if (value && password && value.trim() !== password.trim()) {
                callback(new Error('与原密码不一致'))
            }
            callback()
        },
        handlePasswordconfirm (rule, value, callback) {
            const password = this.form.getFieldValue('password')
            console.log(password)
            if (value === undefined) {
                callback(new Error('请输入密码'))
            }
            if (value && password && value.trim() !== password.trim()) {
                callback(new Error('两次密码不一致'))
            }
            callback()
        },
        saveModify() {
            this.form.validateFields((err, values) => {
                if (!err) {
                    const data = {
                        userName: this.form.getFieldValue('userName'),
                        phoneNumber: this.form.getFieldValue('phoneNumber'),
                    }
                    this.updateUserInfo(data).then(()=>{
                        this.modify = false
                    })
                }
            });
        },
        phoneCheck(rule,value,callbackFn){
            const validatePhoneReg=/^1\d{10}$/
            if(value!=null&&!validatePhoneReg.test(value)){
                callbackFn('手机号码格式有误')
                return
            }
            callbackFn();
        },
        savePassword() {
            this.form.validateFields((err, values) => {
                if (!err) {
                    const data = {
                        password: this.form.getFieldValue('password'),
                    }

                    this.updatePassword(data).then(() => {
                        this.modify_0 = false
                    })
                }
            });
        },
        modifyInfo() {
            setTimeout(() => {
                this.form.setFieldsValue({
                    'userName': this.userInfo.userName,
                    'phoneNumber': this.userInfo.phoneNumber,
                })
            }, 0)
            this.modify = true
        },
        passwordInfo(){
            this.modify_0=true
        },
        cancelModify() {
            this.modify = false
        },
        cancelPassword(){
            this.modify_0= false;
        },
        async confirmCancelOrder(record){
            await this.cancelOrder(record.id)
            await this.getUserOrders(this.userId)
            await this.getUserInfo();
            await this.getCreditList(this.userId);
        },
        cancelCancelOrder() {

        },
        confirmCancelCredit(){
            this.cancelCredit(this.userId)
        },
        cancelCancelCredit(){

        },
        showRegister(){
            this.set_registerVisible(true)
        }
    }
}
</script>
<style scoped lang="less">
    .info-wrapper {
        padding: 50px;
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>
<template>
    <a-modal
        :visible="orderVisible"
        :footer="null"
        @cancel="close"
    >
        <div class="info-wrapper">
            <a-tabs>
                <a-tab-pane tab="详细信息" >
                    <a-form :form="form">
                        <a-form-item label="订单号" :label-col="{ span: 4 }" :wrapper-col="{ span: 5 , offset: 1 }">
                            <span >{{ activeOrderData.id}}</span>
                        </a-form-item>

                    </a-form>
                    <a-form :form="form">
                        <a-form-item label="酒店名" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1  }">
                            <span >{{ activeOrderData.hotelName}}</span>
                        </a-form-item>

                    </a-form>
                    <a-form :form="form">
                        <a-form-item label="入住时间" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1  }">
                            <span >{{ activeOrderData.checkInDate}}</span>
                        </a-form-item>

                    </a-form>
                    <a-form :form="form">
                        <a-form-item label="离店时间" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1  }">
                            <span >{{ activeOrderData.checkOutDate}}</span>
                        </a-form-item>
                    </a-form>
                    <a-form :form="form">
                        <a-form-item label="房间类型" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <span>{{activeOrderData.roomType}}</span>
                        </a-form-item>

                    </a-form>
                    <a-form :form="form">
                        <a-form-item label="房间数" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1  }">
                            <span >{{ activeOrderData.roomNum}}</span>
                        </a-form-item>

                    </a-form>
                    <a-form :form="form">
                        <a-form-item label="入住人数" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1  }">
                            <span >{{ activeOrderData.peopleNum}}</span>
                        </a-form-item>

                    </a-form>
                    <a-form :form="form">
                        <a-form-item label="有无儿童" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1  }">
                            <span v-if="activeOrderData.haveChild == 0">无</span>
                            <span v-if="activeOrderData.haveChild== 1">有</span>
                        </a-form-item>

                    </a-form>
                    <a-form :form="form">
                        <a-form-item label="创建日期" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1  }">
                            <span >{{ activeOrderData.createDate}}</span>
                        </a-form-item>

                    </a-form>
                    <a-form :form="form">
                        <a-form-item label="总价" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1  }">
                            <span >{{ activeOrderData.price}}</span>
                        </a-form-item>

                    </a-form>
                    <a-form :form="form">
                        <a-form-item label="预订者" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1  }">
                            <span >{{ activeOrderData.clientName}}</span>
                        </a-form-item>

                    </a-form>
                    <a-form :form="form">
                        <a-form-item label="联系电话" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1  }">
                            <span >{{ activeOrderData.phoneNumber}}</span>
                        </a-form-item>

                    </a-form>
                    <a-form :form="form">
                        <a-form-item label="订单状态" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1  }">
                            <span >{{ activeOrderData.orderState}}</span>
                        </a-form-item>
                    </a-form>
                    <a-form :form="form" v-if="activeOrderData.orderState==='已撤销'">
                        <a-form-item label="撤销时间"  :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1 }">
                            <span>{{activeOrderData.annulDate}}</span>
                        </a-form-item>
                    </a-form>
                    <a-form :form="form">
                        <a-form-item label="信用值" :label-col="{ span: 4 }" :wrapper-col="{ span: 8, offset: 1 }">
                            <span>{{userInfo.credit}}</span>
                        </a-form-item>
                    </a-form>
                </a-tab-pane>
            </a-tabs>
        </div>
        <a-button type="primary" style="margin-left: 400px" @click="close"  >确定</a-button>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
const moment = require('moment')
export default {
    name: 'showOrder',
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
    computed: {
        ...mapGetters([
            'orderVisible',
            'activeOrderData',
            'activeOrderId',
            'userInfo'
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'showOrder' });
    },
    async mounted() {

    },
    methods: {
        ...mapMutations([
            'set_orderVisible',

        ]),
        ...mapActions([
        ]),
        close() {
            this.set_orderVisible(false)
        },



    }
}
</script>
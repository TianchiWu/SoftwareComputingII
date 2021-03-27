<template>

    <a-layout>
        <a-layout-content>
            <div class="hotelDetailCard">
                <h1>
                    {{ currentHotelInfo.title }}
                </h1>
                <div class="hotel-info">
                    <a-card style="width: 240px">
                        <img
                            alt="example"
                            src="@/assets/cover.jpeg"
                            slot="cover"
                            referrerPolicy="no-referrer"
                            />
                    </a-card>
                    <div class="info">
                        <div class="items" v-if="currentHotelInfo.hotelName">
                            <span class="label">酒店名称：</span>
                            <span class="value">{{ currentHotelInfo.hotelName }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.address">
                            <span class="label">地址</span>
                            <span class="value">{{ currentHotelInfo.address }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.hotelStar">
                            <span class="label">星级:</span>
                            <span class="value">{{ currentHotelInfo.hotelStar }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.hotelStar">
                            <span class="label">评分:</span>
                            <a-rate style="font-size: 15px" :value="currentHotelInfo.rate" disabled="true" allowHalf="true"/>
                        </div>
                        <div class="items" v-if="currentHotelInfo.phoneNum">
                            <span class="label">联系电话</span>
                            <span class="value">{{ currentHotelInfo.phoneNum}}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.description">
                            <span class="label">酒店简介:</span> 
                            <span class="value">{{ currentHotelInfo.description }}</span>
                        </div>
                    </div>
                </div>
                <a-divider></a-divider>
                <a-tabs>
                    <a-tab-pane tab="房间信息" key="1">
                        <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
                    </a-tab-pane>
                    <a-tab-pane tab="酒店详情" key="2">
                        <span class="value" style="font-size: 15px">{{this.currentHotelInfo.information}}</span>
                    </a-tab-pane>
                    <a-tab-pane tab="酒店评论" key="3">


                        <a-button type="primary" size="small" style="margin-left: 800px" @click="addTextVisible">添加评论</a-button>

                        <a-table
                                :columns="columns"
                                :dataSource="comments"
                                bordered
                        >
                            <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showText(record)">查看评论</a-button>
                                <a-popconfirm
                                        title="你确定删除此评论吗？"
                                        @confirm="confirmDeleteComment(record)"
                                        @cancel="cancelDeleteComment"
                                        okText="确定"
                                        cancelText="取消"
                                        v-if="record.userEmail==email"
                                >
                            <a-button type="primary" size="small"  >删除评论</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical"></a-divider>
                    </span>
                        </a-table>

                    </a-tab-pane>
                </a-tabs>
            </div>
            <ShowText></ShowText>
            <AddText></AddText>
        </a-layout-content>



    </a-layout>

</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import RoomList from './components/roomList'
import ShowText from './components/showText'
import AddText from './components/addText'
const moment = require('moment')
import {message} from "ant-design-vue";
const columns = [
    {
      title: '评论id',
      sorter:(a,b)=>a.id-b.id,
      dataIndex:'id',
    },
    {
        title: '用户',
        dataIndex: 'userEmail'
    },
    {
        title: '评分',
        sorter:(a,b)=>a.rate-b.rate,
        dataIndex: 'rate',
    },
    {
        title: '评论时间',
        sorter:(a,b)=> moment(a.commentTime).diff(moment(b.commentTime)),
        dataIndex: 'commentTime',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
export default {
    name: 'hotelDetail',
    components: {
        RoomList,
        ShowText,
        AddText,
    },
    data() {
        return {
            columns
        }
    },
    computed: {
        ...mapGetters([
            'currentHotelInfo',
            'userId',
            'comments',
            'email',
        ])
    },
    async mounted() {
        await this.set_currentCommentHotelId(Number(this.$route.params.hotelId))
        await this.set_currentHotelId(Number(this.$route.params.hotelId))
        await this.getHotelById()
        await this.getCommentList()
    },
    beforeRouteUpdate(to, from, next) {
        this.set_currentHotelId(Number(to.params.hotelId))
        this.getHotelById()
        next()
    },
    methods: {
        ...mapMutations([
            'set_currentHotelId',
            "set_currentCommentHotelId",
            'set_activeComment',
            'set_showText',
            'set_addText'
        ]),
        ...mapActions([
            'getHotelById',
            'getCommentList',
            'setAddText',
            'deleteComment',
        ]),
        showText(record){
            this.set_activeComment(record);
            this.set_showText(true);
        },
        addTextVisible(){
            this.setAddText(this.userId);
        },
        async confirmDeleteComment(record){
            await this.deleteComment(record.id);
        },
        cancelDeleteComment(){

        }
    }
}
</script>
<style scoped lang="less">
    .hotelDetailCard {
        padding: 50px 50px;
    }
    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;
        .info{
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;
            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
                .label{
                    margin-right: 10px;
                    font-size: 18px;
                }
                .value {
                    margin-right: 15px
                }
            }
        }
    }

</style>
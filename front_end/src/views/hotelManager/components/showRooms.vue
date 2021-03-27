<template>
    <a-modal
            :visible="roomVisible"
            title="房间列表"
            :footer="null"
            @cancel="close"
    >
        <a-button type="primary" size="small" @click="addRoom">添加房间</a-button>
        <a-button type="danger" size="small" style="margin-left: 15px" @click="deleteRooms">按房间类型删除房间</a-button>
        <div class="list">
            <a-table
                    :columns="columns"
                    :dataSource="activeRoomList"
            >
                <span slot="action" slot-scope="record">
                         <a-divider type="vertical" v-if="record.roomState === '空房'"></a-divider>
                        <a-popconfirm
                                title="你确定删除此房间吗？"
                                @confirm="confirmDeleteRoom(record)"
                                @cancel="cancelCancelRoom"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.roomState === '空房'"
                        >
                            <a-button type="danger" size="small">删除</a-button>
                        </a-popconfirm>

                    </span>
            </a-table>
        </div>
        <AddRoomModal></AddRoomModal>
        <DeleteRooms></DeleteRooms>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import AddRoomModal from "./addRoomModal";
    import DeleteRooms from "./deleteRooms"
    const columns = [
        {
          title: '房间id',
          sorter:(a,b)=>a.id-b.id,
          dataIndex: 'id',
          key:'id',
        },
        {
            title: '房型',
            dataIndex: 'roomType',
            filters: [{ text: '大床房', value: '大床房' }, { text: '双床房', value: '双床房' }, { text: '家庭房', value: '家庭房' }],
            onFilter: (value, record) => record.roomType.includes(value),
            key: 'roomType',
        },
        {
            title: '早餐',
            dataIndex: 'breakfast',
            key: 'breakfast',
        },
        {
            title: '设施',
            key: 'facility',
            dataIndex: 'facility',
        },
        {
            title: '价格',
            key: 'price',
            sorter:(a,b)=>a.price-b.price,
            dataIndex: 'price',
            scopedSlots: { customRender: 'price'}
        },
        {
            title: '房间状态',
            key: 'roomState',
            filters: [ { text: '空房', value: '空房' } ,{ text: '已预订', value: '已预订' }, { text: '已入住', value: '已入住' }],
            onFilter: (value, record) => record.roomState.includes(value),
            dataIndex: 'roomState',

        },
        {
          title: '所属订单号',
          key:'orderId',
          sorter:(a,b)=>a.orderId-b.orderId,
          dataIndex:'orderId'
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: { customRender: 'action' },
        },
    ];
    export default {
        name: 'showRooms',
        data() {
            return {
                data:[],
                columns,
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
            AddRoomModal,
            DeleteRooms,
        },
        computed: {
            ...mapGetters([
                'roomVisible',
                'activeRoomList',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'showRooms' });
        },
        async mounted() {
        },
        methods: {
            ...mapMutations([
                'set_roomVisible',
                'set_addRoomModalVisible',
                'set_deleteNum',
                'set_deleteRoomVisible',
                'set_activeRoomId',
                'set_deleteRoomNumVisible',

            ]),
            ...mapActions([
                'deleteRoom',
                'updateRoomNum',
                'getActiveRoomType'
            ]),
            close() {
                this.set_roomVisible(false)
            },
            addRoom() {
                this.set_addRoomModalVisible(true)
            },
            deleteRooms(){
                this.set_deleteRoomVisible(true)
                this.getActiveRoomType();
            },
            cancelCancelRoom(){

            },
            confirmDeleteRoom(record){
                this.deleteRoom(record.id);
                this.set_deleteNum(1);
                this.set_activeRoomId(record.roomId)
              this.updateRoomNum();
             },

        }
    }
</script>
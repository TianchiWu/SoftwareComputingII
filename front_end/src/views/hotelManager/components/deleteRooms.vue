<template>
    <a-modal
            :visible="deleteRoomVisible"
            title="房间类型"
            :footer="null"
            @cancel="close"
    >
        <div class="list">
            <a-table
                    :columns="columns"
                    :dataSource="activeRooms"
            >
                <span slot="action" slot-scope="record">
                    <a-button type="danger" size="small" @click="deleteRoomsNum(record)">指定数量</a-button>
                        <a-popconfirm
                                title="你确定删除此类房间吗？(只能删除空房)"
                                @confirm="confirmDeleteRoom(record)"
                                @cancel="cancelCancelRoom"
                                okText="确定"
                                cancelText="取消"

                        >
                            <a-button type="danger" size="small">删除</a-button>
                        </a-popconfirm>

                    </span>
            </a-table>
        </div>
        <DeleteRoomsNum></DeleteRoomsNum>
    </a-modal>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    import DeleteRoomsNum from './deleteRoomsNum'

    const columns = [
        {
            title: '房间类型id',
            sorter:(a,b)=>a.id-b.id,
            dataIndex: 'id',
            key:'id',
        },
        {
            title: '房型',
            dataIndex: 'roomType',
            filters: [{ text: '大床房', value: 'BigBed' }, { text: '双床房', value: 'DoubleBed' }, { text: '家庭房', value: 'Family' }],
            onFilter: (value, record) => record.roomType.includes(value),
            key: 'roomType',
            scopedSlots: { customRender: 'roomType' }
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
            title: '剩余数量',
            key: 'curNum',
            sorter:(a,b)=>a.curNum-b.curNum,
            dataIndex: 'curNum'
        },
        {
            title: '总量',
            key: 'total',
            sorter:(a,b)=>a.total-b.total,
            dataIndex: 'total',
        },
        {
            title: '价格',
            key: 'price',
            sorter:(a,b)=>a.price-b.price,
            dataIndex: 'price',
            scopedSlots: { customRender: 'price'}
        },
        {
            title: 'Action',
            key: 'action',
            scopedSlots: { customRender: 'action' },
        },
    ];
    export default {
        name: "deleteRooms",
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
            DeleteRoomsNum,
        },
        computed: {
            ...mapGetters([
                'deleteRoomVisible',
                'activeRooms',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'deleteRooms' });
        },
        async mounted() {
        },
        methods: {
            ...mapMutations([
                'set_deleteRoomVisible',
                'set_deleteNum',
                'set_activeRoomId',
                'set_deleteRoomNumVisible',
            ]),
            ...mapActions([
                'updateRoomNum',
                'DeleteRooms'
            ]),
            close() {
                this.set_deleteRoomVisible(false)
            },
            cancelCancelRoom(){

            },
            confirmDeleteRoom(record){

                this.set_deleteNum(record.curNum);
                this.set_activeRoomId(record.id)
                this.DeleteRooms();
                this.updateRoomNum();
            },
            deleteRoomsNum(record){
                this.set_activeRoomId(record.id)
                this.set_deleteRoomNumVisible(true)
            }
        }
    }
</script>

<style scoped>

</style>
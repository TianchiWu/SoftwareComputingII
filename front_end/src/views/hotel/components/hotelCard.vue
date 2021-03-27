<template>
  <a-card hoverable class="hotelCard ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
    <img
      alt="example"
      src="@/assets/cover.jpeg"
      slot="cover"
      referrerPolicy="no-referrer"
    />
    <a-tooltip :title="hotel.title" placement="top">
      <a-card-meta :title="hotel.hotelName">
      <template slot="description">
        <a-rate style="font-size: 15px" :value="hotel.rate" disabled allowHalf/> {{hotel.rate}}分
      </template>
        <template slot="description">
          <a-form style="margin-top: 7px"></a-form>
            <span >{{hotel.hotelStar}}</span>

        </template>
        <template slot="description">
          <a-form style="margin-top: 7px"></a-form>
            <span>{{hotel.address}}</span>

        </template>
    </a-card-meta>
    </a-tooltip>
  </a-card>
</template>
<script>
export default {
  name:'',
  props: {
    hotel: {}
  },
  data() {
    return{

    }
  },
}
</script>
<style scoped lang="less">
    .hotelCard {
        margin: 10px 10px;
        min-width: 200px;
        max-height: 410px;
        img {
          height: 275px;
        }
    }
</style>

<style lang="less">
.hotelCard{
  .ant-card-body{
    padding: 12px;
  }
}

.ant-card-hoverable:hover {
  box-shadow: 0 2px 8px rgba(0,0,0,0.4)
}

</style>
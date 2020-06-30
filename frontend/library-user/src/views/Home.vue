<template>
  <el-container>
    <el-header>
      <Header/>
    </el-header>
    <el-container>
      <el-aside>
        <Navbar/>
      </el-aside>
      <el-main>
        <el-card>
          <el-table
            :data="tableData"
            height="750"
            style="width: 100%">
            <el-table-column
              prop="orderId"
              label="订单号"
              width="200"
            >
            </el-table-column>
            <el-table-column
              prop="bookName"
              label="图书名称"
              width="200">
            </el-table-column>
            <el-table-column
              prop="isbn"
              label="ISBN号"
            >
            </el-table-column>
            <el-table-column
              prop="borrowTime"
              label="借阅时间"
            >
            </el-table-column>
            <el-table-column
              prop="estimateReturnTime"
              label="预计还书时间"
            >
            </el-table-column>
            <el-table-column
              prop="state"
              label="状态"
              :formatter="stateFormatter"
            >
            </el-table-column>
          </el-table>
        </el-card>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
  import Header from "../components/Header";
  import Navbar from "../components/Navbar";
  import Global from "../components/Global";

  export default {
    name: "Home.vue",
    components: {Navbar, Header},
    beforeRouteEnter: (from, to, next) => {
      next(vm => {
        vm.getOrderList();
      });
    },
    data() {
      return {
        tableData: [{
          orderId: '',
          bookId: '',
          bookName: '',
          isbn: '',
          borrowTime: '',
          estimateReturnTime: '',
          state: ''
        }],
      }
    },
    methods: {
      getOrderList() {
        this.axios({
          method: 'post',
          url: Global.httpUrl + 'user/order/list',
          headers: {
            'Content-Type': 'application/json',
            'token': Global.token
          }
        }).then(response => {
          if (response.data.success === 20000000) {
            this.tableData = response.data.data;
          } else {
            this.$message.error(response.data.message);
          }
        })
      },
      stateFormatter(row, column) {
        let state = row.state;
        return state === 0 ? "正常" : "已逾期";
      }
    }
  }

</script>

<style scoped>

</style>

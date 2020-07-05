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

  export default {
    name: "Home.vue",
    components: {Navbar, Header},
    data() {
      return {
        tableData: [{
          orderId: '202007030000023123',
          bookId: '1',
          bookName: '高等数学',
          isbn: '',
          borrowTime: '2020-07-03',
          estimateReturnTime: '2020-07-19',
          state: '2'
        }],
      }
    }
  }

</script>

<style scoped>

</style>

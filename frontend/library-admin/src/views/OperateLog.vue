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
            border
            height="750"
            style="width: 100%">
            <el-table-column
              prop="date"
              label="时间"
              fixed
            >
            </el-table-column>
            <el-table-column
              prop="operationName"
              label="操作名称"
            >
            </el-table-column>
            <el-table-column
              prop="operateCode"
              label="代码"
            >
            </el-table-column>
            <el-table-column
              prop="method"
              label="操作方法"
            >
            </el-table-column>
            <el-table-column fixed="right" label="详情" width="200">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="primary"
                  @click="openEditForm(scope.$index, scope.row)">查看详情
                </el-button>
              </template>
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
    name: "OperateLog",
    components: {Navbar, Header},
    beforeRouteEnter: (from, to, next) => {
      next(vm => {
        vm.initData();
      });
    },
    data() {
      return {
        tableData: [{
          id: '',
          date: '',
          operationName: '',
          operateCode: '',
          method: ''
        }]
      }
    },
    methods: {
      initData() {
        this.axios({
          method: 'get',
          url: Global.httpUrl + 'admin/log/operate',
          headers: {
            'Content-Type': 'application/json',
            'token': Global.token
          }
        }).then(response => {
          console.log(response);
          this.tableData = response.data.data;
        }).catch(error => {
          console.log(error);
        })
      }
    }
  }
</script>

<style scoped>

</style>

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
              prop="time"
              label="时间"
            >
            </el-table-column>
            <el-table-column
              prop="warnName"
              label="异常名称"
            >
            </el-table-column>
            <el-table-column
              prop="warnCode"
              label="异常代码"
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
    name: "WarnLog",
    beforeRouteEnter: (from, to, next) => {
      next(vm => {
        vm.initData();
      });
    },
    components: {Navbar, Header},
    data() {
      return {
        tableData: [{
          id: '',
          time: '',
          warnName: '',
          warnCode: ''
        }]
      }
    },
    methods: {
      initData() {
        this.axios({
          method: 'get',
          url: Global.httpUrl + 'admin/log/warn',
          headers: {
            'Content-Type': 'application/json',
            'token': localStorage.getItem('token')
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

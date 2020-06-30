<template>
  <div>
    <el-container>
      <el-header>
        <Header/>
      </el-header>
      <el-container>
        <el-aside>
          <Navbar/>
        </el-aside>
        <el-main>
          <div style="margin-top: 15px;">
            <el-input
              placeholder="请输入内容"
              v-model="input"
              clearable>
            </el-input>
          </div>
          <div>
            <el-table
              :data="tableData"
              border
              style="width: 100%">
              <el-table-column
                prop="name"
                label="书名"
                width="180">
              </el-table-column>
              <el-table-column
                prop="publishDate"
                label="出版日期"
                width="180">
              </el-table-column>
              <el-table-column
                prop="isbn"
                label="ISBN号">
              </el-table-column>
              <el-table-column
                prop="author"
                label="作者">
              </el-table-column>
              <el-table-column
                prop="price"
                label="价格">
              </el-table-column>
              <el-table-column
                prop="introduction"
                label="内容简介">
              </el-table-column>
              <el-table-column
                prop="number"
                label="库存">
              </el-table-column>
              <el-table-column
                prop="state"
                label="状态">
              </el-table-column>
              <el-table-column
                fixed="right"
                label="操作"
                width="100">
                <template slot-scope="scope">
                  <el-button @click="openDetails(scope.row)" type="text" size="small">查看详情</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              :page-size="20"
              :pager-count="11"
              layout="prev, pager, next"
              :total="1000">
            </el-pagination>
          </div>
        </el-main>
      </el-container>
      <el-dialog :visible.sync="dialogDetails" @close="clossDetailForm">
        <el-container>
          <el-header>
            <h1>详细信息</h1>
          </el-header>
          <el-main>
            <el-row type="flex" class="row-bg" justify="space-around">
              <el-col :span="4" style="margin-top: 15px">
                <div class="grid-content bg-purple">
                  图书名称
                </div>
              </el-col>
              <el-col :span="14" style="margin-top: 15px">
                <div class="grid-content bg-purple-light">
                  那些忧伤的年轻人
                </div>
              </el-col>
            </el-row>
            <el-row type="flex" class="row-bg" justify="space-around">
              <el-col :span="4" style="margin-top: 15px">
                <div class="grid-content bg-purple">
                  简介
                </div>
              </el-col>
              <el-col :span="14" style="margin-top: 15px">
                <div class="grid-content bg-purple-light">
                  简介
                </div>
              </el-col>
            </el-row>
            <el-row type="flex" class="row-bg" justify="space-around">
              <el-col :span="6" style="margin-top: 15px">
                <div class="grid-content bg-purple">
                  ISBN编号
                </div>
              </el-col>
              <el-col :span="6" style="margin-top: 15px">
                <div class="grid-content bg-purple-light">
                  isbn
                </div>
              </el-col>
              <el-col :span="6" style="margin-top: 15px">
                <div class="grid-content bg-purple">
                  作者
                </div>
              </el-col>
              <el-col :span="6" style="margin-top: 15px">
                <div class="grid-content bg-purple-light">
                  作者
                </div>
              </el-col>
            </el-row>
            <el-row type="flex" class="row-bg" justify="space-around">
              <el-col :span="4" style="margin-top: 15px">
                <div class="grid-content bg-purple">
                  出版日期
                </div>
              </el-col>
              <el-col :span="4" style="margin-top: 15px">
                <div class="grid-content bg-purple-light">
                  出版日期
                </div>
              </el-col>
              <el-col :span="4" style="margin-top: 15px">
                <div class="grid-content bg-purple">
                  库存量
                </div>
              </el-col>
              <el-col :span="4" style="margin-top: 15px">
                <div class="grid-content bg-purple-light">
                  库存量
                </div>
              </el-col>
              <el-col :span="4" style="margin-top: 15px">
                <div class="grid-content bg-purple">
                  价格
                </div>
              </el-col>
              <el-col :span="4" style="margin-top: 15px">
                <div class="grid-content bg-purple-light">
                  价格
                </div>
              </el-col>
            </el-row>
          </el-main>
          <el-footer>
            <el-button id="btn" disabled>申请借阅</el-button>
          </el-footer>
        </el-container>
      </el-dialog>
    </el-container>

  </div>
</template>

<script>
  import Header from "../components/Header";
  import Navbar from "../components/Navbar";
  import Global from "../components/Global";

  export default {
    name: "Book.vue",
    components: {Navbar, Header},
    beforeRouteEnter: (from, to, next) => {
      next(vm => {
        vm.initData();
      });
    },
    data() {
      return {
        dialogDetails: false,
        tableData: [
          {
            id: 1,
            name: "那些忧伤的年轻人",
            publishDate: "2015-12-31",
            isbn: "978-7-5470-3950-2",
            author: "FS菲兹杰拉德",
            price: 32.00,
            introduction: "这世上有千万种爱，但没有一种爱，可以重来",
            number: 5,
            state: 1
          }
        ],
        total:'',
        pageSize:'',
        input: '',
        select: ''
      }
    },
    methods: {
      openDetails(row) {
        this.dialogDetails = true;
      },
      initData() {
        this.axios({
          method: 'get',
          url: Global.httpUrl + 'book/1',
          headers: {
            'Content-Type': 'application/json',
            'token': Global.token
          }
        }).then(response=>{
          if(response.data.code === 20000000){
            this.tableData = this.data.data.list;
            this.total = this.data.data.pages;
            this.total = this.data.data.pageSize;
          }else {
            this.$message.error(response.data.message);
          }
        })
      }
    }
  }
</script>

<style scoped>
  .el-select .el-input {
    width: 130px;
  }

  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }

  .el-row {
    margin-bottom: 20px;
  }

  .el-col {
    border-radius: 4px;
  }

  .bg-purple-dark {
    background: #99a9bf;
  }

  .bg-purple {
    background: #d3dce6;
  }

  .bg-purple-light {
    background: #e5e9f2;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }

  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>

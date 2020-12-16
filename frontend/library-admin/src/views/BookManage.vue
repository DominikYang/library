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
          <el-card>
            <div slot="header" class="clearfix">
              <el-button class="add-button" type="primary" style="float: left; padding: 0px 0;font-size: small"
                         @click="openAddForm" round>添加图书
              </el-button>
            </div>
            <el-table
              :data="tableData"
              border
              style="width: 100%"
              >
              <el-table-column
                fixed
                prop="name"
                label="书名"
                width="150"
              >
              </el-table-column>
              <el-table-column
                prop="publishDate"
                label="出版日期"
                width="150"
              >
              </el-table-column>
              <el-table-column
                prop="isbn"
                label="ISBN编号"
                width="150"
              >
              </el-table-column>
              <el-table-column
                prop="author"
                label="作者"
                width="150"
              >
              </el-table-column>
              <el-table-column
                prop="price"
                label="价格"
                width="150"
              >
              </el-table-column>
              <el-table-column
                prop="number"
                label="库存量"
                width="150"
              >
              </el-table-column>
              <el-table-column
                prop="state"
                label="状态"
                width="150"
              >
              </el-table-column>
              <el-table-column fixed="right" width="200" label="操作">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="primary"
                    @click="openEditForm(scope.$index, scope.row)">编辑
                  </el-button>
                  <el-button
                    size="mini"
                    type="danger"
                    @click="openEditForm(scope.$index, scope.row)">删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              @current-change="handleCurrentChange"
              :page-size="pageInfo.pageSize"
              layout="prev, pager, next"
              :total="pageInfo.total">
            </el-pagination>
          </el-card>
        </el-main>
      </el-container>
    </el-container>

    <el-dialog :title="title" :visible.sync="dialogAddVisible" @close="closeAddForm">
      <el-form :model="addForm" ref="addForm">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="图书名称">
              <el-input v-model="addForm.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="ISBN号">
              <el-input v-model="addForm.isbn"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="作者">
              <el-input v-model="addForm.author"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="出版日期">
              <el-date-picker
                style="width: 80%"
                v-model="addForm.publishDate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="价格">
              <el-input v-model="addForm.price"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="简介">
            <el-input type="textarea"
                      :autosize="{ minRows: 2, maxRows: 4}" v-model="addForm.introduction"></el-input>
          </el-form-item>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="库存">
              <el-input style="width: 60%" v-model="addForm.number"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-select v-model="addForm.state" placeholder="请选择">
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveAddForm()">确 定</el-button>
        <el-button @click="dialogAddVisible = false">取 消</el-button>
      </div>
    </el-dialog>


    <el-dialog :title="title" :visible.sync="dialogEditVisible" @close="closeEditForm">
      <el-form :model="editForm" ref="editForm">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="图书名称">
              <el-input v-model="editForm.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="ISBN号">
              <el-input v-model="editForm.isbn"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="作者">
              <el-input v-model="editForm.author"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="出版日期">
              <el-date-picker
                style="width: 80%"
                v-model="editForm.publishDate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="价格">
              <el-input v-model="editForm.price"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="简介">
          <el-input v-model="editForm.introduction"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="editForm.state" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveEditForm()">确 定</el-button>
        <el-button @click="dialogEditVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Navbar from "../components/Navbar";
import Header from "../components/Header";
import Global from "../components/Global";

export default {
  name: "BookManage",
  components: {Header, Navbar},
  data() {
    return {
      dialogAddVisible: false,
      dialogEditVisible: false,
      options: [
        {
          label: "可借阅",
          value: 1
        },
        {
          label: "待上架",
          value: 2
        }
      ],
      tableData: [
        {
          id: '1',
          name: '暂无数据',
          publishDate: '2015-12-31',
          isbn: '978-097-2555',
          author: '',
          price: '00.00',
          number: '0',
          state: 1
        }
      ],
      editForm: {},
      addForm: {},
      pageInfo: {
        total: 1,
        pageNum: 1,
        pageSize: 20,
        size: 1,
        startRow: 1,
        endRow: 1,
        pages: 1,
        prePage: 0,
        nextPage: 0,
        isFirstPage: true,
        isLastPage: true,
        hasPreviousPage: false,
        hasNextPage: false,
        navigatePages: 8,
        navigatepageNums: [
          1
        ],
        navigateFirstPage: 1,
        navigateLastPage: 1
      },
      currentPage: 1
    }
  },
  mounted() {
    this.getBookList();
  },
  methods: {
    getBookList(){
      this.axios({
        method: 'get',
        url: Global.httpUrl + 'book/' + this.$data.currentPage
      }).then(response => {
        console.log(response);
        if (response.data.code === 200) {
          this.$data.pageInfo = response.data.data;
          this.$data.tableData = response.data.data.list;
          this.$data.currentPage = response.data.data.pageNum
        } else {
          this.$message.error(response.data.message);
        }
      })
    },
    closeAddForm(){
      this.addForm = {};
    },
    handleCurrentChange: function (currentPage) {
      this.$data.currentPage = currentPage;
      console.log(this.currentPage);
      this.getBookList();
    },
    saveEditForm() {
      console.log("save edit form")
    },
    saveAddForm() {
      console.log(this.$data.addForm.publishDate.getTime())
      this.axios({
        method: 'get',
        url: Global.httpUrl + 'admin/book/add',
        data: JSON.stringify({
          name: this.$data.addForm.name,
          isbn: this.$data.addForm.isbn,
          publishDate:this.$data.addForm.publishDate.getTime(),
          author:this.$data.addForm.author,
          price:this.$data.addForm.price,
          introduction:this.$data.addForm.introduction,
          number:this.$data.addForm.number,
          state:this.$data.addForm.state
        }),
      }).then(response => {
        console.log(response);
        if (response.data.code === 200) {
          this.$message.info('添加成功');
        } else {
          this.$message.error(response.data.message);
        }
      }).then( () =>
        {
          this.closeAddForm()
          this.dialogAddVisible = false;
        }
      )
    },
    openAddForm() {
      this.dialogAddVisible = true;
      this.title = "新增图书信息"
    },
    openEditForm() {
      this.dialogEditVisible = true;
      this.title = "编辑图书信息"
    }
  }
}
</script>

<style scoped>
.clearfix {
  height: 30px;
}

.add-button {
  width: 100px;
  height: 40px;
}

.dialog-footer {
  text-align: center;
}
</style>

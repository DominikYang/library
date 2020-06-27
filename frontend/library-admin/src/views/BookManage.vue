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
              height="500"
              style="width: 100%">
              <el-table-column
                prop="name"
                label="书名"
                >
              </el-table-column>
              <el-table-column
                prop="publishDate"
                label="出版日期"
              >
              </el-table-column>
              <el-table-column
                prop="isbn"
                label="ISBN编号"
              >
              </el-table-column>
              <el-table-column
                prop="author"
                label="作者"
              >
              </el-table-column>
              <el-table-column
                prop="price"
                label="价格"
              >
              </el-table-column>
              <el-table-column
                prop="number"
                label="库存量"
              >
              </el-table-column>
              <el-table-column
                prop="state"
                label="状态"
              >
              </el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="primary"
                    @click="openEditForm(scope.$index, scope.row)">编辑
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-main>
      </el-container>
    </el-container>

    <el-dialog :title="title" :visible.sync="dialogAddVisible" @close="closeAddForm">
      <el-form :model="addForm" ref="addForm">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="图书名称">
              <el-input v-model="addForm.nickName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="ISBN号">
              <el-input v-model="addForm.nickName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="作者">
              <el-input v-model="addForm.nickName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="出版日期">
              <el-date-picker
                v-model="addForm.publishDate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="价格">
              <el-input v-model="addForm.nickName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="简介" >
          <el-input  v-model="addForm.nickName"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="value" placeholder="请选择">
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
        <el-button type="primary" @click="saveAddForm()">确 定</el-button>
        <el-button @click="dialogAddVisible = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="title" :visible.sync="dialogEditVisible" @close="closeEditForm">
      <el-form :model="editForm" ref="addForm">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="图书名称">
              <el-input v-model="editForm.nickName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="ISBN号">
              <el-input v-model="editForm.nickName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="作者">
              <el-input v-model="editForm.nickName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="出版日期">
              <el-date-picker
                v-model="editForm.publishDate"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="价格">
              <el-input v-model="editForm.nickName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="简介" >
          <el-input  v-model="editForm.nickName"></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="value" placeholder="请选择">
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

  export default {
    name: "BookManage",
    components: {Header, Navbar},
    data() {
      return {
        dialogAddVisible:false,
        dialogEditVisible: false,
        options:'',
        editForm:[{

        }],
        addForm:[{

        }]
      }
    },
    methods: {
      openAddForm(){
        this.dialogEditVisible = true;
        this.title = "新增图书信息"
      },
      openEditForm(){
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
  .dialog-footer{
    text-align: center;
  }
</style>

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
        <el-form-item label="简介">
          <el-input v-model="addForm.nickName"></el-input>
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
        <el-form-item label="简介">
          <el-input v-model="editForm.nickName"></el-input>
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
        dialogAddVisible: false,
        dialogEditVisible: false,
        options: '',
        tableData: [
          {
            id: '1',
            name: '高等数学',
            publishDate: '2015-12-31',
            isbn: '978-097-2555',
            author: '韦宇阳',
            price: '10.00',
            number: '100',
            state: '1'
          },
          {
            id: '2',
            name: '在路上',
            publishDate: '2020-1-20',
            isbn: '978-7-5594-0560-9',
            author: '韦宇阳',
            price: '59.9',
            number: '99',
            state: '1'
          },
          {
            id: '3',
            name: '什么是哲学',
            publishDate: '2015-12-1',
            isbn: '978-7-5610-8218-8',
            author: '王国坛',
            price: '39.00',
            number: '98',
            state: '1'
          },
          {
            id: '4',
            name: '国境外警察概论',
            publishDate: '2015-12-3',
            isbn: '978-7-5610-8215-7',
            author: '于群',
            price: '45.00',
            number: '97',
            state: '1'
          },
          {
            id: '5',
            name: '当代中国社会问题研究',
            publishDate: '2016-1-2',
            isbn: '978-7-5610-8231-7',
            author: '李健',
            price: '32.00',
            number: '96',
            state: '1'
          },
          {
            id: '6',
            name: '架起心灵的桥梁',
            publishDate: '2015-12-31',
            isbn: '978-7-5610-8225-6',
            author: '韦宇阳',
            price: '30.00',
            number: '95',
            state: '1'
          },
          {
            id: '7',
            name: '量子力学学习辅导',
            publishDate: '2016-1-4',
            isbn: '978-7-5610-8227-0',
            author: '方戈亮',
            price: '38.00',
            number: '94',
            state: '1'
          },
          {
            id: '8',
            name: '遇见德国',
            publishDate: '2015-12-31',
            isbn: '978-7-01-015676-7',
            author: '杨坚华',
            price: '39.00',
            number: '93',
            state: '2'
          },
          {
            id: '9',
            name: '多民族国家的文学与文化',
            publishDate: '2015-12-31',
            isbn: '978-7-01-015675-0',
            author: '徐新建',
            price: '50.00',
            number: '92',
            state: '2'
          },
          {
            id: '10',
            name: '人口红利问题研究',
            publishDate: '2015-12-31',
            isbn: '978-7-01-015682-8',
            author: '王婷',
            price: '27.00',
            number: '91',
            state: '2'
          },
          {
            id: '11',
            name: '国家重器',
            publishDate: '2015-12-31',
            isbn: '978-7-01-015688-0',
            author: '杨英健',
            price: '59.00',
            number: '90',
            state: '2'
          },
          {
            id: '12',
            name: '唐高祖传',
            publishDate: '2015-12-31',
            isbn: '978-7-01-015698-9',
            author: '牛致功',
            price: '34.00',
            number: '89',
            state: '2'
          },
          {
            id: '13',
            name: '玉树临风',
            publishDate: '2016-10-3',
            isbn: '978-7-01-015783-2',
            author: '马顺清',
            price: '68.00',
            number: '88',
            state: '2'
          },
          {
            id: '14',
            name: '中国自信',
            publishDate: '2016-6-28',
            isbn: '978-7-01-015768-9',
            author: '章传家',
            price: '48.00',
            number: '87',
            state: '2'
          },
          {
            id: '15',
            name: '做不受人惑的人',
            publishDate: '201-1-20',
            isbn: '978-7-5546-0608-7',
            author: '胡适',
            price: '35.00',
            number: '86',
            state: '3'
          },
          {
            id: '16',
            name: '葑溪琐记',
            publishDate: '2015-12-31',
            isbn: '978-7-5546-0611-7',
            author: '陆辰荪',
            price: '36.00',
            number: '85',
            state: '3'
          },
          {
            id: '17',
            name: '艺唯心境',
            publishDate: '2015-12-31',
            isbn: '978-7-5401-2795-4',
            author: '王卫红',
            price: '160.00',
            number: '84',
            state: '3'
          },
          {
            id: '18',
            name: '甜蜜与痛苦',
            publishDate: '2015-12-31',
            isbn: '978-7-5399-7098-1',
            author: '王立新',
            price: '38.00',
            number: '83',
            state: '3'
          },
          {
            id: '19',
            name: '消失的渡口',
            publishDate: '2015-12-31',
            isbn: '978-7-5399-8062-1',
            author: '徐立峰',
            price: '42.00',
            number: '82',
            state: '3'
          },
          {
            id: '20',
            name: '山水画',
            publishDate: '2015-12-31',
            isbn: '978-7-5344-8376-9',
            author: '文良玉',
            price: '20.00',
            number: '81',
            state: '3'
          }
        ],
        editForm: [{}],
        addForm: [{}]
      }
    },
    methods: {
      openAddForm() {
        this.dialogEditVisible = true;
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

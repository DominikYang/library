<template>
  <div>
    <el-container>
      <el-header>
        <h1>用户管理</h1>
      </el-header>
      <el-container>
        <el-aside>
          <Navbar/>
        </el-aside>
        <el-main>
          <el-card>
            <div slot="header" class="clearfix">
              <el-button class="add-button" type="primary" style="float: left; padding: 0px 0;font-size: small"
                         @click="openAddForm" round>新增用户
              </el-button>
            </div>
            <el-table
              :data="UserData"
              border
              height="500"
              style="width: 100%">
              <el-table-column
                prop="id"
                label="用户id"
                width="100">
              </el-table-column>
              <el-table-column
                prop="realName"
                label="姓名"
                width="100"
                fixed
              >
              </el-table-column>
              <el-table-column
                prop="username"
                label="用户账号"
                width="150">
              </el-table-column>
              <el-table-column
                prop="code"
                label="学号\员工号"
                width="200">
              </el-table-column>
              <el-table-column
                prop="username"
                label="用户账号"
                width="200">
              </el-table-column>
              <el-table-column
                prop="department"
                label="所属院系"
                width="200">
              </el-table-column>
              <el-table-column
                prop="major"
                label="专业"
                width="200">
              </el-table-column>
              <el-table-column
                prop="grade"
                label="年级"
              >
              </el-table-column>
              <el-table-column fixed="right" label="操作" width="200">
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
  </div>
</template>

<script>
  import Navbar from "../components/Navbar";

  export default {
    name: "UserManage",
    components: {Navbar}
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
</style>

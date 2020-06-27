<template>
<div>
  <el-container>
    <el-header>

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
            :data="tableData"
            border
            height="750"
            style="width: 100%">
            <el-table-column
              prop="state"
              label="状态"
              width="150"
              :formatter="statesFormatter"
            >
            </el-table-column>
            <el-table-column
              prop="id"
              label="用户id"
              width="200">
            </el-table-column>
            <el-table-column
              prop="username"
              label="用户账号"
              width="450">
            </el-table-column>
            <el-table-column
              prop="nickName"
              label="用户昵称"
            >
            </el-table-column>
            <el-table-column label="操作" width="300">
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
        name: "BookManage",
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

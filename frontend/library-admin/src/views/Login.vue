<template>
  <div class="parent-box">
    <el-card>
      <h1 style="font-size: 20px">登录</h1>
      <el-form :label-position="left" label-width="80px" :model="formLabelAlign">
        <el-form-item label="账号">
          <el-input v-model="formLabelAlign.username"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="formLabelAlign.password" type="password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-col :span="12" :offset="5">
            <el-button type="primary" @click="submitForm('formLabelAlign')">登录</el-button>
          </el-col>
        </el-form-item>
      </el-form>
    </el-card>
  </div>

</template>

<script>
    import Global from "../components/Global";

    export default {
        name: "Login",
        data() {
            return {
                labelPosition: 'right',
                formLabelAlign: {
                    username: '',
                    password: '',
                }
            };
        },
        methods: {
            submitForm: function () {
                this.axios({
                    method: 'post',
                    url:  Global.httpUrl + 'admin/login',
                    data: JSON.stringify({
                        username: this.formLabelAlign.username,
                        password: this.formLabelAlign.password
                    }),
                    headers: {'Content-Type': 'application/json'}
                }).then(response => {
                    console.log(response);
                    if (response.data.code === 200) {
                        Global.setToken(response.data.data.token);
                        this.$router.push({path: '/home'})
                    }else {
                        this.$message.error(response.data.message);
                    }
                })
            },

        }
    }
</script>

<style scoped>
  .parent-box{
    margin: 0 auto;
    width: 700px;
    height: 300px;
  }

  .el-form{
    position: relative;
    margin-top: 10%;
  }

  .el-card{
    height: 400px;
  }

  .el-button {
    width: 100%;
  }

</style>

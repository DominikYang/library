<template>
    <el-menu :default-active="this.$router.path" router>
      <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
        {{ item.navItem }}
      </el-menu-item>
      <el-submenu index="1">
        <template slot="title">
          <span>日志查看</span>
        </template>
        <el-menu-item-group>
          <el-menu-item v-for="(item,i) in logList" :key="i" :index="item.name">
            {{ item.navItem }}
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
</template>

<script>
    export default {
        name: "Navbar",
        data() {
            return {
                activeIndex: '1',
                activeIndex2: '1',
                navList:[
                    {name:'/home',navItem:'个人信息'},
                    {name:'/book',navItem:'图书管理'},
                    {name:'/user',navItem:'用户管理'},

                ],
              logList:[
                {name:'/error',navItem:'异常日志'},
                {name:'/operate',navItem:'操作日志'},
              ]
            };
        },
        methods: {
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            }
        }
    }
</script>

<style scoped>

</style>

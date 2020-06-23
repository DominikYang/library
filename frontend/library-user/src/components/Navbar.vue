<template>
    <el-menu :default-active="this.$router.path" router>
      <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
        {{ item.navItem }}
      </el-menu-item>
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
                    {name:'/home',navItem:'个人借阅情况'},
                    {name:'/book',navItem:'图书浏览'},
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

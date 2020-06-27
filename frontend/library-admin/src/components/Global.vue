<template>

</template>

<script>
    const httpUrl = 'http://localhost:8080/api/v1/';
    const token = '';
    function setToken(token) {
        this.token = token;
    }
    export default {
        name: "Global",
        httpUrl,
        setToken,
        token
    }
</script>

<style scoped>

</style>

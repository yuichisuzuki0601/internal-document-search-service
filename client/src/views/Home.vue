<template>
  <div>
    <img alt="Vue logo" src="@/assets/logo.png" />
    <h1>Home</h1>
  </div>
</template>

<script>
export default {
  name: 'Home',
  created() {
    this.$store.dispatch('setPageTitle', { pageTitle: 'Home' });
  }
}
</script>

<style scoped>
div {
  text-align: center;
}
</style>
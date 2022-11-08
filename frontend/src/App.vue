<template>
  <LineChartVue />
</template>

<script>
import LineChartVue from "./components/LineChart.vue";

export default {
  name: "App",
  components: {
    LineChartVue,
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

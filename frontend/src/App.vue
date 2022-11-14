<template>
  <nav class="navbar navbar-light banner">
    <a class="navbar-brand" href="#">
      <a class="navbar-brand" href="#">
        <img
          src="./assets/ff_header2.png"
          width="100"
          height="50"
          alt=""
          class="d-inline-block align-top"
        />
        <!-- <inline> <h2>Crpyto Analysis Project</h2> </inline> -->
      </a>
      Raphael J. Sandor - Crpyto Analysis Project
    </a>
  </nav>
  <div class="container-fluid">
    <div class="row">
      <div class="col-2 nopadding">
        <div class="list-group algselect">
          <a href="#" class="list-group-item list-group-item active"> Cras justo odio </a>
          <a href="#" class="list-group-item list-group-item-action"
            >Dapibus ac facilisis in</a
          >
          <a href="#" class="list-group-item list-group-item-action">Morbi leo risus</a>
          <a href="#" class="list-group-item list-group-item-action"
            >Porta ac consectetur ac</a
          >
          <a href="#" class="list-group-item list-group-item-action disabled"
            >Vestibulum at eros</a
          >
        </div>
      </div>
      <div class="col-10 .chart-container">
        <LineChartVue />
      </div>
    </div>
    <div class="row flex-grow-1">
      <div class="col-2">test</div>
      <div class="col-10">
        <prism class="code" language="javascript">{{ test.value }} </prism>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import LineChartVue from "./components/LineChart.vue";
import text1 from "raw-loader!../../server/src/main/java/dev/findfirst/CryptoProjectFinal/service/BabyStepGiaintStep.java";
import Prism from "vue-prism-component";

export default {
  name: "App",
  data() {
    return {
      test: { name: "text1", value: text1 },
    };
  },
  components: {
    LineChartVue,
    Prism,
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
}

html,
body {
  margin: 0;
  padding: 0;
}

.chart-container {
  position: relative;
  margin: auto;
  height: 100%;
  width: 100%;
}

.algselect {
  /* padding-left: 0; */
  margin-left: 0;
  padding-left: 0;
  border-radius: 0 !important;
}

.banner {
  background: #296bd3;
}

.nopadding {
  padding: 0 !important;
  margin: 0 !important;
}
/* .code {
  background-color: green;
  height: 100%;
} */
.fill-height {
  display: flex;
  flex-direction: column;
  min-height: 100%;
}
.code {
  height: -webkit-calc(100vh - 500px);
  height: -moz-calc(100vh - 500px);
  height: calc(100vh - 500px);
}
</style>

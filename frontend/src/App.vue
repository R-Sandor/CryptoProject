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
          <a href="#" class="list-group-item list-group-item active"> RSA </a>
          <a href="#" class="list-group-item list-group-item-action">Diffie-Hellman</a>
          <a href="#" class="list-group-item list-group-item-action"> Elliptic Curve</a>
        </div>
      </div>
      <div class="col-10 .chart-container">
        <LineChartVue />
      </div>
    </div>
    <div class="row flex-grow-1">
      <div class="col-2">
        <div class="card sysStat">
          <div class="card-header">System Statistics</div>
          <ul class="list-group list-group-flush" style="text-align: left">
            <li class="list-group-item">
              CPU Speed: {{ stats.maxFreq / 1000000000 }}GHz
            </li>
            <li class="list-group-item">Cores: {{ stats.logicalProcessorCount }}</li>
            <li class="list-group-item">
              Mem: {{ Math.round((stats.memory / 1000000000) * 100) / 100 }}Gb
            </li>
          </ul>
        </div>
      </div>
      <div class="col-10 codeSel2">
        <div class="codeSel">
          <div class="btn-group" role="group" aria-label="Basic example">
            <button type="button" class="btn btn-secondary">Brute Force</button>
            <button type="button" class="btn active btn-secondary">
              Baby Step Gaint Step
            </button>
            <button type="button" class="btn btn-secondary">Pollard's Rho Method</button>
          </div>
        </div>
        <prism class="code" language="java">{{ test.value }} </prism>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import LineChartVue from "./components/LineChart.vue";
import text1 from "raw-loader!../../server/src/main/java/dev/findfirst/CryptoProjectFinal/service/BabyStepGiaintStep.java";
import "prismjs/components/prism-java";
import Prism from "vue-prism-component";
import axios from "axios";

export default {
  name: "App",
  data() {
    return {
      normalizedSpeed: 0,
      memory: 0,
      stats: {},
      test: { name: "text1", value: text1 },
    };
  },
  components: {
    LineChartVue,
    Prism,
  },
  mounted() {
    axios.get("http://localhost:9000/sys/stats").then((response) => {
      this.stats = response ? response.data : null;
      this.normalizedSpeed = response.maxFreq / 1000000000;
    });
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

.sysStat {
  text-align: left !important;
  background-color: #6c757d;
  border-radius: 0 !important;
}

.banner {
  background: #296bd3;
}

.nopadding {
  padding: 0 !important;
  margin: 0 !important;
  margin-top: 0px !important;
}

.code {
  height: -webkit-calc(100vh - 500px);
  height: -moz-calc(100vh - 500px);
  height: calc(100vh - 500px);
}

.codeSel {
  margin-top: 0px;
  padding-top: 0px;
  text-align: right;
  position: relative;
  right: 15px;
  top: 46px;
}

.codeSel2 {
  margin-top: 0px;
  padding-top: 0px;
  text-align: right;
  position: relative;
  top: -46px;
}
</style>

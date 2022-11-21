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
            <li class="list-group-item">CPU Speed: {{ cpuSpeed() }}GHz</li>
            <li class="list-group-item">Cores: {{ stats.logicalProcessorCount }}</li>
            <li class="list-group-item">{{ sysMem() }}</li>
          </ul>
        </div>
      </div>
      <div class="col-10 codeSel2">
        <div class="codeSel">
          <div class="btn-group" data-toggle="buttons" role="group">
            <button
              @click="codeSelection(0)"
              type="button"
              data-bs-toggle="button"
              class="btn btn-secondary active"
              :aria-pressed="buttons[0].state"
            >
              Brute Force
            </button>
            <button
              @click="codeSelection(1)"
              type="button"
              data-bs-toggle="button"
              class="btn btn-secondary"
              :aria-pressed="buttons[1].state"
            >
              Baby Step Gaint Step
            </button>
            <button
              @click="codeSelection(2)"
              type="button"
              data-bs-toggle="button"
              class="btn btn-secondary"
              :aria-pressed="buttons[2].state"
            >
              Pollard's Rho Method
            </button>
          </div>
        </div>
        <div class="code">
          <pre><code class="language-java">{{selected}}</code></pre>
        </div>
        <!-- <prism class="code" language="java">{{ selected }} </prism>
        <div>{{ test }}</div> -->
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import LineChartVue from "./components/LineChart.vue";
import dhBruteForce from "raw-loader!../../server/src/main/java/dev/findfirst/CryptoProjectFinal/crypto/diffiehellman/DiffieHellmanBruteForce.java";
import babyStepGaintStep from "raw-loader!../../server/src/main/java/dev/findfirst/CryptoProjectFinal/crypto/diffiehellman/BabyStepGiaintStep.java";
import pollardRho from "raw-loader!../../server/src/main/java/dev/findfirst/CryptoProjectFinal/crypto/PollardRho.java";
import "prismjs/components/prism-java";
import Prism from "prismjs";
// import "prismjs/themes/prism.css"; // you can change
import axios from "axios";
export default {
  name: "App",
  data() {
    return {
      normalizedSpeed: 0,
      memory: 0,
      stats: {},
      test: "cool",
      dhBruteForce: { name: "bruteForce", value: dhBruteForce },
      babystep: { name: "babystep", value: babyStepGaintStep },
      pollardRho: { name: "pollardRho", value: pollardRho },
      selected: dhBruteForce,
      buttons: [
        { caption: "Brute Force", state: true },
        { caption: "Baby Step Gaint Step", state: false },
        { caption: "Pollard's Rho Method", state: false },
      ],
    };
  },
  computed: {},
  methods: {
    sysMem() {
      return Math.round((this.stats.memory / 1000000000) * 100) / 100;
    },
    cpuSpeed() {
      return this.stats.maxFreq / 1000000000;
    },
    codeSelection(buttonIdx) {
      console.info(buttonIdx);
      console.log(this.buttons[buttonIdx].state);
      this.buttons[buttonIdx].state = !this.buttons[buttonIdx].state;
      switch (buttonIdx) {
        case 0:
          this.selected = this.dhBruteForce.value;
          Prism.highlightAll(); // highlight your code on moun
          break;
        case 1:
          this.selected = this.babystep.value;
          Prism.highlightAll(); // highlight your code on moun
          break;
        case 2:
          this.selected = this.pollardRho.value;
          break;
      }
      setTimeout(function () {
        Prism.highlightAll(); // highlight your code on moun
      }, 500);
    },
  },
  components: {
    LineChartVue,
    Prism,
  },
  watch: {
    selected() {
      console.info("here");
    },
  },
  mounted() {
    axios.get("http://localhost:9000/sys/stats").then((response) => {
      this.stats = response ? response.data : null;
      this.normalizedSpeed = response.maxFreq / 1000000000;
    });
    // window.Prism = window.Prism || {};
    // window.Prism.manual = true;
    // Prism.highlightAll(); // highlight your code on moun
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

<template>
  <NavBar />
  <div class="container-fluid">
    <div class="row">
      <div class="col-2 nopadding">
        <AlgorithmSelectVue :alg="selectedAlg" @alg-select="algSelect" />
        <SystemStatsVue :stats="this.stats" />
      </div>
      <div class="col-10 .chart-container">
        <LineChartVue :chartData="chartData" />
      </div>
    </div>
    <div class="row flex-grow-1">
      <div class="col-2 nopadding">
        <DhKeyGeneratorVue
          v-if="this.selectedAlg == 'diffieAlg'"
          :init-alpha="alpha"
          :init-bit-size="bitsize"
        />
        <RsaKeyGeneratorVue
          v-if="this.selectedAlg == 'rsaAlg'"
          :init-bit-size="bitsize"
        />
      </div>
      <div class="col-10 codeSel2">
        <div class="codeSel">
          <div class="btn-group" data-toggle="buttons" role="group">
            <div class="btn-group" v-for="button in buttons" :key="button.id">
              <AlgorithmButton @codeselect="codeSelection" :button="button" />
            </div>
          </div>
        </div>
        <pre class="code"><code class="code language-java">{{selectedCode}}</code></pre>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import $ from "jquery";
import AlgorithmButton from "./components/AlgorithmButton.vue";
import AlgorithmSelectVue from "./components/AlgorithmSelect.vue";
import api from "./Api";
import babyStepGaintStep from "raw-loader!./assets/BabyStepGiantStep.java";
import dhBruteForce from "raw-loader!./assets/DiffieHellmanBruteForce.java";
import DhKeyGeneratorVue from "./components/DhKeyGenerator.vue";
import LineChartVue from "./components/LineChart.vue";
import NavBar from "./components/NavBar.vue";
import pollardRho from "raw-loader!./assets/PollardRho.java";
import "prismjs/components/prism-java";
import Prism from "prismjs";
import RsaKeyGeneratorVue from "./components/RsaKeyGenerator.vue";
import SystemStatsVue from "./components/SystemStats.vue";
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
      selectedCode: dhBruteForce,
      selectedAlg: "diffieAlg",
      p: "",
      e: "",
      d: "",
      alpha: 2,
      bitsize: 16,
      buttons: [
        { id: "btnForce", idx: 0, caption: "Brute Force", state: true },
        { id: "btnBaby", idx: 1, caption: "Baby Step Gaint Step", state: false },
        { id: "btnPollard", idx: 2, caption: "Pollard's Rho method", state: false },
      ],
      chartData: {},
    };
  },
  computed: {},
  methods: {
    algSelect(algType) {
      this.selectedAlg = algType;
    },
    codeSelection(buttonIdx) {
      console.info(buttonIdx);
      console.log(this.buttons[buttonIdx].state);
      this.buttons[0].state = false;
      this.buttons[1].state = false;
      this.buttons[2].state = false;
      this.buttons[buttonIdx].state = !this.buttons[buttonIdx].state;
      switch (buttonIdx) {
        case 0:
          this.selectedCode = this.dhBruteForce.value;
          break;
        case 1:
          this.selectedCode = this.babystep.value;
          break;
        case 2:
          this.selectedCode = this.pollardRho.value;
          break;
      }
      $("#" + this.buttons[buttonIdx].id).addClass("active");
      // Need to give prism just a little bit of time to let the code load.
      setTimeout(function () {
        Prism.highlightAll();
      }, 10);
    },
    setBabyStepData() {
      let idx = 0;
      for (let i = 4; i <= 32; i += 4) {
        this.buildData("dh/babyStep", 1, i, idx);
        idx++;
      }
    },
    setBruteForce() {
      let idx = 0;
      for (let i = 4; i <= 20; i += 4) {
        this.buildData("dh/bf", 0, i, idx);
        idx++;
      }
    },
    setPollardRho() {
      let idx = 0;
      for (let i = 4; i <= 32; i += 4) {
        this.buildData("dh/pr", 2, i, idx);
        idx++;
      }
    },
    buildData(req, ds, i, idx) {
      const me = this;
      setTimeout(function () {
        api.runAlgorithm(req, i).then((response) => {
          let time = response ? response.data : null;
          console.log(idx);
          if (time) me.chartData.datasets[ds].data[idx] = time;
          console.log(time);
        });
      }, i * 250);
    },
  },
  components: {
    AlgorithmButton,
    AlgorithmSelectVue,
    DhKeyGeneratorVue,
    LineChartVue,
    NavBar,
    Prism,
    RsaKeyGeneratorVue,
    SystemStatsVue,
  },
  watch: {},
  mounted() {
    api.getStats().then((response) => {
      this.stats = response ? response.data : null;
      this.normalizedSpeed = response.maxFreq / 1000000000;
    });
    // set bruteforce to be active by default
    $("#" + this.buttons[0].id).addClass("active");
    window.Prism = window.Prism || {};
    window.Prism.manual = true;
    Prism.highlightAll(); // highlight your code on moun
    this.chartData = {
      labels: ["4", "8", "12", "16", "20", "24", "28", "32"],
      datasets: [
        {
          label: "Brute Force",
          data: [],
        },
        {
          label: "Baby Step Giant Step",
          data: [],
        },
        {
          label: "Pollard's Rho",
          data: [],
        },
      ],
    };
    this.setBabyStepData();
    this.setBruteForce();
    this.setPollardRho();
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
  overflow: hidden;
}

.chart-container {
  position: relative;
  margin: auto;
  height: 100%;
  width: 100%;
}

.nopadding {
  padding: 0 !important;
  margin: 0 !important;
  margin-top: 0px !important;
}

.code {
  height: -webkit-calc(100vh - 500px);
  height: -moz-calc(100vh - 500px);
  height: calc(100vh - 480px);
}

.codeSel {
  margin-top: 0px;
  margin-right: 10px;
  padding-top: 0px;
  text-align: right;
  position: relative;
  top: 46px;
}

.codeSel2 {
  margin-top: 0px;
  padding-top: 0px;
  text-align: right;
  position: relative;
  top: -46px;
}

.list-group-item.active {
  z-index: 2;
  color: whitesmoke;
  background-color: #6c757d;
  border-color: #6c757d;
}
.btn-outline-secondary.active {
  z-index: 2;
  color: #fff;
  background-color: #6c757d;
  border-color: #6c757d;
}

.btn-check:checked + .btn,
.btn.active,
.btn.show,
.btn:first-child:active,
:not(.btn-check) + .btn:active {
  color: whitesmoke;
  background-color: #6c757d;
  border-color: #6c757d;
}

::-webkit-scrollbar {
  width: 10px;
  color: #6c757d;
}

::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 3px #6c757d;
  border-radius: 19px;
}

::-webkit-scrollbar-thumb {
  border-radius: 19px;
  -webkit-box-shadow: inset 0 0 3px #6c757d;
  background: #296bd3;
  scrollbar-color: rebeccapurple green;
}
</style>

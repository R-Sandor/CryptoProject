<template>
  <div class="chart-container">
    <Line
      :chart-id="chartId"
      :chart-options="chartOptions"
      :width="width"
      :height="height"
      :styles="styles"
      :plugins="plugins"
      :data-id-key="datasetIdKey"
      :chart-data="chartData"
    />
  </div>
</template>
<script>
import { Line } from "vue-chartjs";
import { Colors } from "chart.js";

import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  PointElement,
  CategoryScale,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  PointElement,
  CategoryScale,
  Colors
);

export default {
  name: "LineChart",
  components: {
    Line,
  },
  props: {
    chartData: {},
    chartId: {
      type: String,
      default: "line-chart",
    },
    datasetIdKey: {
      type: String,
      default: "label",
    },
    width: {
      type: Number,
      default: 400,
    },
    height: {
      type: Number,
      default: 400,
    },
    cssClasses: {
      default: "",
      type: String,
    },
    styles: {
      type: Object,
      default: () => {},
    },
    plugins: {
      type: Object,
      default: () => {},
    },
    title: {
      default: "Diffie-Hellman",
      type: String,
    },
  },
  data() {
    return {
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            title: {
              display: true,
              text: "Bit Length",
            },
          },
          y: {
            title: {
              display: true,
              text: "Time (milliseconds)",
            },
          },
        },
        plugins: {
          title: {
            display: true,
            text: this.title,
          },
        },
      },
    };
  },
  methods: {
    mounted() {
      this.addPlugin(Colors);
    },
  },
};
</script>

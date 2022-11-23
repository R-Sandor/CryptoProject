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
  CategoryScale
);

export default {
  name: "LineChart",
  components: {
    Line,
  },
  props: {
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
  },
  data() {
    return {
      chartData: {
        labels: ["4", "8", "12", "16", "20", "24", "28", "32"],
        datasets: [
          {
            data: [300, 700, 450, 750, 450],
          },
        ],
      },
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
              text: "Time",
            },
          },
        },
      },
    };
  },
  methods: {},
};
</script>

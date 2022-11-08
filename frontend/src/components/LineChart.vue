<template>
  <div>
    <Line id="LineChart"> </Line>
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
  Plugin,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  LineElement,
  Plugin,
  PointElement,
  Legend,
  CategoryScale,
  LinearScale
);

export default {
  components: {
    Line,
  },
  name: "LineChart",
  props: {
    msg: String,
  },
};
</script>

<template>
  <div class="card sysStat">
    <div class="card-header">System Statistics</div>
    <ul class="list-group list-group-flush" style="text-align: left">
      <li class="list-group-item">CPU Speed: {{ cpuSpeed() }}GHz</li>
      <li class="list-group-item">Cores: {{ stats.logicalProcessorCount }}</li>
      <li class="list-group-item">Memory: {{ sysMem() }}Gb</li>
    </ul>
  </div>
</template>
<script>
export default {
  props: ["stats"],
  methods: {
    sysMem() {
      return Math.round((this.stats.memory / 1000000000) * 100) / 100;
    },
    cpuSpeed() {
      return Math.round((this.stats.maxFreq / 1000000000) * 100) / 100;
    },
  },
};
</script>
<style>
.sysStat {
  text-align: left !important;
  /* background-color: #6c757d; */
  border-radius: 0 !important;
  margin-top: 25px;
}
</style>

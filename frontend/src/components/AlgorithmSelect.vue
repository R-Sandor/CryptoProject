<template>
  <div class="list-group algselect">
    <a
      @click="$emit('algSelect', 'diffieAlg')"
      id="diffieAlg"
      class="list-group-item list-group-item-action active"
      >Diffie-Hellman</a
    >
    <a
      id="rsaAlg"
      @click="$emit('algSelect', 'rsaAlg')"
      class="list-group-item list-group-item-action"
    >
      RSA
    </a>
    <!-- <a
      id="eccAlg"
      @click="$emit('algSelect', 'ecc')"
      class="list-group-item list-group-item-action disabled"
    >
      Elliptic Curve</a
    > -->
  </div>
</template>
<script>
import $ from "jquery";
export default {
  props: ["alg"],
  watch: {
    alg: function (newState, oldState) {
      if (newState != oldState) {
        $("#" + oldState).removeClass("active");
        $("#" + newState).addClass("active");
      }
    },
  },
};
</script>
<style>
.algselect {
  /* padding-left: 0; */
  margin-left: 0;
  padding-left: 0;
  border-radius: 0 !important;
}
</style>

<template>
  <button
    :id="button.id"
    @click="$emit('algselect', button.idx)"
    type="button"
    data-bs-toggle="button"
    class="btn btn-outline-secondary"
    style="border-radius: 0 !important"
    aria-pressed="false"
  >
    {{ button.caption }}
  </button>
</template>

<script>
import $ from "jquery";
export default {
  props: ["button"],
  watch: {
    "button.state": function (newState, oldState) {
      if (!newState) {
        $("#" + this.button.id).removeClass("active");
      }
      console.log(newState + " " + oldState);
    },
  },
};
</script>
<style></style>

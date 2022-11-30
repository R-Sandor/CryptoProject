<template>
  <div class="keyGen">
    Key Generator - Diffie-Hellman
    <hr />
    <form @submit.prevent>
      <div class="form-group row">
        <label for="alpha" class="col-sm-2 col-form-label">&alpha;</label>
        <div class="col-sm-5">
          <input v-model="alpha" type="number" class="form-control" id="alpha" />
        </div>
        <label for="p" class="col-sm-1 form-label">p</label>
        <div class="col-sm-3 nopadding">
          <textarea
            readonly
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            :title="p"
            v-model="p"
            class="form-control"
            id="p"
            rows="1"
          ></textarea>
        </div>
      </div>
      <div class="row">
        <label for="bitlength" class="col-sm-2 col-form-label">Bitsize</label>
        <div class="col-sm-5">
          <input
            v-model="bitsize"
            type="number"
            class="form-control"
            id="bitLength"
            placeholder="4-2048"
          />
        </div>
        <label for="e" class="col-sm-1 form-label">&beta;</label>
        <div class="col-sm-3 nopadding">
          <textarea
            v-model="e"
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            :title="e"
            readonly
            class="form-control"
            id="p"
            rows="1"
          ></textarea>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-7"></div>
        <div class="col-sm-1">d</div>
        <div class="col-sm-3 nopadding">
          <textarea
            v-model="d"
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            :title="d"
            readonly
            class="form-control"
            id="d"
            rows="1"
          ></textarea>
        </div>
      </div>
      <div class="row nopadding">
        <button @click="generateKey()" class="btn btn-primary mb-3">Generate Key</button>
      </div>
    </form>
  </div>
</template>
<script>
import api from "../Api";
export default {
  props: ["initAlpha", "initBitSize"],
  data() {
    return {
      alpha: this.initAlpha,
      bitsize: this.initBitSize,
      p: "",
      d: "",
      e: "",
    };
  },
  methods: {
    generateKey() {
      api.generateDhKey(this.alpha, this.bitsize).then((response) => {
        if (response.status == 200) {
          let key = response.data;
          this.p = key.p;
          this.e = key.kpub;
          this.d = key.kpriv;

          console.log(key);
        }
      });
    },
  },
};
</script>

<style>
.nopadding {
  padding: 0 !important;
  margin: 0 !important;
  margin-top: 0px !important;
}

.keyGen {
  border-radius: 3px;
  border: 1px solid #2c3e50;
  background-color: whitesmoke;
  margin-top: 25px;
}
</style>

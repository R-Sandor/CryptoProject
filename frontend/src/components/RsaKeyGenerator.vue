<template>
  <div class="keyGen">
    Key Generator - RSA
    <hr />
    <form @submit.prevent>
      <div class="form-group row"></div>
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
        <label for="p" class="col-sm-1 form-label">p</label>
        <div class="col-sm-3 nopadding">
          <textarea
            v-model="p"
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            :title="p"
            readonly
            class="form-control"
            id="p"
            rows="1"
          ></textarea>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-7"></div>
        <div class="col-sm-1">q</div>
        <div class="col-sm-3 nopadding">
          <textarea
            v-model="q"
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            :title="q"
            readonly
            class="form-control"
            id="q"
            rows="1"
          ></textarea>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-7"></div>
        <div class="col-sm-1">n</div>
        <div class="col-sm-3 nopadding">
          <textarea
            v-model="n"
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            :title="n"
            readonly
            class="form-control"
            id="n"
            rows="1"
          ></textarea>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-7"></div>
        <div class="col-sm-1">e</div>
        <div class="col-sm-3 nopadding">
          <textarea
            v-model="e"
            data-bs-toggle="tooltip"
            data-bs-placement="bottom"
            :title="e"
            readonly
            class="form-control"
            id="e"
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
  props: ["initBitSize"],
  data() {
    return {
      bitsize: this.initBitSize,
      p: "",
      q: "",
      n: "",
      e: "",
      d: "",
    };
  },
  methods: {
    generateKey() {
      api.generateRsaKey(this.bitsize).then((response) => {
        if (response.status == 200) {
          let key = response.data;
          this.p = key.p;
          this.q = key.q;
          this.n = key.n;
          this.e = key.e;
          this.d = key.d;
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

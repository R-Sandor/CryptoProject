import axios from "axios";

const SERVER_URL = "api";

const instance = axios.create({
  baseURL: SERVER_URL,
  timeout: 90000,
});

export default {
  async execute(method, resource, data, config) {
    return instance({
      method: method,
      url: resource,
      data,
      ...config,
    });
  },

  runAlgorithm(req, i) {
    return this.execute("GET", req + "/" + i);
  },

  getStats() {
    return this.execute("GET", "sys/stats");
  },

  generateDhKey(alpha, bitsize) {
    return this.execute("GET", "keygen/dh/" + alpha + "/" + bitsize);
  },

  generateRsaKey(bitsize) {
    return this.execute("GET", "keygen/rsa/" + bitsize);
  },
};

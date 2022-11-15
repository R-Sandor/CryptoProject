package dev.findfirst.CryptoProjectFinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alg")
public class AlgorithmController {
  @RequestMapping("/")
  String get() {
    // mapped to hostname:port/home/
    return "Hello from get";
  }
}

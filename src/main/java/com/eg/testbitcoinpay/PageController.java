package com.eg.testbitcoinpay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/pay")
    public String toPayPage(Map<String, String> map) {
        map.put("hello", "afweyes");
        return "pay";
    }
}

package com.eg.testbitcoinpay;

import com.eg.testbitcoinpay.bitcoin.transaction.BitcoinTransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/page")
public class PageController {
    @Resource
    private BitcoinTransactionService bitcoinTransactionService;

    @RequestMapping("/pay")
    public String toPayPage(Map<String, String> map) {
        map.put("hello", "afweyes");
        return "pay";
    }
}

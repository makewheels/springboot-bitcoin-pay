package com.eg.testbitcoinpay;

import com.eg.testbitcoinpay.bitcoin.transaction.BitcoinTransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {
    @Resource
    private BitcoinTransactionService bitcoinTransactionService;

    @RequestMapping("/toPayPage")
    public String toPayPage(Map<String, Object> map) {
        map.put("legalTenderAmount", 3);
        map.put("legalTenderCurrency", "USD");
        map.put("payMethod", "BTC");
        return "submit_order";
    }

    @RequestMapping("/submitOrder")
    public String submitOrder(
            Map<String, Object> map,
            @RequestParam("legalTenderAmount") double legalTenderAmount,
            @RequestParam("legalTenderCurrency") String legalTenderCurrency,
            @RequestParam("payMethod") String payMethod
    ) {
        map.put("bitcoinAmount", 0);
        return "wait_bitcoin_pay";
    }
}

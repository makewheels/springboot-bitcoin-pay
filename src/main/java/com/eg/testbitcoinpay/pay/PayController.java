package com.eg.testbitcoinpay.pay;

import com.eg.testbitcoinpay.order.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {
    @Resource
    private PayService payService;

    /**
     * 请求支付
     *
     * @param map
     * @return
     */
    @RequestMapping("/toPayPage")
    public String toPayPage(Map<String, Object> map) {
        map.put("legalTenderAmount", 3);
        map.put("legalTenderCurrency", "USD");
        map.put("payMethod1", "BTC");
        return "submit_order";
    }

    /**
     * 提交订单
     *
     * @param map
     * @param legalTenderAmount
     * @param legalTenderCurrency
     * @param payMethod
     * @return
     */
    @RequestMapping("/submitOrder")
    public String submitOrder(
            Map<String, Object> map,
            @RequestParam("legalTenderAmount") double legalTenderAmount,
            @RequestParam("legalTenderCurrency") String legalTenderCurrency,
            @RequestParam("payMethod") String payMethod
    ) {
        //创建订单
        Order order = payService.createOrder(legalTenderAmount, legalTenderCurrency, payMethod);

        map.put("bitcoinAmount", 0);
        return "wait_bitcoin_pay";
    }
}

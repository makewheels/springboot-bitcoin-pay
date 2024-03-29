package com.eg.testbitcoinpay.pay;

import com.alibaba.fastjson.JSON;
import com.eg.testbitcoinpay.bitcoin.address.BitcoinAddress;
import com.eg.testbitcoinpay.bitcoin.address.BitcoinAddressService;
import com.eg.testbitcoinpay.bitcoin.transaction.BitcoinTransactionService;
import com.eg.testbitcoinpay.bitcoin.util.BitcoinUtil;
import com.eg.testbitcoinpay.order.PayOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {
    @Resource
    private PayService payService;
    @Resource
    private BitcoinAddressService bitcoinAddressService;
    @Resource
    private BitcoinTransactionService bitcoinTransactionService;

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
            @RequestParam("legalTenderAmount") BigDecimal legalTenderAmount,
            @RequestParam("legalTenderCurrency") String legalTenderCurrency,
            @RequestParam("payMethod") String payMethod
    ) {
        //创建订单
        PayOrder payOrder = payService.createPayOrder(legalTenderAmount, legalTenderCurrency, payMethod);
        map.put("payOrderUuid", payOrder.getUuid());
        BigDecimal bitcoinAmount = BitcoinUtil.satoshiToBtc(payOrder.getBitcoinAmount());
        map.put("bitcoinAmount", bitcoinAmount);

        //创建比特币地址
        BitcoinAddress bitcoinAddress = payService.createBitcoinAddress(payOrder);
        map.put("bitcoinAddress", bitcoinAddress.getAddress());
        map.put("bitcoinQrCode", "bitcoin:" + bitcoinAddress.getAddress().toUpperCase()
                + "?amount=" + bitcoinAmount);
        return "wait_bitcoin_pay";
    }

    /**
     * 前端查询订单支付信息
     *
     * @param payOrderUuid
     * @return
     */
    @RequestMapping("/queryPayOrder")
    @ResponseBody
    public String queryPayOrder(String payOrderUuid) {
        PayOrderInfoResponse payOrderInfoResponse = payService.queryPayOrder(payOrderUuid);
        String json = JSON.toJSONString(payOrderInfoResponse);
        System.out.println(json);
        return json;
    }

}

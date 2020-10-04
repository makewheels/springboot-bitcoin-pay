package com.eg.testbitcoinpay.pay;

import com.eg.testbitcoinpay.huobi.QueryPriceUtil;
import com.eg.testbitcoinpay.order.PayOrder;
import com.eg.testbitcoinpay.order.PayOrderRepository;
import com.eg.testbitcoinpay.util.Constants;
import com.eg.testbitcoinpay.util.UuidUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class PayService {
    @Resource
    private PayOrderRepository payOrderRepository;

    /**
     * 创建订单
     *
     * @param legalTenderAmount
     * @param legalTenderCurrency
     * @param payMethod
     * @return
     */
    public PayOrder createPayOrder(BigDecimal legalTenderAmount, String legalTenderCurrency, String payMethod) {
        PayOrder payOrder = new PayOrder();
        payOrder.setUuid(UuidUtil.getUuid());
        long creatTime = System.currentTimeMillis();
        payOrder.setCreateTime(new Date(creatTime));
        payOrder.setName("bitcoin-pay-order_" + payOrder.getCreateTime());
        payOrder.setInvalidTime(new Date(creatTime + Constants.ORDER_VALID_TIME_LENGTH));
        payOrder.setValidTimeLength(Constants.ORDER_VALID_TIME_LENGTH);
        payOrder.setPayMethod(payMethod);
        payOrder.setLegalTenderCurrency(legalTenderCurrency);
        //转化为最小单位
        if (legalTenderCurrency.equals("USD")) {
            payOrder.setLegalTenderAmount(UsdUtil.dollarToCents(legalTenderAmount));
        } else if (legalTenderCurrency.equals("CNY")) {
            payOrder.setLegalTenderAmount((legalTenderAmount.multiply(new BigDecimal(100)).intValue()));
        }
        payOrder.setDigitalCurrency(payMethod);
        //获取币价
        BigDecimal bitcoinPrice = QueryPriceUtil.getBitcoinPrice();
        payOrder.setBitcoinPriceUsd(UsdUtil.dollarToCents(bitcoinPrice));
        System.out.println("got BTC price in USDT from huobi: " + bitcoinPrice);
        //折算出比特币数量
        //计算方法，美元数量除以比特币价格
        long satoshi = BitcoinUtil.btcToSatoshi(legalTenderAmount.divide(bitcoinPrice, 9, BigDecimal.ROUND_UP));
        payOrder.setBitcoinAmount(satoshi);

        System.out.println(payOrder);
        payOrderRepository.save(payOrder);
        return payOrder;
    }
}

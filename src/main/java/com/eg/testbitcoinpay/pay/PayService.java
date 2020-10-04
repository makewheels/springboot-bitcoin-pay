package com.eg.testbitcoinpay.pay;

import com.eg.testbitcoinpay.huobi.QueryPriceUtil;
import com.eg.testbitcoinpay.order.Order;
import com.eg.testbitcoinpay.order.OrderRepository;
import com.eg.testbitcoinpay.util.Constants;
import com.eg.testbitcoinpay.util.UuidUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class PayService {
    @Resource
    private OrderRepository orderRepository;

    /**
     * 创建订单
     *
     * @param legalTenderAmount
     * @param legalTenderCurrency
     * @param payMethod
     * @return
     */
    public Order createOrder(BigDecimal legalTenderAmount, String legalTenderCurrency, String payMethod) {
        Order order = new Order();
        order.setUuid(UuidUtil.getUuid());
        long creatTime = System.currentTimeMillis();
        order.setCreateTime(new Date(creatTime));
        order.setName("bitcoin-pay-order_" + order.getCreateTime());
        order.setInvalidTime(new Date(creatTime + Constants.ORDER_VALID_TIME_LENGTH));
        order.setValidTimeLength(Constants.ORDER_VALID_TIME_LENGTH);
        order.setPayMethod(payMethod);
        order.setLegalTenderCurrency(legalTenderCurrency);
        //转化为最小单位
        if (legalTenderCurrency.equals("USD")) {
            order.setLegalTenderAmount(UsdUtil.dollarToCents(legalTenderAmount));
        } else if (legalTenderCurrency.equals("CNY")) {
            order.setLegalTenderAmount((legalTenderAmount.multiply(new BigDecimal(100)).intValue()));
        }
        order.setDigitalCurrency(payMethod);
        //获取币价
        BigDecimal bitcoinPrice = QueryPriceUtil.getBitcoinPrice();
        order.setBitcoinPriceUsd(UsdUtil.dollarToCents(bitcoinPrice));
        System.out.println("got BTC price in USDT from huobi: " + bitcoinPrice);
        //折算出比特币数量
        //计算方法，美元数量除以比特币价格
        long satoshi = BtcUtil.btcToSatoshi(legalTenderAmount.divide(bitcoinPrice, 9, BigDecimal.ROUND_UP));
        order.setBitcoinAmount(satoshi);

        System.out.println(order);
        orderRepository.save(order);
        return order;
    }
}

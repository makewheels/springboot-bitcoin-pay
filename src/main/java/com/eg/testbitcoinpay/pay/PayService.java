package com.eg.testbitcoinpay.pay;

import com.eg.testbitcoinpay.huobi.QueryPriceUtil;
import com.eg.testbitcoinpay.order.Order;
import com.eg.testbitcoinpay.order.OrderRepository;
import com.eg.testbitcoinpay.util.Constants;
import com.eg.testbitcoinpay.util.UuidUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

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
    public Order createOrder(double legalTenderAmount, String legalTenderCurrency, String payMethod) {
        Order order = new Order();
        order.setUuid(UuidUtil.getUuid());
        order.setName("bitcoin-pay-order-" + UuidUtil.getUuidWithoutHyphen());
        long creatTime = System.currentTimeMillis();
        order.setCreateTime(new Timestamp(creatTime));
        order.setInvalidTime(new Timestamp(creatTime + Constants.ORDER_VALID_TIME_LENGTH));
        order.setValidTimeLength(Constants.ORDER_VALID_TIME_LENGTH);
        order.setPayMethod(payMethod);
        order.setLegalTenderCurrency(legalTenderCurrency);
        //转化为最小单位
        if (legalTenderCurrency.equals("USD")) {
            order.setLegalTenderAmount(UsdUtil.dollarToCents(legalTenderAmount));
        } else if (legalTenderCurrency.equals("CNY")) {
            order.setLegalTenderAmount((int) (legalTenderAmount * 100));
        }
        order.setDigitalCurrency(payMethod);
        //获取币价
        double bitcoinPrice = QueryPriceUtil.getBitcoinPrice();
        order.setBitcoinPriceUsd(UsdUtil.dollarToCents(bitcoinPrice));
        //折算出比特币数量

        System.out.println(order);
        orderRepository.save(order);
        return order;
    }
}

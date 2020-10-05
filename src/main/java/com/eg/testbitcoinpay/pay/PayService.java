package com.eg.testbitcoinpay.pay;

import com.eg.testbitcoinpay.bitcoin.address.BitcoinAddress;
import com.eg.testbitcoinpay.bitcoin.address.BitcoinAddressService;
import com.eg.testbitcoinpay.bitcoin.jsonrpc.BitcoinJsonRpcService;
import com.eg.testbitcoinpay.bitcoin.transaction.BitcoinTransactionService;
import com.eg.testbitcoinpay.bitcoin.util.BitcoinUtil;
import com.eg.testbitcoinpay.huobi.QueryPriceUtil;
import com.eg.testbitcoinpay.order.PayOrder;
import com.eg.testbitcoinpay.order.PayOrderRepository;
import com.eg.testbitcoinpay.pay.util.UsdUtil;
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
    @Resource
    private BitcoinAddressService bitcoinAddressService;
    @Resource
    private BitcoinTransactionService bitcoinTransactionService;

    @Resource
    private BitcoinJsonRpcService bitcoinJsonRpcService;

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
        //折算出比特币数量，计算方法：美元数量除以比特币USDT价格
        long satoshi = BitcoinUtil.btcToSatoshi(
                legalTenderAmount.divide(bitcoinPrice, 9, BigDecimal.ROUND_UP));
        payOrder.setBitcoinAmount(satoshi);
        payOrderRepository.save(payOrder);
        return payOrder;
    }

    /**
     * 创建比特币地址
     */
    public BitcoinAddress createBitcoinAddress(PayOrder payOrder) {
        BitcoinAddress bitcoinAddress = bitcoinAddressService.createBitcoinAddress(UuidUtil.getUuid());
        bitcoinAddress.setUuid(UuidUtil.getUuid());
        bitcoinAddress.setPayOrderId(payOrder.getId());
        //保存地址到数据库
        bitcoinAddressService.save(bitcoinAddress);
        return bitcoinAddress;
    }

    /**
     * 根据uuid查找订单
     *
     * @param payOrderUuid
     * @return
     */
    public PayOrder findPayOrderByUuid(String payOrderUuid) {
        return payOrderRepository.findByUuid(payOrderUuid);
    }

    /**
     * 根据订单查找比特币收款地址
     *
     * @param payOrder
     * @return
     */
    public BitcoinAddress findBitcoinAddressByPayOrder(PayOrder payOrder) {
        return bitcoinAddressService.findBitcoinAddressByPayOrderId(payOrder.getId());
    }

}

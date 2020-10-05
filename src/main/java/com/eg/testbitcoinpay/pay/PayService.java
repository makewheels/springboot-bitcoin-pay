package com.eg.testbitcoinpay.pay;

import com.eg.testbitcoinpay.bitcoin.address.BitcoinAddress;
import com.eg.testbitcoinpay.bitcoin.address.BitcoinAddressService;
import com.eg.testbitcoinpay.bitcoin.jsonrpc.BitcoinJsonRpcService;
import com.eg.testbitcoinpay.bitcoin.transaction.BitcoinTransaction;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * 查询订单
     *
     * @return
     */
    public PayOrderInfoResponse queryPayOrder(String payOrderUuid) {
        //根据uuid找出订单
        PayOrder payOrder = findPayOrderByUuid(payOrderUuid);
        //根据订单id找出比特币收款地址
        BitcoinAddress bitcoinAddress
                = bitcoinAddressService.findBitcoinAddressByPayOrderId(payOrder.getId());
        //返回给前端的订单状态
        PayOrderInfoResponse payOrderInfoResponse = new PayOrderInfoResponse();
        //根据比特币地址查询交易列表
        List<BitcoinTransaction> transactionListFromNet
                = bitcoinTransactionService.findBitcoinTransactionsByBitcoinAddressFromNet(bitcoinAddress);
        //把交易列表装载到TransactionResponse里
        List<TransactionResponse> transactionResponseList = new ArrayList<>();
        for (BitcoinTransaction bitcoinTransaction : transactionListFromNet) {
            TransactionResponse transactionResponse = new TransactionResponse();
            DecimalFormat decimalFormat = new DecimalFormat("#0.00000000");
            transactionResponse.setAmount(decimalFormat.format(
                    BitcoinUtil.satoshiToBtc(bitcoinTransaction.getSatoshi())));
            transactionResponse.setConfirmations(bitcoinTransaction.getConfirmations());
            transactionResponse.setTxid(bitcoinTransaction.getTxid());
            transactionResponse.setTimereceived(bitcoinTransaction.getTimereceived().getTime());
            transactionResponseList.add(transactionResponse);
        }
        payOrderInfoResponse.setTransactionResponseList(transactionResponseList);
        //更新交易到数据库
        bitcoinTransactionService.updateTransactionsToDatabase(transactionListFromNet);
        //计算收到的比特币总数
        long totalReceivedSatoshi = bitcoinTransactionService.getTotalReceivedSatoshi(transactionListFromNet);
        //返回给前端的总收币数，赋值
        DecimalFormat decimalFormat = new DecimalFormat("#0.00000000");
        payOrderInfoResponse.setTotalReceived(
                decimalFormat.format(BitcoinUtil.satoshiToBtc(totalReceivedSatoshi)));
        //更新订单对应的，比特币收款地址的，比特币总额
        Long bitcoinAddressSatoshi = bitcoinAddress.getSatoshi();
        if (bitcoinAddressSatoshi == null) {
            bitcoinAddressSatoshi = 0L;
        }
        if (bitcoinAddressSatoshi != totalReceivedSatoshi) {
            bitcoinAddress.setSatoshi(totalReceivedSatoshi);
            bitcoinAddressService.save(bitcoinAddress);
        }
        //看是否已经收到足够的satoshi
        //其实这里还可以做很多事情，订单状态修改，也要分确认数
        //还有就是支付时间
        if (totalReceivedSatoshi >= payOrder.getBitcoinAmount()) {
            payOrderInfoResponse.setPayOrderState("is ok 支付完成了，确认数还不知道");
        } else {
            payOrderInfoResponse.setPayOrderState("尚未支付~~~");
        }
        return payOrderInfoResponse;
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

}

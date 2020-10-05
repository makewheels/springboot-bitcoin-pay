package com.eg.testbitcoinpay.pay;

import lombok.Data;

import java.util.List;

/**
 * 返回给客户端的订单支付状态
 */
@Data
public class PayOrderInfoResponse {
    private String payOrderState;
    private String totalReceived;
    private List<TransactionResponse> transactionResponseList;

}

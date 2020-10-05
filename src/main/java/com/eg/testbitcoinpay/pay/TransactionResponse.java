package com.eg.testbitcoinpay.pay;

import lombok.Data;

/**
 * 返回给前端的交易信息
 */
@Data
public class TransactionResponse {
    private String amount;
    private long timereceived;
    private int confirmations;
    private String txid;
}

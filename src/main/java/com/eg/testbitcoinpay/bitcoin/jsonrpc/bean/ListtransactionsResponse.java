package com.eg.testbitcoinpay.bitcoin.jsonrpc.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ListtransactionsResponse {
    private String address;
    private String category;
    private BigDecimal amount;
    private String label;
    private int vout;
    private int confirmations;
    private String blockhash;
    private long blockheight;
    private int blockindex;
    private long blocktime;
    private String txid;
    private List<String> walletconflicts;
    private long time;
    private long timereceived;
    private String bip125_replaceable;
}

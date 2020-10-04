package com.eg.testbitcoinpay.huobi.markettrade;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DataItem {
    private long tradeId;
    private BigDecimal amount;
    private BigDecimal price;
    private String id;
    private long ts;
    private String direction;
}

package com.eg.testbitcoinpay.bitcoin.util;

import java.math.BigDecimal;

/**
 * 比特币工具类
 */
public class BitcoinUtil {
    public static final BigDecimal ONE_BTC_IN_SATOSHI = new BigDecimal(100000000);

    public static long btcToSatoshi(BigDecimal btc) {
        return btc.multiply(ONE_BTC_IN_SATOSHI).longValue();
    }

    public static BigDecimal satoshiToBtc(long satoshi) {
        return new BigDecimal(satoshi).divide(ONE_BTC_IN_SATOSHI, 9, BigDecimal.ROUND_HALF_DOWN);
    }
}

package com.eg.testbitcoinpay.pay;

/**
 * 比特币工具类
 */
public class BtcUtil {
    public static long btcToSatoshi(double btc) {
        return (long) (btc * 1000000000);
    }

    public static double satoshiToBtc(long satoshi) {
        return satoshi / 1000000000.0;
    }
}

package com.eg.testbitcoinpay.pay;

/**
 * USD美元工具类
 */
public class UsdUtil {
    /**
     * 美元转换为美分，整数存储到数据库中
     *
     * @param dollar
     * @return
     */
    public static int dollarToCents(double dollar) {
        return (int) (dollar * 100);
    }
}

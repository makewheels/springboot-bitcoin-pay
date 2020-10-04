package com.eg.testbitcoinpay.pay;

import java.math.BigDecimal;

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
    public static int dollarToCents(BigDecimal dollar) {
        return dollar.multiply(new BigDecimal(100)).intValue();
    }
}

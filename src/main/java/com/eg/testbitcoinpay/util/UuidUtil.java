package com.eg.testbitcoinpay.util;

import java.util.UUID;

public class UuidUtil {
    public static String getUuid() {
        return UUID.randomUUID().toString();
    }

    public static String getUuidWithoutHyphen() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

package com.eg.testbitcoinpay.bitcoin.jsonrpc;

import com.alibaba.fastjson.JSON;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class BitcoinJsonRpcService {

    private static JsonRpcHttpClient jsonRpcHttpClient;

    static {
        String url = "http://rpcuser:rpcpassword@127.0.0.1:18332/";
        Map<String, String> headers = new HashMap<String, String>(1);
        headers.put("Authorization",
                "Basic " + new String(new Base64().encode("rpcuser:rpcpassword".getBytes())));
        try {
            jsonRpcHttpClient = new JsonRpcHttpClient(new URL(url), headers);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取新的比特币地址
     *
     * @return
     */
    public String getnewaddress() {
        try {
            return jsonRpcHttpClient.invoke("getnewaddress", null, String.class);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    /**
     * 获取新的比特币地址
     *
     * @param label
     * @return
     */
    public String getnewaddress(String label) {
        try {
            return jsonRpcHttpClient.invoke(
                    "getnewaddress", new Object[]{label}, String.class);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    /**
     * 获取比特币地址信息
     *
     * @param address
     * @return
     */
    public GetaddressinfoResponse getaddressinfo(String address) {
        try {
            Map<String, String> map = jsonRpcHttpClient.invoke("getaddressinfo",
                    new Object[]{address}, Map.class);
            return JSON.parseObject(JSON.toJSONString(map), GetaddressinfoResponse.class);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    /**
     * dump私钥
     *
     * @param address
     * @return
     */
    public String dumpprivkey(String address) {
        try {
            return jsonRpcHttpClient.invoke("dumpprivkey",
                    new Object[]{address}, String.class);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

}

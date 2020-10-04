package com.eg.testbitcoinpay.bitcoin.jsonrpc.bean;

import lombok.Data;

import java.util.List;

@Data
public class GetaddressinfoResponse {
    private String hdmasterfingerprint;
    private String witnessProgram;
    private String address;
    private boolean ischange;
    private boolean ismine;
    private boolean iswatchonly;
    private String hdseedid;
    private boolean iswitness;
    private List<String> labels;
    private String scriptPubKey;
    private boolean solvable;
    private int witnessVersion;
    private boolean isscript;
    private String hdkeypath;
    private String desc;
    private String pubkey;
    private long timestamp;
}
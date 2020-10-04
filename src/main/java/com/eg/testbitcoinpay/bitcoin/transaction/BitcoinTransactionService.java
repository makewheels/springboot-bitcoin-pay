package com.eg.testbitcoinpay.bitcoin.transaction;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BitcoinTransactionService {
    @Resource
    private BitcoinTransactionRepository bitcoinTransactionRepository;

}

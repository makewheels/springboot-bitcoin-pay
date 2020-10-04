package com.eg.testbitcoinpay.bitcoin;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BitcoinAddressService {
    @Resource
    private BitcoinAddressRepository bitcoinAddressRepository;
}

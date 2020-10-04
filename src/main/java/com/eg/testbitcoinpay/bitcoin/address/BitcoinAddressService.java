package com.eg.testbitcoinpay.bitcoin.address;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BitcoinAddressService {
    @Resource
    private BitcoinAddressRepository bitcoinAddressRepository;
}

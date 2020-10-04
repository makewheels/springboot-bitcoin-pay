package com.eg.testbitcoinpay.order;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PayOrderService {
    @Resource
    private PayOrderRepository payOrderRepository;
}

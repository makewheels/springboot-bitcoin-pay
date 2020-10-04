package com.eg.testbitcoinpay.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PayOrderRepository extends JpaRepository<PayOrder, Integer> {
}

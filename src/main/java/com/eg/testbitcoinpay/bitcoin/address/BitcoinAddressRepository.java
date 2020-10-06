package com.eg.testbitcoinpay.bitcoin.address;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BitcoinAddressRepository extends JpaRepository<BitcoinAddress, Integer> {
    BitcoinAddress findByPayOrderId(int payOrderId);
}

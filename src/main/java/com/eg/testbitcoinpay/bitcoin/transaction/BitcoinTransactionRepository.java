package com.eg.testbitcoinpay.bitcoin.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BitcoinTransactionRepository extends JpaRepository<BitcoinTransaction, Integer> {
    List<BitcoinTransaction> findByReceiveAddress(String receiveAddress);

    BitcoinTransaction findByTxid(String txid);
}

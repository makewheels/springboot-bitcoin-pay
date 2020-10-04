package com.eg.testbitcoinpay.bitcoin.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BitcoinTransactionRepository extends JpaRepository<BitcoinTransaction, Integer> {

}

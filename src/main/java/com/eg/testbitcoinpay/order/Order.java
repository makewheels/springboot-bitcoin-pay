package com.eg.testbitcoinpay.order;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "order")
public class Order implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "invalid_time")
    private Date invalidTime;

    @Column(name = "valid_time_length")
    private Long validTimeLength;

    @Column(name = "pay_time")
    private java.sql.Timestamp payTime;

    @Column(name = "pay_method")
    private String payMethod;

    @Column(name = "legal_tender_currency")
    private String legalTenderCurrency;

    @Column(name = "legal_tender_amount")
    private Integer legalTenderAmount;

    @Column(name = "digital_currency")
    private String digitalCurrency;

    @Column(name = "bitcoin_amount")
    private Long bitcoinAmount;

    @Column(name = "bitcoin_price_usd")
    private Integer bitcoinPriceUsd;

}

package com.eg.testbitcoinpay.order;

import javax.persistence.*;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "create_time")
    private java.sql.Timestamp createTime;

    @Column(name = "invalid_time")
    private java.sql.Timestamp invalidTime;

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


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.sql.Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }

    public java.sql.Timestamp getInvalidTime() {
        return this.invalidTime;
    }

    public void setInvalidTime(java.sql.Timestamp invalidTime) {
        this.invalidTime = invalidTime;
    }

    public Long getValidTimeLength() {
        return this.validTimeLength;
    }

    public void setValidTimeLength(Long validTimeLength) {
        this.validTimeLength = validTimeLength;
    }

    public java.sql.Timestamp getPayTime() {
        return this.payTime;
    }

    public void setPayTime(java.sql.Timestamp payTime) {
        this.payTime = payTime;
    }

    public String getPayMethod() {
        return this.payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getLegalTenderCurrency() {
        return this.legalTenderCurrency;
    }

    public void setLegalTenderCurrency(String legalTenderCurrency) {
        this.legalTenderCurrency = legalTenderCurrency;
    }

    public Integer getLegalTenderAmount() {
        return this.legalTenderAmount;
    }

    public void setLegalTenderAmount(Integer legalTenderAmount) {
        this.legalTenderAmount = legalTenderAmount;
    }

    public String getDigitalCurrency() {
        return this.digitalCurrency;
    }

    public void setDigitalCurrency(String digitalCurrency) {
        this.digitalCurrency = digitalCurrency;
    }

    public Long getBitcoinAmount() {
        return this.bitcoinAmount;
    }

    public void setBitcoinAmount(Long bitcoinAmount) {
        this.bitcoinAmount = bitcoinAmount;
    }

    public Integer getBitcoinPriceUsd() {
        return this.bitcoinPriceUsd;
    }

    public void setBitcoinPriceUsd(Integer bitcoinPriceUsd) {
        this.bitcoinPriceUsd = bitcoinPriceUsd;
    }
}

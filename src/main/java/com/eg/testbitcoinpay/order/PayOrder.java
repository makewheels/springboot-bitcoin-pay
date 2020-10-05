package com.eg.testbitcoinpay.order;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "pay_order")
public class PayOrder implements Serializable {

    //订单创建，但没支付
    public static final int STATE_NOT_PAID = 0;
    //有付款，但是付款金额不够
    public static final int STATE_PAID_BUT_NOT_ENOUGH = 1;
    //付款够了，但是确认数不够6
    public static final int STATE_PAID_ENOUGH_BUT_NOT_CONFIRM = 2;
    //付款金额够了，确认数也够6了，支付已完成
    public static final int STATE_PAID_ENOUGH_AND_CONFIRMED = 3;

    //确认数量
    public static int CONFIRM_AMOUNT = 6;

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

    @Column(name = "state")
    private Integer state;

}

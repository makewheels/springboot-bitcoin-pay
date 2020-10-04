package com.eg.testbitcoinpay.bitcoin.transaction;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "bitcoin_transaction")
public class BitcoinTransaction implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "receive_address")
    private Integer receiveAddress;

    @Column(name = "satoshi")
    private Long satoshi;

    @Column(name = "timereceived")
    private Date timereceived;

    @Column(name = "confirmations")
    private Integer confirmations;

    @Column(name = "txid")
    private String txid;

}

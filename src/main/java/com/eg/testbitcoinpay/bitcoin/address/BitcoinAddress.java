package com.eg.testbitcoinpay.bitcoin.address;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "bitcoin_address")
public class BitcoinAddress implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "pay_order_id")
    private Integer payOrderId;

    @Column(name = "address")
    private String address;

    @Column(name = "private_key")
    private String privateKey;

    @Column(name = "public_key")
    private String publicKey;

    @Column(name = "label")
    private String label;

    @Column(name = "satoshi")
    private Long satoshi;

    @Column(name = "timestamp")
    private Long timestamp;

}

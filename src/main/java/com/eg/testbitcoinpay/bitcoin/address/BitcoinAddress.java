package com.eg.testbitcoinpay.bitcoin.address;

import javax.persistence.*;
import java.io.Serializable;

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
    private java.sql.Timestamp createTime;

    @Column(name = "orderId")
    private Integer orderId;

    @Column(name = "address")
    private String address;

    @Column(name = "private_key")
    private String privateKey;

    @Column(name = "label")
    private String label;

    @Column(name = "satoshi")
    private Long satoshi;


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

    public java.sql.Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrivateKey() {
        return this.privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getSatoshi() {
        return this.satoshi;
    }

    public void setSatoshi(Long satoshi) {
        this.satoshi = satoshi;
    }
}

package com.eg.testbitcoinpay.bitcoin.transaction;

import javax.persistence.*;

@Entity
@Table(name = "pay_bitcoin_transaction")
public class PayBitcoinTransaction {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "create_time")
    private java.sql.Timestamp createTime;

    @Column(name = "receive_address")
    private Integer receiveAddress;

    @Column(name = "satoshi")
    private Long satoshi;

    @Column(name = "timereceived")
    private java.sql.Timestamp timereceived;

    @Column(name = "confirmations")
    private Integer confirmations;

    @Column(name = "txid")
    private String txid;


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

    public Integer getReceiveAddress() {
        return this.receiveAddress;
    }

    public void setReceiveAddress(Integer receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public Long getSatoshi() {
        return this.satoshi;
    }

    public void setSatoshi(Long satoshi) {
        this.satoshi = satoshi;
    }

    public java.sql.Timestamp getTimereceived() {
        return this.timereceived;
    }

    public void setTimereceived(java.sql.Timestamp timereceived) {
        this.timereceived = timereceived;
    }

    public Integer getConfirmations() {
        return this.confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public String getTxid() {
        return this.txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }
}

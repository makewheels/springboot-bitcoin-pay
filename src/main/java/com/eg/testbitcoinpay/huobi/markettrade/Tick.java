package com.eg.testbitcoinpay.huobi.markettrade;

import java.util.List;

public class Tick {
    private List<DataItem> data;
    private long id;
    private long ts;

    public List<DataItem> getData() {
        return data;
    }

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }
}
package com.yinuo.mock;

import javafx.beans.property.SimpleStringProperty;

public class ConsumptionRecord {
    private SimpleStringProperty recordTime;
    private SimpleStringProperty recordMoney;
    private SimpleStringProperty recordMsg;

    public ConsumptionRecord(String recordTime, String recordMoney, String recordMsg) {
        this.recordTime = new SimpleStringProperty(recordTime);
        this.recordMoney = new SimpleStringProperty(recordMoney);
        this.recordMsg = new SimpleStringProperty(recordMsg);
    }

    public String getRecordTime() {
        return recordTime.get();
    }

    public SimpleStringProperty recordTimeProperty() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime.set(recordTime);
    }

    public String getRecordMoney() {
        return recordMoney.get();
    }

    public SimpleStringProperty recordMoneyProperty() {
        return recordMoney;
    }

    public void setRecordMoney(String recordMoney) {
        this.recordMoney.set(recordMoney);
    }

    public String getRecordMsg() {
        return recordMsg.get();
    }

    public SimpleStringProperty recordMsgProperty() {
        return recordMsg;
    }

    public void setRecordMsg(String recordMsg) {
        this.recordMsg.set(recordMsg);
    }
}

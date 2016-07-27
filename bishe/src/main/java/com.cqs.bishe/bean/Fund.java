package com.cqs.bishe.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cqs on 16-6-13.
 */
public class Fund implements Serializable{

    private int id;
    private String fundCp;
    private double rate;
    private String info;
    private int status;
    private Date createTime;
    private Date updateTime;

    public Fund() {
    }

    public static class Builder {
        private Fund fund;

        public Builder() {
            fund = new Fund();
        }

        public Fund end() {
            return fund;
        }

        public Builder setFund_cp(String fund_cp) {
            fund.setFundCp(fund_cp);
            return this;
        }


        public Builder setRate(double rate) {
            fund.setRate(rate);
            return this;
        }

        public Builder setInfo(String info) {
            fund.setInfo(info);
            return this;
        }

        public Builder setStatus(int status) {
            fund.setStatus(status);
            return this;
        }

    }


    public static enum FundStatus{
        start(1),
        stop(0);

        private int id;

        private FundStatus(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFundCp() {
        return fundCp;
    }

    public void setFundCp(String fundCp) {
        this.fundCp = fundCp;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

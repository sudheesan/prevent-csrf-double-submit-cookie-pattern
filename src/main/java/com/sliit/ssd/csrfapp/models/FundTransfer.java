package com.sliit.ssd.csrfapp.models;

/**
 * Represents a fund transfer entity
 *
 * Created by ssudheesan on 9/6/18.
 */
public class FundTransfer {

    private String to;
    private String from;
    private String amount;
    private String csrf;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCsrf() {
        return csrf;
    }

    public void setCsrf(String csrf) {
        this.csrf = csrf;
    }


    @Override
    public String toString() {
        return "FundTransfer{" +
                "to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", amount=" + amount +
                ", csrf='" + csrf + '\'' +
                '}';
    }
}

package com.stroganov.Strategies;

public class Balance {

    private Double freeMoney;
    private int papers;
    private Double allBalance;


    public Balance(Double freeMoney) {
        this.freeMoney = freeMoney;
        papers = 0;
        allBalance = freeMoney;
    }

    public Balance() {

    }

    public Double getFreeMoney() {
        return freeMoney;
    }

    public Double getAllBalance(Double price) {
        allBalance = freeMoney + papers * price;
        return allBalance;
    }

    public Double getAllBalance() {
        return allBalance;
    }

    public int getPapers() {
        return papers;
    }

    public void setFreeMoney(Double freeMoney) {
        this.freeMoney = freeMoney;
    }

    public void setPapers(int papers) {
        this.papers = papers;
    }

    public void setAllBalance(Double allBalance) {
        this.allBalance = allBalance;
    }
}

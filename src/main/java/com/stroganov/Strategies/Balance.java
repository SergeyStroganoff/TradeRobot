package com.stroganov.Strategies;

public class Balance {

    private double freeMoney;
    private int papers;
    private double allBalance;


    public Balance(double freeMoney) {
        this.freeMoney = freeMoney;
        papers = 0;
        allBalance = freeMoney;
    }

    public Balance() {

    }

    public double getFreeMoney() {
        return freeMoney;
    }

    public double getAllBalance(double price) {
        allBalance = freeMoney + papers * price;
        return allBalance;
    }

    public double getAllBalance() {
        return allBalance;
    }

    public int getPapers() {
        return papers;
    }

    public void setFreeMoney(double freeMoney) {
        this.freeMoney = freeMoney;
    }

    public void setPapers(int papers) {
        this.papers = papers;
    }

    public void setAllBalance(double allBalance) {
        this.allBalance = allBalance;
    }
}

package com.stroganov.Strategies;

public class Balance {

    private float freeMoney;
    private int papers;
    private float allBalance;


    public Balance(float freeMoney) {
        this.freeMoney = freeMoney;
        papers = 0;
        allBalance = freeMoney;
    }

    public Balance() {

    }

    public float getFreeMoney() {
        return freeMoney;
    }


    public float getAllBalance(float price) {
        allBalance = freeMoney + papers * price;
        return allBalance;
    }

    public float getAllBalance() {
        return allBalance;
    }

    public int getPapers() {
        return papers;
    }

    public void setPapers(int papers) {
        this.papers = papers;
    }

    public void setFreeMoney(float freeMoney) {
        this.freeMoney = freeMoney;
    }

    public void setAllBalance(float allBalance) {
        this.allBalance = allBalance;
    }
}

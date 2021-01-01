package com.stroganov.Strategies;

import java.util.Objects;

public class Transaction {

    private final String tradeDirect;
    private final int candleIndex;
    private final Balance balance;
    private final Double price;
    private final int countPapers;


    public Transaction(String tradeDirect, int candleIndex, Balance balance, Double price, int countPapers) {
        this.tradeDirect = tradeDirect;
        this.candleIndex = candleIndex;
        this.price = price;
        this.countPapers = countPapers;
        this.balance = new Balance();
        this.balance.setFreeMoney(balance.getFreeMoney());
        this.balance.setPapers(balance.getPapers());
        this.balance.setAllBalance(balance.getAllBalance());
    }

    public String getTradeDirect() {
        return tradeDirect;
    }

    public int getCandleIndex() {
        return candleIndex;
    }

    public Balance getBalance() {
        return balance;
    }

    public Double getPrice() {
        return price;
    }

    public int getCountPapers() {
        return countPapers;
    }


    @Override
    public String toString() {
        String direct = getTradeDirect().equals("Buy")?"Покупка":"Продажа";
        return direct+ " "+ countPapers+ " акций по цене: " + String.format("%.2f",getPrice()) +". "+
                "Общий баланс составил "+ String.format("%.2f",getBalance().getAllBalance())+ " Свеча №"+ getCandleIndex();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return candleIndex == that.candleIndex && countPapers == that.countPapers && Objects.equals(tradeDirect, that.tradeDirect) && balance.equals(that.balance) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tradeDirect, candleIndex, balance, price, countPapers);
    }
}

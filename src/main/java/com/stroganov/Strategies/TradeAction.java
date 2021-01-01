package com.stroganov.Strategies;

public class TradeAction {

    Balance balance;
    private  Double  brokerTax = 0.00035;

    public TradeAction(Balance balance) {
        this.balance = balance;
    }

    public Balance getBalance() {
        return balance;
    }

    public Transaction sell(int papersCount, double price, int candleIndex) {

        double payment = papersCount * price;
        double brokerPayment = brokerTax*price;

        balance.setPapers(balance.getPapers() - papersCount);
        balance.setFreeMoney(balance.getFreeMoney() + payment - brokerPayment);
        balance.setAllBalance(balance.getFreeMoney() + price * balance.getPapers());

        System.out.println("Продажа акций по цене" + price + ". Общий баланс составил: " + balance.getAllBalance());

        return new Transaction("Sell", candleIndex, balance, price, papersCount); // (String tradeDirect, int candleIndex, Balance balance, Double price, Double countPapers) {
    }
    public Transaction buy(int papersCount, double price, int candleIndex) {

        double payment = papersCount * price;
        double brokerPayment = brokerTax*price;

        balance.setPapers(balance.getPapers() + papersCount);
        balance.setFreeMoney(balance.getFreeMoney() - payment - brokerPayment);
        balance.setAllBalance(balance.getFreeMoney() + price * balance.getPapers());

        System.out.println("Покупка акций по цене" + price + ". Общий баланс составил: " + balance.getAllBalance());

        return new Transaction("Buy", candleIndex, balance, price, papersCount); // (String tradeDirect, int candleIndex, Balance balance, Double price, Double countPapers) {
    }


}

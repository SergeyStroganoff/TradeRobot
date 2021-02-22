package com.stroganov.Strategies;

import com.stroganov.MainSMA;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public  class TradeAction {    // может сделать статическим - баланс перенести в стратегию ?

    private static   final Logger logger = Logger.getLogger(TradeAction.class);


    Balance balance;
    private  final   float  brokerTax = 0.00035f;

    public TradeAction(Balance balance) {
        this.balance = balance;
        logger.setLevel(Level.WARN);   // устанавливаем уровень логирования
    }

    public Balance getBalance() {
        return balance;
    }



    public Transaction doTransaction(String directOfTransaction, int papersCount, float price, int candleIndex) {

        String direct = "Покупка";
        if (!directOfTransaction.equals("sell") && (!directOfTransaction.equals("buy") )) {
            throw new IllegalArgumentException("Направление торговой операции задано неверно");}

        float payment = papersCount * price;
        float brokerPayment = brokerTax*payment;

        if (directOfTransaction.equals("sell")){
            papersCount = 0 - papersCount;
            payment = 0 - payment;
            direct = "Продажа";
        }

        balance.setPapers(balance.getPapers() + papersCount);
        balance.setFreeMoney( (balance.getFreeMoney() - payment - brokerPayment));
        balance.setAllBalance(balance.getFreeMoney() + price * balance.getPapers());

        logger.info(direct+ " акций по цене " + price + ". Общий баланс составил: " + balance.getAllBalance());

        return new Transaction("Buy", candleIndex, balance, price, papersCount);
    }


    public Transaction sell(int papersCount, float price, int candleIndex) {

        float payment = papersCount * price;
        float brokerPayment = brokerTax*payment;

        balance.setPapers(balance.getPapers() - papersCount);
        balance.setFreeMoney(balance.getFreeMoney() + payment - brokerPayment);
        balance.setAllBalance(balance.getFreeMoney() + price * balance.getPapers());

       logger.info("Продажа акций по цене " + price + ". Общий баланс составил: " + balance.getAllBalance());

        return new Transaction("Sell", candleIndex, balance, price, papersCount);
    }

    public Transaction buy(int papersCount, float price, int candleIndex) {

        float payment = papersCount * price;
        float brokerPayment = brokerTax*payment;
        logger.info("Оплата брокеру составила: " + brokerPayment);

        balance.setPapers(balance.getPapers() + papersCount);
        balance.setFreeMoney( (balance.getFreeMoney() - payment - brokerPayment));
        balance.setAllBalance(balance.getFreeMoney() + price * balance.getPapers());

        logger.info("Покупка акций по цене " + price + ". Общий баланс составил: " + balance.getAllBalance());

        return new Transaction("Buy", candleIndex, balance, price, papersCount);
    }


}

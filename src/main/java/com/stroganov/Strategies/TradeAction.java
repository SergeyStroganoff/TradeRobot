package com.stroganov.Strategies;

import com.stroganov.MainSMA;
import org.apache.log4j.Logger;

public class TradeAction {

    private static final Logger logger = Logger.getLogger(TradeAction.class);

    Balance balance;
    private final   float  brokerTax = 0.00035f;
    private final boolean logPrint=false;

    public TradeAction(Balance balance) {

        this.balance = balance;
    }

    public Balance getBalance() {
        return balance;
    }

    //

    public void setBalance(Balance balance) {
        this.balance = balance;
    }


    ///

    public Transaction sell(int papersCount, float price, int candleIndex) {

        float payment = papersCount * price;
        float brokerPayment = brokerTax*payment;

        balance.setPapers(balance.getPapers() - papersCount);
        balance.setFreeMoney(balance.getFreeMoney() + payment - brokerPayment);
        balance.setAllBalance(balance.getFreeMoney() + price * balance.getPapers());

      if (logPrint)logger.info("Продажа акций по цене " + price + ". Общий баланс составил: " + balance.getAllBalance());

        return new Transaction("Sell", candleIndex, balance, price, papersCount);
    }
    public Transaction buy(int papersCount, float price, int candleIndex) {

        float payment = papersCount * price;
        float brokerPayment = brokerTax*payment;
        if (logPrint) logger.info("Оплата брокеру составила: " + brokerPayment);

        balance.setPapers(balance.getPapers() + papersCount);
        balance.setFreeMoney( (balance.getFreeMoney() - payment - brokerPayment));
        balance.setAllBalance(balance.getFreeMoney() + price * balance.getPapers());

        if (logPrint) logger.info("Покупка акций по цене " + price + ". Общий баланс составил: " + balance.getAllBalance());

        return new Transaction("Buy", candleIndex, balance, price, papersCount);
    }


}

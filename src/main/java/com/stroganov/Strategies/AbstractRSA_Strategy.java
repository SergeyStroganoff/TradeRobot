package com.stroganov.Strategies;

import com.stroganov.Candle;
import com.stroganov.CandleStream;
import com.stroganov.Indicators.AbstractIndicator;
import com.stroganov.Indicators.RSA_Indicator;

import java.util.ArrayList;

public abstract class AbstractRSA_Strategy {

    CandleStream candleStream;
    AbstractIndicator rsa;
    TradeAction tradeAction;
    int paperCount;
    ArrayList<Transaction> transactionArrayList = new ArrayList<>();

    public AbstractRSA_Strategy(CandleStream candleStream, TradeAction tradeAction, int paperCount, int period) {
        this.candleStream = candleStream;
        this.rsa = new RSA_Indicator(candleStream, period);
        this.paperCount = paperCount;
        this.tradeAction = tradeAction;
    }
    public void printLn(String string) {
        System.out.println(string);
    }

    public ArrayList<Transaction> getTransactionArray() {
        if (transactionArrayList.isEmpty()) {
            throw new IllegalArgumentException("Список транзакций пуст");
        }
        return transactionArrayList;
    }
    public abstract void  runStrategy(int buyLine, int sellLine);

}

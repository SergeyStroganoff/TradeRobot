package com.stroganov.Strategies;

import com.stroganov.CandleStream;
import com.stroganov.Indicators.AbstractIndicator;
import com.stroganov.Indicators.IndicatorContainer;

import java.util.ArrayList;

public abstract class AbstractStrategy {

    CandleStream candleStream;
    AbstractIndicator indicatorOne;
    AbstractIndicator indicatorTwo;
    TradeAction tradeAction;
    int paperCount;
    ArrayList<Transaction> transactionArrayList = new ArrayList<>();
    IndicatorContainer container;

    public AbstractStrategy(CandleStream candleStream, TradeAction tradeAction, int paperCount, int periodOne, int periodTwo, IndicatorContainer container) {
        this.candleStream = candleStream;
        this.container = container;
        this.indicatorOne = container.getIndicatorByPeriod(periodOne);
        this.indicatorTwo = container.getIndicatorByPeriod(periodTwo);
        if (indicatorOne == null || indicatorTwo == null) {
            System.out.println("indicator NULL !!!");
        }
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

    public abstract void  runStrategy(StrategyParam strategyParam);

}

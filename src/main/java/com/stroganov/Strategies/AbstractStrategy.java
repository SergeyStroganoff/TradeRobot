package com.stroganov.Strategies;

import com.stroganov.CandleStream;
import com.stroganov.Indicators.AbstractIndicator;
import com.stroganov.Indicators.IndicatorContainer;
import com.stroganov.MainSMA;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStrategy {

    private static final Logger logger = Logger.getLogger(AbstractStrategy.class);

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
           logger.info(" Сформирован пустой (NULL) индикатор " + periodOne + " " + periodTwo);
        }
        this.paperCount = paperCount;
        this.tradeAction = tradeAction;
    }
    public void printLn(String string) {
        System.out.println(string);
    }

    public ArrayList<Transaction> getTransactionArray() throws IllegalArgumentException {
        if (transactionArrayList.isEmpty()) {
            logger.error("Запрошенный список транзакций - пуст");
            throw new IllegalArgumentException("Список транзакций пуст");
        }
        return transactionArrayList;
    }

    public abstract void  runStrategy(StrategyParam strategyParam);

}

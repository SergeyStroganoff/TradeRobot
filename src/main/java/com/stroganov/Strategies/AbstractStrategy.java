package com.stroganov.Strategies;

import com.stroganov.CandleStream;
import com.stroganov.Grafics.Report;
import com.stroganov.Indicators.AbstractIndicator;
import com.stroganov.Indicators.IndicatorContainer;
import com.stroganov.MainSMA;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStrategy {

    private static final Logger logger = Logger.getLogger(AbstractStrategy.class);

    CandleStream candleStream;
    TradeAction tradeAction;
    int paperCount;
    ArrayList<Transaction> transactionArrayList = new ArrayList<>();
    IndicatorContainer container;

    public AbstractStrategy(CandleStream candleStream, TradeAction tradeAction, int paperCount,  IndicatorContainer container) {
        this.candleStream = candleStream;
        this.container = container;
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


    public Report testStrategyGetReport(StrategyParam strategyParam,float startMoney) { // Может разбить на два метода фабричный  и метод запуска ?

        AbstractIndicator indicatorOne = container.getIndicatorByPeriod(strategyParam.getPeriodOne());
        AbstractIndicator indicatorTwo =  container.getIndicatorByPeriod(strategyParam.getPeriodTwo());
        Balance balance = new Balance(startMoney);
        Report report = new Report(balance);

        if (indicatorOne == null || indicatorTwo == null) {
            logger.info("Сформирован пустой (NULL) индикатор " + strategyParam.getPeriodOne() + " " + strategyParam.getPeriodTwo());
        }


        runStrategy(strategyParam, indicatorOne, indicatorTwo);
        if (report.prepareReport(getTransactionArray())) {
            logger.debug("Отчет сформирован успешно");
        } else {
            logger.error("Отчет с параметрами " + strategyParam.toString() + " не сформировался т.к. список транзакций пуст");
        }
        return report;
    }




    public abstract void  runStrategy(StrategyParam strategyParam, AbstractIndicator indicatorOne, AbstractIndicator indicatorTwo);

}

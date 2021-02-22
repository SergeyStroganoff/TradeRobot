package com.stroganov.Strategies;

import com.stroganov.CandleStream;
import com.stroganov.Grafics.Report;
import com.stroganov.Indicators.AbstractIndicator;
import com.stroganov.Indicators.IndicatorContainer;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public abstract class AbstractStrategy {

    private static final Logger logger = Logger.getLogger(AbstractStrategy.class);

    CandleStream candleStream;
    int paperCount;
    IndicatorContainer container;
    float startMoney;

    public AbstractStrategy(CandleStream candleStream, int paperCount, IndicatorContainer container, float startMoney) {

        if (startMoney<=0 ){
            logger.error("Ошибка - Задано неверное значение стартового капитала");
            throw new IllegalArgumentException("Ошибка - Задано неверное значение стартового капитала");
        }

        if (paperCount<=0 ){
            logger.error("Ошибка - Задано неверное значение количества лотов");
            throw new IllegalArgumentException("Ошибка - Задано неверное значение количества лотов");
        }

        this.candleStream = candleStream;
        this.container = container;
        this.paperCount = paperCount;
        this.startMoney = startMoney;
    }

    public float getStartMoney() {
        return startMoney;
    }

    public void printLn(String string) {
        System.out.println(string);
    }

    public Report testStrategyGetReport(StrategyParam strategyParam) { // Может send container ?

        AbstractIndicator indicatorOne = container.getIndicatorByPeriod(strategyParam.getPeriodOne());
        AbstractIndicator indicatorTwo = container.getIndicatorByPeriod(strategyParam.getPeriodTwo());
        Balance balance = new Balance(startMoney);
        Report report = new Report(balance);

        if (indicatorOne == null || indicatorTwo == null) {
            logger.info("Сформирован пустой (NULL) индикатор " + strategyParam.getPeriodOne() + " " + strategyParam.getPeriodTwo());
        }


        ArrayList<Transaction> transactionArrayList = runStrategy(strategyParam, indicatorOne, indicatorTwo );
        if (report.prepareReport(transactionArrayList)) {
            logger.debug("Отчет сформирован успешно");
        } else {
            logger.error("Отчет с параметрами " + strategyParam.toString() + " не сформировался т.к. список транзакций пуст");
        }
        return report;
    }



    public abstract ArrayList<Transaction> runStrategy(StrategyParam strategyParam, AbstractIndicator abstractIndicatorOne, AbstractIndicator abstractIndicatorTwo);

}

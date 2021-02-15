package com.stroganov.Strategies;

import com.stroganov.CandleStream;
import com.stroganov.Grafics.Report;
import com.stroganov.Indicators.IndicatorContainer;
import com.stroganov.MainSMA;
import org.apache.log4j.Logger;


public class TestStrategy  {

    private static final Logger logger = Logger.getLogger(TestStrategy.class);

    private IndicatorContainer container;
    CandleStream candleStream;
    float startMoney;

    public TestStrategy(CandleStream candleStream, IndicatorContainer container, float startMoney) {

        this.candleStream = candleStream;
        this.startMoney = startMoney;
        this.container = container;
    }



    public AbstractStrategy createStrategy(CandleStream candleStream, IndicatorContainer container, StrategyParam strategyParam, Strategies strategies, float startMoney){

        AbstractStrategy strategy = null;
        Balance balance = new Balance(startMoney);
        TradeAction tradeAction = new TradeAction(balance);

        switch (strategies.name()) {
            case ("RSA_STRATEGY_STOP"):
                strategy = new RSA_StrategyStopLoss(candleStream, tradeAction, 400, strategyParam.getPeriodOne(), strategyParam.getPeriodTwo(), container);
                break;


            case ("SMA_STRATEGY"):
                strategy = new SMA_Strategy(candleStream, tradeAction, 400, strategyParam.getPeriodOne(), strategyParam.getPeriodTwo(), container);
                break;


            case ("SMA_STRATEGY_REVERSE"):
                strategy = new SMA_StrategyReverse(candleStream, tradeAction, 400, strategyParam.getPeriodOne(), strategyParam.getPeriodTwo(), container);
                break;


            case ("SMA_STRATEGY_REVERSE_DIFFERENT"):
                strategy = new SMA_StrategyReverseDifferent(candleStream, tradeAction, 400, strategyParam.getPeriodOne(), strategyParam.getPeriodTwo(), container);
                break;

            default:
                String s = "Тип стратегии указан неверно";
                logger.error(s);
                throw new IllegalArgumentException(s);

        }


       return strategy;

    } //





    public Report runStrategy(StrategyParam strategyParam, Strategies strategies) { // Может разбить на два метода фабричный  и метод запуска ?

        Balance balance = new Balance(startMoney);
        Report report = new Report(balance);
        TradeAction tradeAction = new TradeAction(balance);
        AbstractStrategy strategy = null;

        switch (strategies.name()) {
            case ("RSA_STRATEGY_STOP"):
                strategy = new RSA_StrategyStopLoss(candleStream, tradeAction, 400, strategyParam.getPeriodOne(), strategyParam.getPeriodTwo(), container);
                break;


            case ("SMA_STRATEGY"):
                strategy = new SMA_Strategy(candleStream, tradeAction, 400, strategyParam.getPeriodOne(), strategyParam.getPeriodTwo(), container);
                break;


            case ("SMA_STRATEGY_REVERSE"):
                strategy = new SMA_StrategyReverse(candleStream, tradeAction, 400, strategyParam.getPeriodOne(), strategyParam.getPeriodTwo(), container);
                break;


            case ("SMA_STRATEGY_REVERSE_DIFFERENT"):
                strategy = new SMA_StrategyReverseDifferent(candleStream, tradeAction, 400, strategyParam.getPeriodOne(), strategyParam.getPeriodTwo(), container);
                break;

            default:
                String s = "Тип стратегии указан неверно";
                logger.error(s);
                throw new IllegalArgumentException(s);

        }

        strategy.runStrategy(strategyParam);
        if (report.prepareReport(strategy.getTransactionArray())) {
            logger.debug("Отчет сформирован успешно");
        } else {
           logger.error("Отчет с параметрами " + strategyParam.toString() + " не сформировался т.к. список транзакций пуст");
        }
        return report;
    }
}

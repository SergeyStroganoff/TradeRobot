package com.stroganov.Strategies;

import com.stroganov.CandleStream;
import com.stroganov.Grafics.Report;
import com.stroganov.Indicators.IndicatorContainer;

public class StartStrategyNow extends StartStrategy {

    private IndicatorContainer container;

    public StartStrategyNow(CandleStream candleStream, IndicatorContainer container, double startMoney) {
        super(candleStream, startMoney);
        this.container = container;
    }

    @Override
    public Report testStrategy(StrategyParam strategyParam, Strategies strategies) {

        Balance balance = new Balance(startMoney);
        Report report = new Report(balance);
        TradeAction tradeAction = new TradeAction(balance);
        AbstractStrategy strategy = null;

        switch (strategies.name()) {
            case ("RSA_STRATEGY_STOP"): {
                strategy = new RSA_StrategyStopLoss(candleStream, tradeAction, 400, strategyParam.getPeriodOne(), strategyParam.getPeriodTwo(), container);
                break;
            }

            case ("SMA_STRATEGY"): {
                strategy = new SMA_Strategy(candleStream, tradeAction, 400, strategyParam.getPeriodOne(),strategyParam.getPeriodTwo(), container);
                break;
            }

            case ("SMA_STRATEGY_REVERSE" ): {
                strategy = new SMA_StrategyReverse(candleStream, tradeAction, 400, strategyParam.getPeriodOne(),strategyParam.getPeriodTwo(), container);
                break;
            }


            default: {
                throw new IllegalArgumentException("Тип стратегии указан неверно");
            }
        }


        strategy.runStrategy(strategyParam);
        if (report.prepareReport(strategy.getTransactionArray())) {
            System.out.println("Отчет сформирован успешно");
        } else {
            System.out.println("С отчетом что то не так!!!");
            //throw new IllegalStateException("Ошибка подготовки отчета");
        }
        return report;
    }
}

package com.stroganov.Strategies;

import com.stroganov.CandleStream;
import com.stroganov.Grafics.Report;
import com.stroganov.Indicators.IndicatorContainer;

public class StartStrategyRSA_StopLoss extends StartStrategy {

    private IndicatorContainer container;

    public StartStrategyRSA_StopLoss(CandleStream candleStream,IndicatorContainer container, double startMoney) {
        super(candleStream, startMoney);
        this.container = container;
    }

    @Override
    public Report testStrategy(StrategyParam strategyParam) {

        Balance balance = new Balance(startMoney);
        Report report = new Report(balance);
        TradeAction tradeAction = new TradeAction(balance);

        AbstractRSA_Strategy rsa_strategyStopLoss = new RSA_StrategyStopLoss(candleStream, tradeAction, 400, strategyParam.getPeriodRSA(), container); // TODO
        rsa_strategyStopLoss.runStrategy(strategyParam);
        if (report.prepareReport(rsa_strategyStopLoss.getTransactionArray())) {
           // report.printReport();
            System.out.println("Отчет сформирован успешно");
        } else {
            System.out.println("С отчетом что то не так!!!");
            throw new IllegalStateException("Ошибка подготовки отчета");
        }
        return report;
    }
}

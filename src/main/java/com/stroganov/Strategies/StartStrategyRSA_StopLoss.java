package com.stroganov.Strategies;

import com.stroganov.CandleStream;
import com.stroganov.Grafics.Report;

public class StartStrategyRSA_StopLoss extends StartStrategy {

    public StartStrategyRSA_StopLoss(CandleStream candleStream, double startMoney) {
        super(candleStream, startMoney);
    }

    @Override
    public Report startStrategy(StrategyParam strategyParam) {

        Balance balance = new Balance(startMoney);
        Report report = new Report(balance);
        TradeAction tradeAction = new TradeAction(balance);

        AbstractRSA_Strategy rsa_strategyStopLoss = new RSA_StrategyStopLoss(candleStream, tradeAction, 400, strategyParam.getPeriodRSA());
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

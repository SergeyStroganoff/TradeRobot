package com.stroganov.Strategies;

import com.stroganov.CandleStream;
import com.stroganov.Grafics.Report;

public abstract class StartStrategy {

    CandleStream candleStream;


    public StartStrategy(CandleStream candleStream, double startMoney) {
        this.candleStream = candleStream;
        Balance balance = new Balance(startMoney);
        Report report = new Report(balance);
        TradeAction tradeAction = new TradeAction(balance);

    }

    public abstract Report startStrategy(StrategyParam strategyParam);

}

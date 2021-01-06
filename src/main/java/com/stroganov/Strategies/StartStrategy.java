package com.stroganov.Strategies;

import com.stroganov.CandleStream;
import com.stroganov.Grafics.Report;

public abstract class StartStrategy {

    CandleStream candleStream;
    double startMoney;


    public StartStrategy(CandleStream candleStream, double startMoney) {
        this.candleStream = candleStream;
        this.startMoney = startMoney;

    }

    public abstract Report testStrategy(StrategyParam strategyParam, String name);

}

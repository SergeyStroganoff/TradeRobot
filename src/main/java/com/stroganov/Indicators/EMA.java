package com.stroganov.Indicators;

import com.stroganov.CandleStream;

public class EMA extends AbstractIndicator {

    public EMA(CandleStream candleStream, int period) {
        super(candleStream, period);
    }

    private double calcFirstCandleEMA() {

        return candlesArrayList.get(0).getCloseCandle();
    }

    public void calculateIndicator() {
        double index;
        double k = 2 / (period + 1f);
        arrayListIndicator.add(calcFirstCandleEMA());
        for (int i = 1; i < candlesArrayList.size(); i++) {
            index = candlesArrayList.get(i).getCloseCandle() * k + arrayListIndicator.get(i - 1) * (1 - k);
            arrayListIndicator.add(index);
        }

    }

}

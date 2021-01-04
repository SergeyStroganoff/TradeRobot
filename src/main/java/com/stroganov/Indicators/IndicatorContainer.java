package com.stroganov.Indicators;

import com.stroganov.CandleStream;

public class IndicatorContainer {

    AbstractIndicator rsa_indicators[] = new AbstractIndicator[32];
    AbstractIndicator abstractIndicator;
    CandleStream candleStream;

    public IndicatorContainer(CandleStream candleStream) {
        this.candleStream = candleStream;
        for (int i = 1; i < 32; i++) {
            rsa_indicators[i] = new RSA_Indicator(candleStream, i);
        }
    }

    public AbstractIndicator getIndicatorByPeriod(int period) {
        if (period < 1 && period > 32)
            throw new IllegalArgumentException("Период индикатора указан в неверном диапазоне - должен быть от 1 до 32");
        return rsa_indicators[period];
    }

}

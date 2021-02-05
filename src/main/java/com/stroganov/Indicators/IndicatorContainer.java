package com.stroganov.Indicators;

import com.stroganov.CandleStream;

public class IndicatorContainer {

    AbstractIndicator bufIndicators[] = new AbstractIndicator[90];
    CandleStream candleStream;

    public IndicatorContainer(CandleStream candleStream, Indicators indicators) {
        this.candleStream = candleStream;

        switch (indicators.name()) {

            case ("EMA"): {
                for (int i = 1; i < 90; i++) {
                    bufIndicators[i] = new EMA(candleStream, i);
                }
                break;
            }

            case ("RSA"): {
                for (int i = 3; i < 28; i++) {
                    bufIndicators[i] = new RSA_Indicator(candleStream, i);
                }
                break;
            }

            case ("SMMA"): {
                for (int i = 3; i < 90; i++) {
                    bufIndicators[i] = new SMMA(candleStream, i);
                }
                break;
            }

            case ("SMA"): {
                for (int i = 3; i < 90; i++) {
                    bufIndicators[i] = new SMA(candleStream, i);
                }
                break;
            }

            default: {
                throw new IllegalArgumentException("Индикатор указан неверно");
            }

        }
    }

    public AbstractIndicator getIndicatorByPeriod(int period) {
        if (period < 0 || period > 32)
            throw new IllegalArgumentException("Период индикатора указан в неверном диапазоне - должен быть от 3 до 28");
        return bufIndicators[period];
    }

}

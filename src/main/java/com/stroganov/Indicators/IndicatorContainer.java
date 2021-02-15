package com.stroganov.Indicators;

import com.stroganov.CandleStream;
import com.stroganov.MainSMA;
import org.apache.log4j.Logger;

public class IndicatorContainer {
    private static final Logger logger = Logger.getLogger(IndicatorContainer.class);


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
                logger.error("индикатор "+ indicators.name() +" указан  неверно");
                throw new IllegalArgumentException("Индикатор указан неверно");
            }

        }
    }

    public AbstractIndicator getIndicatorByPeriod(int period) {
        if (period < 0 || period > 90)
        { logger.error("Период индикатора указан в неверном диапазоне");
            throw new IllegalArgumentException("Период индикатора указан в неверном диапазоне ");}
        return bufIndicators[period];
    }

}

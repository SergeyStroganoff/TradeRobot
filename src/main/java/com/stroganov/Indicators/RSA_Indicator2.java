package com.stroganov.Indicators;

import com.stroganov.CandleStream;

public class RSA_Indicator2 extends AbstractIndicator {

    public RSA_Indicator2(CandleStream candleStream, int period) {
        super(candleStream, period);
    }


    private double calcRSI(double up, double down) {
        double rsi;
        rsi = 100 - 100 / (1 + up / down);
        return rsi;
    }

    public void calculateIndicator() {

        double up = 0;
        double down = 0;
        double delta;
        double rsi;

        for (int s = 1; s < period; s++) {
            arrayListIndicator.add(0d);
            System.out.println("какого хуя !!!");
        }

        for (int i = 1; i < period; i++) {
            delta = candlesArrayList.get(i).getCloseCandle() - candlesArrayList.get(i - 1).getCloseCandle();
            if (delta > 0) {
                up += delta;
            } else {
                down -= delta;
            }
        }
        up /= period - 1;                         // up = up/period-1  средний UP
        down /= period - 1;
        arrayListIndicator.add(calcRSI(up, down));  // инициализируем значение RSI !!!

////////////////////////////////////////////////////////
        double u = 0;
        double d = 0;
        for (int n = period; n < candlesArrayList.size(); n++) {
            delta = candlesArrayList.get(n).getCloseCandle() - candlesArrayList.get(n - 1).getCloseCandle();
            if (delta > 0) {
                u = delta;
            } else {
                d = -delta;
            }
            up = (up * (period - 1) + u) / period;
            down = (down * (period - 1) + d) / period;
            arrayListIndicator.add(calcRSI(up, down));  // инициализируем значение RSI !!!
        }
    }
}

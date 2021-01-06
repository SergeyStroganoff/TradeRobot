package com.stroganov.Indicators;


//Простая Скользящая средняя (SMA)
// рассчитывается как сумма цен закрытия каждой свечи за определенное число периодов
// (например, за 26 часов, как в нашем предыдущем примере), деленная на число периодов.
// SMA = SUM (CLOSE (i), N) / N где: SUM - сумма. CLOSE (i) - цена закрытия текущего периода. N - число периодов.
// Простая Скользящая средняя уравнивает по значимости цены каждого дня (4-х часов, 1 часа и т. д. в зависимости от выбранного таймфрейма).
// Из этого следует, что удельный вес каждому следующему значению задается одинаковый. И если за расчетный период были достаточно большие ценовые скачки,
// то простое Скользящая средняя будет их учитывать наравне с нормальным движением цены.


import com.stroganov.CandleStream;

import java.util.Collections;

public class SMA extends AbstractIndicator {


    public SMA(CandleStream candleStream, int period) {
        super(candleStream, period);
    }

    public double calculateSMAOneCandle(int period, int indexCandle) {
        float smaResult;
        smaResult = 0;
        for (int i = 0; i < period; i++) {
            smaResult += candlesArrayList.get(indexCandle-i).getCloseCandle();
        }
        return smaResult / period;
    }

    @Override
    public void calculateIndicator() {

        Double indicator;

        for (int i = candlesArrayList.size() - 1; i >=period; i--) {

            indicator = calculateSMAOneCandle(period, i);
            arrayListIndicator.add(indicator);
        }
        for (int n = 1; n < period; n++) {
            arrayListIndicator.add(0d);
        }
        Collections.reverse(arrayListIndicator);

    }
}

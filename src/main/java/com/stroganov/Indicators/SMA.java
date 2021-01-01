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
        if (period < 1) throw new IllegalArgumentException("период для SMA задан неверно");
        smaResult = 0;
        for (int i = period; i > 0; i--) {
            //   System.out.println("Период составляет " + i);
            smaResult += candlesArrayList.get(indexCandle).getCloseCandle();
            indexCandle--;
        }

        return smaResult / period;
    }

    @Override
    public void calculateIndicator() {

        for (int i = candlesArrayList.size() - 1; i >= period - 1; i--) {
            //   System.out.println(period+" +++++++++++ "+ i);
            arrayListIndicator.add(calculateSMAOneCandle(period, i));
        }
        for (int n = 1; n < period; n++) {
            arrayListIndicator.add(0d);
        }
        Collections.reverse(arrayListIndicator);

    }
}

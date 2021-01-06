package com.stroganov.Indicators;


/*
Для расчета относительной силы выбираются все свечи выбранного промежутка времени,
которые показали закрытие выше, чем предшествующая свеча,
и определяется среднее значение прироста с помощью формулы экспоненциального скользящего средней.
Аналогичная операция производится для свечей, показавших закрытие ниже предшествующей.
Отношение этих двух величин и даст значение относительной силы (RS).

RS = EMAn(Up) / EMAn(Down)
Для удобного отображения на графике полученная величина преобразуется таким образом,
чтобы значения укладывались в диапазон от 0 до 100%.
Полученный результат и есть индекс относительной силы, динамику которого можно увидеть на графике индикатора.
RSI = 100 — 100/ (1+RS)

 */

import com.stroganov.CandleStream;

import java.util.ArrayList;
import java.util.Collections;


public class RSA_Indicator extends AbstractIndicator {

    public RSA_Indicator(CandleStream candleStream, int period) {

        super(candleStream, period);
    }

    private double average(ArrayList<Double> arrayList) { // среднее значение
        float result = 0;
        if (arrayList.isEmpty()) {
            throw new IllegalArgumentException("Массив значений для вычисления средних пуст");
        }
        for (double number : arrayList) {
            result += number;
        }
        return result / arrayList.size();
    }

    public double calculateIndexOneCandle(int indexCandle) {
        double indexResult;
        double theDifference;
        int index = indexCandle;
        ArrayList<Double> upBarsClose = new ArrayList<>();
        ArrayList<Double> downBarsClose = new ArrayList<>();

        if (period < 1) throw new IllegalArgumentException("период задан неверно");

        for (int i = period; i > 0; i--) {
            theDifference = candlesArrayList.get(index).getCloseCandle() - candlesArrayList.get(index - 1).getCloseCandle();
            if (theDifference > 0) {
                upBarsClose.add(theDifference);
                downBarsClose.add(0d);
            } else {
                downBarsClose.add(Math.abs(theDifference));
                upBarsClose.add(0d);
            }
            index--;
        }
        if (average(downBarsClose) == 0) return 100;
        indexResult = 100 - 100 / (1 + average(upBarsClose) / average(downBarsClose));
        candlesArrayList.get(indexCandle).setIndicator((float) indexResult);                 // временно сохраняем значение RSI
        return indexResult;
    }

    public void calculateIndicator() {

        for (int i = candlesArrayList.size() - 1; i > period - 1; i--) {
            arrayListIndicator.add(calculateIndexOneCandle(i));
        }

        for (int n = 0; n < period; n++) {
            arrayListIndicator.add(0d);
        }
        Collections.reverse(arrayListIndicator);

    }
}

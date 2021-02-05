package com.stroganov.Indicators;

import com.stroganov.CandleStream;

import java.util.ArrayList;
import java.util.Collections;

public class SMA_Change extends SMA {

    public SMA_Change(CandleStream candleStream, int period) {
        super(candleStream, period);
    }

    @Override
    public void calculateIndicator() {
        super.calculateIndicator();

        ArrayList<Double> tempIndicatorList = new ArrayList<>();


        for (int i = arrayListIndicator.size() - 1; i >= period; i--) {

            double result = arrayListIndicator.get(i) - arrayListIndicator.get(i - 1);

            tempIndicatorList.add(result);
        }
        for (int n = 1; n < period + 1; n++) {
            tempIndicatorList.add(0d);

        }
        Collections.reverse(tempIndicatorList);
        arrayListIndicator.clear();
        arrayListIndicator = tempIndicatorList;

    }
}

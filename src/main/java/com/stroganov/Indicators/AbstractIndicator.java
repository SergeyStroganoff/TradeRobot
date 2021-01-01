package com.stroganov.Indicators;

import com.stroganov.Candle;
import com.stroganov.CandleStream;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractIndicator {


    ArrayList<Candle> candlesArrayList;
    ArrayList<Double> arrayListIndicator = new ArrayList<>();
    int period;

    public AbstractIndicator(CandleStream candleStream, int period) {
        if (period<1){throw new IllegalArgumentException();}
        this.candlesArrayList = candleStream.getCandlesArrayList();
        this.period = period;
        calculateIndicator();

    }

    public abstract void calculateIndicator();


    public ArrayList<Double> getArrayListIndicator() {
        return arrayListIndicator;
    }

    public void saveArrayIndicatorFile(String fileName){

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){

            for (Double index:arrayListIndicator) {
                writer.write(index +"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

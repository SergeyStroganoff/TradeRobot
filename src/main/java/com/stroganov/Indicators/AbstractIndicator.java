package com.stroganov.Indicators;

import com.stroganov.Candle;
import com.stroganov.CandleStream;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractIndicator {

    private static final Logger logger = Logger.getLogger(AbstractIndicator.class);

    ArrayList<Candle> candlesArrayList;
    ArrayList<Double> arrayListIndicator = new ArrayList<>();
    int period;

    public AbstractIndicator(CandleStream candleStream, int period) {
        if (period < 1) {
            logger.error("Период индикатора меньше 1");
            throw new IllegalArgumentException("Период индикатора меньше 1 ");
        }
        this.candlesArrayList = candleStream.getCandlesArrayList();
        this.period = period;
        calculateIndicator();
    }

    public int getPeriod() {
        return period;
    }

    public abstract void calculateIndicator();


    public ArrayList<Double> getArrayListIndicator() {
        return arrayListIndicator;
    }

    public void saveArrayIndicatorFile(String fileName) {

        if (fileName.isEmpty() & fileName == null) {
            logger.error("Имя файла для сохранения массива индикатора пусто");
            throw new IllegalArgumentException("Имя файла для сохранения массива индикатора пусто");
        }

        if (getArrayListIndicator().isEmpty()) {
            logger.error("Ошибка сохранения массива индекса " + this.getClass() + " Массив пустой");
            throw new ArrayStoreException("Ошибка сохранения массива индекса " + this.getClass() + " Массив пустой");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Double index : arrayListIndicator) {
                writer.write(index + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

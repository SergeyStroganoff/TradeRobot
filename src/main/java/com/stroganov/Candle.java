package com.stroganov;

import java.time.LocalDate;
import java.time.LocalTime;

public class Candle {

    private final String ticker;
    private final int period;
    private final LocalDate data;
    private final LocalTime time;
    private final float openCandle;
    private final float highCandle;
    private final float lowCandle;
    private final float closeCandle;
    private final float volume;
    private  float indicator=0f;  /// времянка

    public Candle(String ticker, int period, LocalDate data, LocalTime time, float openCandle, float highCandle, float lowCandle, float closeCandle, float volume) {
        this.ticker = ticker;
        this.period = period;
        this.data = data;
        this.time = time;
        this.openCandle = openCandle;
        this.highCandle = highCandle;
        this.lowCandle = lowCandle;
        this.closeCandle = closeCandle;
        this.volume = volume;
    }

    public void setIndicator(float indicator) {  /// времянка
        this.indicator = indicator;
    }

    public float getIndicator() {     /// времянка
        return indicator;
    }

    public String getTicker() {
        return ticker;
    }

    public int getPeriod() {
        return period;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getTime() {
        return time;
    }

    public float getOpenCandle() {
        return openCandle;
    }

    public float getHighCandle() {
        return highCandle;
    }

    public float getLowCandle() {
        return lowCandle;
    }

    public float getCloseCandle() {
        return closeCandle;
    }

    public float getVolume() {
        return volume;
    }


}

package com.stroganov;

import java.time.LocalDate;

public class Candle {

    private final String ticker;
    private final int period;
    private final LocalDate data;
    private final int time;
    private final float openCandle;
    private final float highCandle;
    private final float lowCandle;
    private final float closeCandle;
    private final float Volume;
    private  Double rsaIndicator=0d;  /// времянка

    public Candle(String ticker, int period, LocalDate data, int time, float openCandle, float highCandle, float lowCandle, float closeCandle, float volume) {
        this.ticker = ticker;
        this.period = period;
        this.data = data;
        this.time = time;
        this.openCandle = openCandle;
        this.highCandle = highCandle;
        this.lowCandle = lowCandle;
        this.closeCandle = closeCandle;
        Volume = volume;
    }

    public void setRsaIndicator(Double rsaIndicator) {  /// времянка
        this.rsaIndicator = rsaIndicator;
    }

    public Double getRsaIndicator() {     /// времянка
        return rsaIndicator;
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

    public int getTime() {
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
        return Volume;
    }


}

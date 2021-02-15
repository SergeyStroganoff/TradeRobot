package com.stroganov.Strategies;

import java.util.Objects;

public class StrategyParam {

    private int periodOne;
    private int periodTwo;
    private float buyLIne;
    private float sellLine;
    private float buyLIneShort;
    private float sellLineShort;

    private float stopLoss;

    public StrategyParam(int periodOne, int periodTwo, float buyLIne, float sellLine, float stopLoss) {
        this.periodOne = periodOne;
        this.periodTwo = periodTwo;
        this.buyLIne = buyLIne;
        this.sellLine = sellLine;
        this.stopLoss = stopLoss;
    }


    public StrategyParam(int periodOne, int periodTwo, float buyLineLong, float sellLineLong, float buyLineShort, float sellLineShort, float stopLoss) {
        this.periodOne = periodOne;
        this.periodTwo = periodTwo;
        this.buyLIne = buyLineLong;
        this.sellLine = sellLineLong;
        this.stopLoss = stopLoss;
        this.buyLIneShort = buyLineShort;
        this.sellLineShort = sellLineShort;
    }


    public float getBuyLIneShort() {
        return buyLIneShort;
    }

    public void setBuyLIneShort(float buyLIneShort) {
        this.buyLIneShort = buyLIneShort;
    }

    public float getSellLineShort() {
        return sellLineShort;
    }

    public void setSellLineShort(float sellLineShort) {
        this.sellLineShort = sellLineShort;
    }

    public int getPeriodOne() {
        return periodOne;
    }

    public int getPeriodTwo() {
        return periodTwo;
    }

    public float getBuyLIne() {
        return buyLIne;
    }

    public float getSellLine() {
        return sellLine;
    }

    public float getStopLoss() {
        return stopLoss;
    }

    public void setPeriodIndicator(int periodIndicator) {
        this.periodOne = periodIndicator;
    }

    public void setBuyLIne(int buyLIne) {
        this.buyLIne = buyLIne;
    }

    public void setSellLine(int sellLine) {
        this.sellLine = sellLine;
    }

    public void setStopLoss(int stopLoss) {
        this.stopLoss = stopLoss;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StrategyParam that = (StrategyParam) o;
        return periodOne == that.periodOne && periodTwo == that.periodTwo && Float.compare(that.buyLIne, buyLIne) == 0 && Float.compare(that.sellLine, sellLine) == 0 && Float.compare(that.buyLIneShort, buyLIneShort) == 0 && Float.compare(that.sellLineShort, sellLineShort) == 0 && Float.compare(that.stopLoss, stopLoss) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(periodOne, periodTwo, buyLIne, sellLine, buyLIneShort, sellLineShort, stopLoss);
    }

    @Override
    public String toString() {
        return "StrategyParam{" +
                "periodOne=" + periodOne +
                ", periodTwo=" + periodTwo +
                ", buyLIne=" + buyLIne +
                ", sellLine=" + sellLine +
                ", buyLIneShort=" + buyLIneShort +
                ", sellLineShort=" + sellLineShort +
                ", stopLoss=" + stopLoss +
                '}';
    }
}

package com.stroganov.Strategies;

import java.util.Objects;

public class StrategyParam {

   private int periodOne;
   private int periodTwo;
   private float buyLIne;
   private float sellLine;

   private float stopLoss;

    public StrategyParam(int periodOne,int periodTwo, float buyLIne, float sellLine, float stopLoss) {
        this.periodOne = periodOne;
        this.periodTwo = periodTwo;
        this.buyLIne = buyLIne;
        this.sellLine = sellLine;
        this.stopLoss = stopLoss;
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
        return periodOne == that.periodOne && buyLIne == that.buyLIne && sellLine == that.sellLine && stopLoss == that.stopLoss;
    }

    @Override
    public int hashCode() {
        return Objects.hash(periodOne, buyLIne, sellLine, stopLoss);
    }


    @Override
    public String toString() {
        return "StrategyParam{" +
                "periodOne=" + periodOne +
                ", periodTwo=" + periodTwo +
                ", buyLIne=" + buyLIne +
                ", sellLine=" + sellLine +
                ", stopLoss=" + stopLoss +
                '}';
    }
}

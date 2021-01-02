package com.stroganov.Strategies;

import java.util.Objects;

public class StrategyParam {

   private int periodRSA;
   private int buyLIne;
   private int sellLine;
   private int stopLoss;

    public StrategyParam(int periodIndicator, int buyLIne, int sellLine, int stopLoss) {
        this.periodRSA = periodIndicator;
        this.buyLIne = buyLIne;
        this.sellLine = sellLine;
        this.stopLoss = stopLoss;
    }


    public int getPeriodRSA() {
        return periodRSA;
    }

    public int getBuyLIne() {
        return buyLIne;
    }

    public int getSellLine() {
        return sellLine;
    }

    public int getStopLoss() {
        return stopLoss;
    }

    public void setPeriodIndicator(int periodIndicator) {
        this.periodRSA = periodIndicator;
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
        return periodRSA == that.periodRSA && buyLIne == that.buyLIne && sellLine == that.sellLine && stopLoss == that.stopLoss;
    }

    @Override
    public int hashCode() {
        return Objects.hash(periodRSA, buyLIne, sellLine, stopLoss);
    }


    @Override
    public String toString() {
        return "StrategyParam{" +
                "periodRSA=" + periodRSA +
                ", buyLIne=" + buyLIne +
                ", sellLine=" + sellLine +
                ", stopLoss=" + stopLoss +
                '}';
    }
}

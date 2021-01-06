package com.stroganov.Strategies;

/*
Стратегия Лонг покупки в зонах перепроданности  и продажи
в зонах перекошенности при условии цены продажи не меньше цены покупки
 */

import com.stroganov.Candle;
import com.stroganov.CandleStream;
import com.stroganov.Indicators.AbstractIndicator;
import com.stroganov.Indicators.IndicatorContainer;

import java.util.ArrayList;

public class RSA_Strategy {

    CandleStream candleStream;
    AbstractIndicator rsa;
    TradeAction tradeAction;
    int paperCount;
    ArrayList<Transaction> transactionArrayList = new ArrayList<>();

    public RSA_Strategy(CandleStream candleStream, TradeAction tradeAction, int paperCount, int period, IndicatorContainer container) {
        this.candleStream = candleStream;
        //this.rsa = new RSA_Indicator(candleStream, period);
        this.rsa = container.getIndicatorByPeriod(period);
        this.paperCount = paperCount;
        this.tradeAction = tradeAction;
    }

    public void printLn(String string) {
        System.out.println(string);
    }

    public ArrayList<Transaction> getTransactionArrayList() {
        if (transactionArrayList.isEmpty()) {
            throw new IllegalArgumentException("Список транзакций пуст");
        }
        return transactionArrayList;
    }

    public void startStrategy(int buyLine, int sellLine, int stopLoss) {

        boolean prepareToBuy = false;
        boolean prepareToSell = false;
        int index = 0;
        Double saveRsaIndicator = 0d;

        for (double rsaIndicator : rsa.getArrayListIndicator()) {

            StringBuffer stringBuffer = new StringBuffer();
            Candle currentCandle = candleStream.getCandlesArrayList().get(index);
            stringBuffer.append(currentCandle.getData()).append(" ").append(currentCandle.getTime()).append(" RSI   ").append(currentCandle.getIndicator());
            System.out.println(stringBuffer);

            float countPapers = tradeAction.getBalance().getPapers();

            if (rsaIndicator < 30 && !prepareToBuy && countPapers == 0) {
                prepareToBuy = true;

                printLn("Готовимся к покупке");
            }
            if (prepareToBuy) {
                if (rsaIndicator > saveRsaIndicator) {
                    transactionArrayList.add(tradeAction.buy(paperCount, candleStream.getCandlesArrayList().get(index + 1).getOpenCandle(), index));
                    prepareToBuy = false;
                    printLn("Купили акции по цене " + candleStream.getCandlesArrayList().get(index + 1).getOpenCandle() + candleStream.getCandlesArrayList().get(index).getData().toString());
                }
            }

            if (rsaIndicator > 70 && !prepareToSell && countPapers > 0) {
                prepareToSell = true;
                printLn("Готовимся к продаже ");
            }

            if (prepareToSell) { // продажа при условии RSA> предыдущего RSA, цена больше цены покупки.
                if (rsaIndicator < saveRsaIndicator && tradeAction.getBalance().getPapers() > 0 && candleStream.getCandlesArrayList().get(index + 1).getOpenCandle() > transactionArrayList.get(transactionArrayList.size() - 1).getPrice()) { //TODO
                    transactionArrayList.add(tradeAction.sell(paperCount, candleStream.getCandlesArrayList().get(index + 1).getOpenCandle(), index));
                    prepareToSell = false;
                    printLn("Продали акции в колличестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index + 1).getOpenCandle() + candleStream.getCandlesArrayList().get(index).getData().toString());
                }
            }
            index++;
            saveRsaIndicator = rsaIndicator;
        }
    }
}

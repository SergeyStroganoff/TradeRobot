package com.stroganov.Strategies;

import com.stroganov.Candle;
import com.stroganov.CandleStream;

public class RSA_StrategyStopLoss extends AbstractRSA_Strategy {

    public RSA_StrategyStopLoss(CandleStream candleStream, TradeAction tradeAction, int paperCount, int period) {
        super(candleStream, tradeAction, paperCount, period);
    }

    @Override
    public void runStrategy(StrategyParam strategyParam) {

        boolean prepareToBuy = false;
        boolean prepareToSell = false;
        int index = 0;
        Double saveRsaIndicator = 0d;

        for (double rsaIndicator : rsa.getArrayListIndicator()) {

            StringBuffer stringBuffer = new StringBuffer();
            Candle currentCandle = candleStream.getCandlesArrayList().get(index);
            stringBuffer.append(currentCandle.getData()).append(" ").append(currentCandle.getTime()).append(" RSI   ").append(currentCandle.getRsaIndicator());
            System.out.println(stringBuffer);

            float countPapers = tradeAction.getBalance().getPapers();

            if (rsaIndicator < strategyParam.getBuyLIne() && !prepareToBuy && countPapers == 0) {
                prepareToBuy = true;

                printLn("Готовимся к покупке");
            }
            if (prepareToBuy) {
                if (rsaIndicator > saveRsaIndicator) { // saveRsaIndicator
                    transactionArrayList.add(tradeAction.buy(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index)); // поменял свечу для получения цены
                    prepareToBuy = false;
                    printLn("Купили акции по цене " + candleStream.getCandlesArrayList().get(index).getCloseCandle() + candleStream.getCandlesArrayList().get(index).getData().toString());
                }
            }

            if (index > 3 && index < candleStream.getCandlesArrayList().size()-1 ) {
                if (rsaIndicator < rsa.getArrayListIndicator().get(index - 2)-8 && countPapers > 0) {
                    prepareToSell = false;
                    printLn("Stop Loss Detected ");
                    transactionArrayList.add(tradeAction.sell(paperCount, candleStream.getCandlesArrayList().get(index + 1).getOpenCandle(), index));
                    printLn("Продали акции в колличестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index + 1).getOpenCandle() + candleStream.getCandlesArrayList().get(index).getData().toString());

                }
            }

            if (rsaIndicator > 68 && !prepareToSell && countPapers > 0) {
                prepareToSell = true;
                printLn("Готовимся к продаже ");
            }

            if (prepareToSell) { // продажа при условии RSA> предыдущего RSA,
                if (rsaIndicator < saveRsaIndicator && tradeAction.getBalance().getPapers() > 0) {
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

package com.stroganov.Strategies;

import com.stroganov.Candle;
import com.stroganov.CandleStream;
import com.stroganov.Indicators.IndicatorContainer;

public class RSA_StrategyStopLoss extends AbstractRSA_Strategy {

    public RSA_StrategyStopLoss(CandleStream candleStream, TradeAction tradeAction, int paperCount, int period, IndicatorContainer container) {
        super(candleStream, tradeAction, paperCount, period, container);
    }

    @Override
    public void runStrategy(StrategyParam strategyParam) {

        boolean prepareToBuy = false;
        boolean prepareToSell = false;
        boolean logPrint = false;
        int index = 0;
        Double saveRsaIndicator = 0d;

        for (double rsaIndicator : rsa.getArrayListIndicator()) {

            if (logPrint){
                StringBuffer stringBuffer = new StringBuffer();
                Candle currentCandle = candleStream.getCandlesArrayList().get(index);
                stringBuffer.append(currentCandle.getData()).append(" ").append(currentCandle.getTime()).append(" RSI   ").append(currentCandle.getRsaIndicator());
                System.out.println(stringBuffer);
            }

            float countPapers = tradeAction.getBalance().getPapers();


            if (rsaIndicator < strategyParam.getBuyLIne() && !prepareToBuy && countPapers == 0) {
                prepareToBuy = true;
               if (logPrint)printLn("Готовимся к покупке");
            }

            if (prepareToBuy) {  // покупка акций
                if (rsaIndicator > saveRsaIndicator) { // saveRsaIndicator
                    transactionArrayList.add(tradeAction.buy(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index)); // поменял свечу для получения цены
                    prepareToBuy = false;
                   if (logPrint) printLn("Купили акции по цене " + candleStream.getCandlesArrayList().get(index).getCloseCandle() + candleStream.getCandlesArrayList().get(index).getData().toString());
                }
            }


            if (index > 3 && index < candleStream.getCandlesArrayList().size()-1 ) {
                if (rsaIndicator < rsa.getArrayListIndicator().get(index - 3)-strategyParam.getStopLoss() && countPapers > 0) { /// !!!
                    prepareToSell = false;
                    printLn("Stop Loss Detected ");
                    transactionArrayList.add(tradeAction.sell(paperCount, candleStream.getCandlesArrayList().get(index + 1).getOpenCandle(), index));
                  if (logPrint) printLn("Продали акции в колличестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index + 1).getOpenCandle() + candleStream.getCandlesArrayList().get(index).getData().toString());
                }
            }


            if (rsaIndicator > strategyParam.getSellLine() && !prepareToSell && countPapers > 0) {
                prepareToSell = true;
               if (logPrint)printLn("Готовимся к продаже ");
            }


            if (prepareToSell) { // продажа при условии RSA> предыдущего RSA,
                if (rsaIndicator < saveRsaIndicator && tradeAction.getBalance().getPapers() > 0) {  // saveRsaIndicator
                    transactionArrayList.add(tradeAction.sell(paperCount, candleStream.getCandlesArrayList().get(index + 1).getOpenCandle(), index));
                    prepareToSell = false;
                  if (logPrint) printLn("Продали акции в колличестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index + 1).getOpenCandle() + candleStream.getCandlesArrayList().get(index).getData().toString());
                }
            }


            index++;
            saveRsaIndicator = rsaIndicator;
        }

    }
}

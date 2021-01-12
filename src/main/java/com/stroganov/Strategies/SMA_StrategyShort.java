package com.stroganov.Strategies;

import com.stroganov.Candle;
import com.stroganov.CandleStream;
import com.stroganov.Indicators.IndicatorContainer;

public class SMA_StrategyShort extends AbstractStrategy {


    public SMA_StrategyShort(CandleStream candleStream, TradeAction tradeAction, int paperCount, int periodOne, int periodTwo, IndicatorContainer container) {
        super(candleStream, tradeAction, paperCount, periodOne, periodTwo, container);
    }

    @Override
    public void runStrategy(StrategyParam strategyParam) {

        int doublePaperCount = paperCount * 2;
        int position = 0;

        boolean firstTime = true;
        boolean logPrint = false;
        int index = 0;
        double saveIndicator = 0d;
        double varianceIndicator;

        for (double currentIndicator : indicatorOne.getArrayListIndicator()) {

            if (!firstTime) {
                paperCount = doublePaperCount;
            }

            Candle currentCandle = candleStream.getCandlesArrayList().get(index);
            double currentIndicatorTwo = indicatorTwo.getArrayListIndicator().get(index);
            double currentIndicatorTwoSave; // удалить
            varianceIndicator = currentIndicatorTwo - currentIndicator;


            if (logPrint) {
                StringBuilder stringBuffer = new StringBuilder();
                stringBuffer.append(currentCandle.getData()).append(" ").append(currentCandle.getTime()).append(" SMA1 ").append(currentIndicator).append(" SMA2 ").append(currentIndicatorTwo);//
                System.out.println(stringBuffer);
            }
            int countPapers = tradeAction.getBalance().getPapers();

            if (currentIndicator > 0 && currentIndicatorTwo > 0) {

                if (varianceIndicator>strategyParam.getBuyLIne() && position <= 0) { // currentCandle.getCloseCandle()// currentIndicator < currentIndicatorTwo
                    transactionArrayList.add(tradeAction.buy(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index)); // поменял свечу для получения цены
                    position = 1;
                    if (firstTime) {
                        firstTime = false;
                    }
                    if (logPrint)
                        printLn("Купили акции в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
                }

                // стоп лосс тест

           //  if (varianceIndicator>saveIndicator+strategyParam.getStopLoss()  && position > 0) {
           // //  if (currentIndicatorTwo<saveIndicator+strategyParam.getStopLoss()  && position > 0) {
           //       transactionArrayList.add(tradeAction.sell(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index));
           //       position = 0;
           //       firstTime = false;
           //       if (logPrint) {
           //           printLn("Продали акции по стоплоссу в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
           //       }
           //   }

            //   if (currentIndicatorTwo>saveIndicator+3.6f  && position < 0) {
            //       transactionArrayList.add(tradeAction.buy(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index)); // поменял свечу для получения цены
            //       position = 1;
            //       firstTime = false;
            //       if (logPrint) {
            //           printLn("Купили по стоплоссу акции в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
            //       }
            //   }

                // стоп лосс тест

                if (varianceIndicator<strategyParam.getSellLine()  && position >= 0) {
                    transactionArrayList.add(tradeAction.sell(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index));
                    position = -1;
                    if (firstTime) {
                        firstTime = false;
                    }
                    if (logPrint) {
                        printLn("Продали акции в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
                    }
                }
            }

            index++;
            saveIndicator = varianceIndicator;  // currentIndicatorTwo; // varianceIndicator;
        }


    }
}

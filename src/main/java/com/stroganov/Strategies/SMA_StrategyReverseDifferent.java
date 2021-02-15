package com.stroganov.Strategies;

import com.stroganov.Candle;
import com.stroganov.CandleStream;
import com.stroganov.Indicators.IndicatorContainer;

public class SMA_StrategyReverseDifferent extends AbstractStrategy {


    public SMA_StrategyReverseDifferent(CandleStream candleStream, TradeAction tradeAction, int paperCount, int periodOne, int periodTwo, IndicatorContainer container) {
        super(candleStream, tradeAction, paperCount, periodOne, periodTwo, container);
    }

    @Override
    public void runStrategy(StrategyParam strategyParam) {


        // int doublePaperCount = paperCount * 2;
        int position = 0;

        boolean firstTime = true;
        boolean logPrint = false;
        int index = 0;
        double saveIndicator = 0d;
        double varianceIndicator;

        for (double currentIndicatorOne : indicatorOne.getArrayListIndicator()) {

            //  if (!firstTime) {
            //     paperCount = doublePaperCount;
            // }

            Candle currentCandle = candleStream.getCandlesArrayList().get(index);
            double currentIndicatorTwo = indicatorTwo.getArrayListIndicator().get(index);
            // double smaChangeIndicator = smaChangeIndicators.getArrayListIndicator().get(index);

            varianceIndicator = currentIndicatorTwo - currentIndicatorOne;


            if (logPrint) {
                StringBuilder stringBuffer = new StringBuilder();
                stringBuffer.append(currentCandle.getData()).append(" ").append(currentCandle.getTime()).append(" SMA1 ").append(currentIndicatorOne).append(" SMA2 ").append(currentIndicatorTwo);//
                System.out.println(stringBuffer);
            }

            int countPapers = tradeAction.getBalance().getPapers();

            if (currentIndicatorOne > 0 && currentIndicatorTwo > 0) {


                if (varianceIndicator > strategyParam.getBuyLIne() && position == 0) {
                    transactionArrayList.add(tradeAction.buy(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index)); // поменял свечу для получения цены
                    position = 1;
                    if (logPrint)
                        printLn("Купили акции в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
                }


                // стоп лосс тест

                  if (varianceIndicator>saveIndicator+strategyParam.getStopLoss()  && position > 0) {
                      if (currentIndicatorTwo < saveIndicator + strategyParam.getStopLoss() && tradeAction.getBalance().getPapers() > 0) {
                          transactionArrayList.add(tradeAction.sell(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index));
                           position = 0;
                          if (logPrint) {
                              printLn("Продали акции по стоплоссу в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
                          }
                      }
                  }


                if (varianceIndicator < strategyParam.getSellLine() && position > 0) {
                    transactionArrayList.add(tradeAction.sell(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index));
                    position = 0;
                    if (logPrint) {
                        printLn("Продали акции в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
                    }
                }


                ////// sel block //////////////


                if (varianceIndicator < strategyParam.getSellLineShort() && position == 0) {
                    transactionArrayList.add(tradeAction.sell(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index));
                    position = -1;
                    if (logPrint) {
                        printLn("Продали акции в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
                    }
                }


                if (varianceIndicator > strategyParam.getBuyLIneShort() && position == -1) {
                    transactionArrayList.add(tradeAction.buy(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index)); // поменял свечу для получения цены
                    position = 0;
                    if (logPrint)
                        printLn("Купили акции в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
                }
            }

            index++;
            saveIndicator = currentIndicatorTwo;  // currentIndicatorTwo; // varianceIndicator;
        }


    }
}

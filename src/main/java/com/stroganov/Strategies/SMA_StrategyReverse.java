package com.stroganov.Strategies;

import com.stroganov.Candle;
import com.stroganov.CandleStream;
import com.stroganov.Indicators.AbstractIndicator;
import com.stroganov.Indicators.IndicatorContainer;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class SMA_StrategyReverse extends AbstractStrategy {

    private static final Logger logger = Logger.getLogger(SMA_StrategyReverse.class);


    public SMA_StrategyReverse(CandleStream candleStream, int paperCount, IndicatorContainer container) {
        super(candleStream, paperCount, container);
    }

    @Override
    public ArrayList<Transaction> runStrategy(StrategyParam strategyParam, AbstractIndicator indicatorOne, AbstractIndicator indicatorTwo) {

        TradeAction tradeAction = new TradeAction(new Balance(100000));

        ArrayList<Transaction> transactionArrayList = new ArrayList<>();

        //  SMA_Change smaChangeIndicators = new SMA_Change(candleStream,45);

        int doublePaperCount = paperCount * 2;
        int position = 0;

        boolean firstTime = true;
        boolean logPrint = false;
        int index = 0;
        double saveIndicator = 0d;
        double varianceIndicator;

        for (double currentIndicatorOne : indicatorOne.getArrayListIndicator()) {

            if (!firstTime) {
                paperCount = doublePaperCount;
            }

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


                if (varianceIndicator > strategyParam.getBuyLIne() && position <= 0) { // currentCandle.getCloseCandle()// currentIndicatorOne < currentIndicatorTwo
                    transactionArrayList.add(tradeAction.buy(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index)); // поменял свечу для получения цены
                    position = 1;
                    if (firstTime) {
                        firstTime = false;
                    }
                    if (logPrint)
                        logger.info("Купили акции в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
                }
                // }

                // стоп лосс тест

                if (varianceIndicator > saveIndicator + strategyParam.getStopLoss() && position > 0) {
                    if (currentIndicatorTwo < saveIndicator + strategyParam.getStopLoss() && tradeAction.getBalance().getPapers() > 0) {
                        paperCount = 400;
                        transactionArrayList.add(tradeAction.sell(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index));
                        // position = 0;
                        firstTime = true;
                        if (logPrint) {
                            logger.info("Продали акции по стоплоссу в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
                        }
                    }
                }

                //   if (currentIndicatorTwo>saveIndicator+0.7   && tradeAction.getBalance().getPapers()<0) {
                //       transactionArrayList.add(tradeAction.buy(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index)); // поменял свечу для получения цены
                //       paperCount = 400;
                //      // position = 1;
                //       firstTime = true;
                //       if (logPrint) {
                //           logger.info("Купили по стоплоссу акции в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
                //       }
                //   }

                // стоп лосс тест

                //   if (smaChangeIndicator<0.02) {

                if (varianceIndicator < strategyParam.getSellLine() && position >= 0) {
                    transactionArrayList.add(tradeAction.sell(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index));
                    position = -1;
                    if (firstTime) {
                        firstTime = false;
                    }
                    if (logPrint) {
                        logger.info("Продали акции в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
                    }
                }

            }

            index++;
            saveIndicator = currentIndicatorTwo;  // currentIndicatorTwo; // varianceIndicator;
        }

        return transactionArrayList;

    }
}

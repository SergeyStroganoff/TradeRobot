package com.stroganov.Strategies;

import com.stroganov.Candle;
import com.stroganov.CandleStream;
import com.stroganov.Indicators.AbstractIndicator;
import com.stroganov.Indicators.IndicatorContainer;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class SMA_Strategy extends AbstractStrategy {

    private static final Logger logger = Logger.getLogger(SMA_Strategy.class);


    public SMA_Strategy(CandleStream candleStream, TradeAction tradeAction, int paperCount, IndicatorContainer container) {
        super(candleStream, tradeAction, paperCount, container);
    }

    @Override
    public ArrayList<Transaction> runStrategy(StrategyParam strategyParam, AbstractIndicator indicatorOne, AbstractIndicator indicatorTwo) {

        ArrayList<Transaction> transactionArrayList = new ArrayList<>();

        int position = 0;
        boolean logPrint = false;
        int index = 0;
        float savePriseBuy = 0f;
        double varianceIndicator = 0;
        Candle currentCandle;

        for (double currentIndicator : indicatorOne.getArrayListIndicator()) {

            int countPapers = tradeAction.getBalance().getPapers();

            currentCandle = candleStream.getCandlesArrayList().get(index);
            double currentIndicatorTwo = indicatorTwo.getArrayListIndicator().get(index);
            double currentIndicatorTwoSave; // удалить
            varianceIndicator = currentIndicatorTwo - currentIndicator;


            if (logPrint) {
                StringBuilder stringBuffer = new StringBuilder();
                stringBuffer.append(currentCandle.getData()).append(" ").append(currentCandle.getTime()).append(" SMA1 ").append(currentIndicator).append(" SMA2 ").append(currentIndicatorTwo);//
                logger.debug(stringBuffer);
            }

            if (currentIndicator > 0 && currentIndicatorTwo > 0) {

                if (varianceIndicator > strategyParam.getBuyLIne() && position == 0) {
                    transactionArrayList.add(tradeAction.buy(paperCount, currentCandle.getCloseCandle(), index));
                    position = 1;
                    savePriseBuy = currentCandle.getCloseCandle();

                    if (logPrint) {

                        logger.debug(new StringBuilder().append("Купили акции в количестве: ").append(paperCount).append(" по цене:").append(currentCandle.getCloseCandle()).append("   ").append(candleStream.getCandlesArrayList().get(index).getData().toString()).toString());
                        logger.debug("Наша позиция " + position);
                        logger.debug("Сохранили цену по сделке" + savePriseBuy);
                    }

                }

                // тейк профит

                //   if ((currentCandle.getOpenCandle() - savePriseBuy) > strategyParam.getStopLoss() && position > 0 && candle == 0) {
                //       if (transactionArrayList.get(transactionArrayList.size() - 1).getBalance().getPapers() > 0) {

                //           transactionArrayList.add(tradeAction.sell(paperCount, currentCandle.getOpenCandle(), index));
                //            // position = 0;
                //           if (logPrint) {
                //               printLn("Продали акции по стоплоссу в количестве: " + paperCount + " по цене:" + currentCandle.getOpenCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
                //           }
                //       }
                //   }

                // стоп лосс тест

                //    if (varianceIndicator > saveIndicator + strategyParam.getStopLoss() && position > 0) {

                //          transactionArrayList.add(tradeAction.sell(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index));
                //          position = 0;
                //          if (logPrint) {
                //              logger.debug("Продали акции по стоплоссу в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
                //          }
                //      }


                if (varianceIndicator < strategyParam.getSellLine() && position > 0) {
                    if (transactionArrayList.get(transactionArrayList.size() - 1).getBalance().getPapers() > 0) {
                        transactionArrayList.add(tradeAction.sell(paperCount, currentCandle.getCloseCandle(), index));
                        position = 0;
                        if (logPrint) {
                            logger.debug("Продали акции в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
                        }
                    } else position = 0;
                }
            }

            index++;

        }
return transactionArrayList;
    }
}

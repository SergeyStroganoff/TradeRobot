package com.stroganov.Strategies;

import com.stroganov.Candle;
import com.stroganov.CandleStream;
import com.stroganov.Indicators.AbstractIndicator;
import com.stroganov.Indicators.IndicatorContainer;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class SMA_Strategy extends AbstractStrategy {

    private static final Logger logger = Logger.getLogger(SMA_Strategy.class);


    public SMA_Strategy(CandleStream candleStream, int paperCount, IndicatorContainer container, float startMoney) {
        super(candleStream, paperCount, container, startMoney);
    }


    @Override
    public ArrayList<Transaction> runStrategy(StrategyParam strategyParam, AbstractIndicator indicatorOne, AbstractIndicator indicatorTwo) {

        TradeAction tradeAction = new TradeAction(new Balance(100000));
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();

        int position = 0;
        boolean logPrint = false;
        int index = 0;
        float savePriseBuy = 0f;
        double varianceIndicator = 0;
        double saveIndicator = 0;
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
                    //  transactionArrayList.add(tradeAction.doTransaction("buy",paperCount, currentCandle.getCloseCandle(), index));
                    transactionArrayList.add(tradeAction.buy(paperCount, currentCandle.getCloseCandle(), index));
                    position = 1;
                    savePriseBuy = currentCandle.getCloseCandle();

                    if (logPrint) {
                        logger.info("Купили акции : " +
                                " по цене:" + currentCandle.getCloseCandle() +
                                " Количество акций после покупки " + tradeAction.getBalance().getPapers() +
                                " Дата сделки " + currentCandle.getData().toString());

                    } // levels of logging

                }  // вход в сделку на buy

                // stop loss

                   if (position > 0 && savePriseBuy > currentCandle.getCloseCandle()) {
                       if (transactionArrayList.get(transactionArrayList.size() - 1).getBalance().getPapers() > 0)
                           transactionArrayList.add(tradeAction.sell(paperCount, currentCandle.getCloseCandle(), index));
                     //   position = 0;

                       System.out.println("SavePrive= " + savePriseBuy +
                               " Текущая цена закрытия =  " + currentCandle.getCloseCandle() +
                               " Дата сделки " + currentCandle.getData().toString() +
                               " Время сделки " + currentCandle.getTime().toString());

                       if (logPrint) {

                           logger.info("Продали акции по стоплоссу : " +
                                   " по цене:" + currentCandle.getCloseCandle() +
                                   " Количество акций после продажи " + tradeAction.getBalance().getPapers() +
                                   " Дата сделки " + currentCandle.getData().toString());

                       }
                   }


                // стоп лосс тест

            //    if (position > 0 && saveIndicator - varianceIndicator > strategyParam.getStopLoss() && tradeAction.getBalance().getPapers() > 0) {
            //        transactionArrayList.add(tradeAction.sell(paperCount, currentCandle.getCloseCandle(), index));
            //        // position = 1; // типо мы в акциях
            //        if (logPrint) {
            //            logger.info(" Продали по стоп лоссу по коэффициенту " + (saveIndicator - varianceIndicator) +
            //                    " по цене:" + currentCandle.getCloseCandle() +
            //                    " Количество акций после продажи " + tradeAction.getBalance().getPapers() +
            //                    " Дата сделки " + currentCandle.getData().toString());
//
            //        }
            //    }


                if (varianceIndicator < strategyParam.getSellLine() && position > 0) {
                    if (transactionArrayList.get(transactionArrayList.size() - 1).getBalance().getPapers() > 0) {
                        transactionArrayList.add(tradeAction.doTransaction("sell", paperCount, currentCandle.getCloseCandle(), index));

                        if (logPrint) {

                            logger.info("Продали акции : " +
                                    " по цене:" + currentCandle.getCloseCandle() +
                                    " Количество акций после покупки " + tradeAction.getBalance().getPapers() +
                                    " Дата сделки " + currentCandle.getData().toString());
                        }

                    }
                    position = 0;
                }
            }
            index++;
            saveIndicator = varianceIndicator;
        }
        return transactionArrayList;
    }
}

// выделить общие и в абстракцию
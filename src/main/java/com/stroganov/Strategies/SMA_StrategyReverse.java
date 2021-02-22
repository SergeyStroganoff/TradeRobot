package com.stroganov.Strategies;

import com.stroganov.Candle;
import com.stroganov.CandleStream;
import com.stroganov.Indicators.AbstractIndicator;
import com.stroganov.Indicators.IndicatorContainer;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class SMA_StrategyReverse extends AbstractStrategy {

    private static final Logger logger = Logger.getLogger(SMA_StrategyReverse.class);


    public SMA_StrategyReverse(CandleStream candleStream, int paperCount, IndicatorContainer container, float startMoney) {
        super(candleStream, paperCount, container, startMoney);
    }

    @Override
    public ArrayList<Transaction> runStrategy(StrategyParam strategyParam, AbstractIndicator indicatorOne, AbstractIndicator indicatorTwo) {

        TradeAction tradeAction = new TradeAction(new Balance(100000));
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();

        //  SMA_Change smaChangeIndicators = new SMA_Change(candleStream,45);


        boolean firstTime = true;
        boolean logPrint = false;
        boolean isStopLossByIndicator = false;
        boolean isStopLossByPrice = false;


        int papers;
        int position = 0;
        int index = 0;
        double saveIndicator = 0d;
        double varianceIndicator;
        float priceSave = 0;


        for (double currentIndicatorOne : indicatorOne.getArrayListIndicator()) {
            if (firstTime) {
                papers = paperCount;
            } else papers = paperCount * 2;

            Candle currentCandle = candleStream.getCandlesArrayList().get(index);
            double currentIndicatorTwo = indicatorTwo.getArrayListIndicator().get(index);
            varianceIndicator = currentIndicatorTwo - currentIndicatorOne;
            int countPapers = tradeAction.getBalance().getPapers();


            if (logPrint) {
                StringBuilder stringBuffer = new StringBuilder();
                stringBuffer.append(currentCandle.getData()).append(" ").
                        append(currentCandle.getTime()).append(" SMA1 ").
                        append(currentIndicatorOne).append(" SMA2 ").
                        append(currentIndicatorTwo);//
                logger.debug(stringBuffer);
            }


            if (currentIndicatorOne > 0 && currentIndicatorTwo > 0) {


                /////////////////////////////// BUY BLOCK

                if (varianceIndicator > strategyParam.getBuyLIne() && position <= 0) {
                    transactionArrayList.add(tradeAction.buy(papers, currentCandle.getCloseCandle(), index)); // поменял свечу для получения цены
                    priceSave = currentCandle.getCloseCandle();
                    position = 1;
                    firstTime = false;

                    if (logPrint)
                        logger.info("Купили акции в количестве: " + papers +
                                " по цене:" + currentCandle.getCloseCandle() +
                                " Количество акций после покупки " + tradeAction.getBalance().getPapers() +
                                " Дата сделки " + currentCandle.getData().toString());
                }


                ///////////////////// стоп лосс отсечка для buy

                if (isStopLossByPrice) {

                    if (position > 0 && (currentCandle.getLowCandle() + 0.5) < priceSave && tradeAction.getBalance().getPapers() > 0) {  //TODO
                        papers = 400;
                        transactionArrayList.add(tradeAction.sell(papers, priceSave, index));
                        position = 1; // типо мы в акциях
                        firstTime = true;
                        if (logPrint) {

                            logger.info("Продали по стоп лоссу-отсечке цене " + currentCandle.getCloseCandle() +
                                    " по цене:" + priceSave +
                                    " Количество акций после продажи " + tradeAction.getBalance().getPapers() +
                                    " Дата сделки " + currentCandle.getData().toString());

                        }
                    }
                }


                //////////////////////////////////////////////////////// saveIndicator - varianceIndicator > произошло резкое снижение  просвета индикаторов

                if (isStopLossByIndicator) {
                    if (position > 0 && varianceIndicator < saveIndicator + strategyParam.getStopLoss() && tradeAction.getBalance().getPapers() > 0) {
                        papers = 400;
                        transactionArrayList.add(tradeAction.sell(papers, currentCandle.getCloseCandle(), index));
                        position = 1; // типо мы в акциях
                        firstTime = true;
                        if (logPrint) {
                            logger.info(" Продали по стоп лоссу по коэффициенту " + (saveIndicator - varianceIndicator) +
                                    " по цене:" + currentCandle.getCloseCandle() +
                                    " Количество акций после продажи " + tradeAction.getBalance().getPapers() +
                                    " Дата сделки " + currentCandle.getData().toString());
                        }
                    }
                }


                //////////////////////////////////////////////////////////// SELL BLOCK


                if (varianceIndicator < strategyParam.getSellLine() && position >= 0) {
                    transactionArrayList.add(tradeAction.sell(papers, currentCandle.getCloseCandle(), index));
                    priceSave = currentCandle.getCloseCandle();
                    position = -1;
                    firstTime = false;

                    if (logPrint) {
                        logger.info("Продали акции в количестве: " + papers +
                                " по цене:" + currentCandle.getCloseCandle() +
                                " Количество акций после покупки " + tradeAction.getBalance().getPapers() +
                                " Дата сделки " + currentCandle.getData().toString());
                    }
                }


                //    if (position < 0 && currentCandle.getCloseCandle() > priceSave && tradeAction.getBalance().getPapers() < 0) {
                //        papers = 400;
                //        transactionArrayList.add(tradeAction.buy(papers, priceSave, index));
                //        position = -1; // типо мы в акциях
                //        firstTime = true;
                //        if (logPrint) {
//
                //            logger.info("Купили по стоп лоссу-отсечке цене " + currentCandle.getCloseCandle() +
                //                    " по цене:" + priceSave +
                //                    " Количество акций после покупке " + tradeAction.getBalance().getPapers() +
                //                    " Дата сделки " + currentCandle.getData().toString());
//
                //        }
                //    }


            }

            index++;
            saveIndicator = varianceIndicator;
        }

        return transactionArrayList;

    }
}

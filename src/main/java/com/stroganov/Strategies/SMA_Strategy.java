package com.stroganov.Strategies;

import com.stroganov.Candle;
import com.stroganov.CandleStream;
import com.stroganov.Indicators.IndicatorContainer;

public class SMA_Strategy extends AbstractStrategy {


    public SMA_Strategy(CandleStream candleStream, TradeAction tradeAction, int paperCount, int periodOne, int periodTwo, IndicatorContainer container) {
        super(candleStream, tradeAction, paperCount, periodOne, periodTwo, container);
    }

    @Override
    public void runStrategy(StrategyParam strategyParam) {

        boolean logPrint = true;
        int index = 0;
        double saveIndicator = 0d;


        printLn("Период выбранного индикатора равен " + indicatorOne.getPeriod());
        printLn("Период выбранного индикатора равен " + indicatorTwo.getPeriod());


        for (double currentIndicator : indicatorOne.getArrayListIndicator()) {

            Candle currentCandle = candleStream.getCandlesArrayList().get(index);

            if (logPrint) {
                StringBuilder stringBuffer = new StringBuilder();
                stringBuffer.append(currentCandle.getData()).append(" ").append(currentCandle.getTime()).append(" SMA ").append(currentIndicator);//
                System.out.println(stringBuffer);
            }
            int countPapers = tradeAction.getBalance().getPapers();


            if (currentIndicator < indicatorTwo.getArrayListIndicator().get(index)&& countPapers == 0) { // currentCandle.getCloseCandle()
                //if (currentIndicator < currentCandle.getCloseCandle() && countPapers == 0) { // currentCandle.getCloseCandle()

                transactionArrayList.add(tradeAction.buy(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index)); // поменял свечу для получения цены
                if (logPrint)
                    printLn("Купили акции по цене " + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
            }

            if (currentIndicator > indicatorTwo.getArrayListIndicator().get(index) && countPapers > 0) {
                //  if (currentIndicator >  currentCandle.getCloseCandle() && countPapers > 0) {
                transactionArrayList.add(tradeAction.sell(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index));
                if (logPrint)
                    printLn("Продали акции в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
            }
            index++;
            saveIndicator = currentIndicator;
        }


    }
}

package com.stroganov.Strategies;

import com.stroganov.Candle;
import com.stroganov.CandleStream;
import com.stroganov.Indicators.IndicatorContainer;

public class SMA_Strategy extends AbstractStrategy {


    public SMA_Strategy(CandleStream candleStream, TradeAction tradeAction, int paperCount, int periodOne, int periodTwo, IndicatorContainer container) {
        super(candleStream, tradeAction, paperCount, periodOne, periodTwo,  container);
    }

    @Override
    public void runStrategy(StrategyParam strategyParam) {


        boolean prepareToBuy = false;
        boolean prepareToSell = false;
        boolean logPrint = true;
        int index = 0;
        Double saveIndicator = 0d;

// Удалить этот цикл
        //     for (int i =5; i < 28; i++) {
        //         AbstractIndicator indicators = container.getIndicatorByPeriod(i);
        //         System.out.println("сдесь Новый Индикаатор " + i + " ??????????????????????????????????????????????????????");
        //         for (double dobleIndicator:indicators.getArrayListIndicator()) {
        //             System.out.println("Period " + i+"Indicator: "+ dobleIndicator);
        //         }
        //     }

        printLn("Период выбранного индикатора равен " + indicatorOne.getPeriod());
        printLn("Период выбранного индикатора равен " + indicatorTwo.getPeriod());


        for (double currentIndicator : indicatorOne.getArrayListIndicator()) {

            Candle currentCandle = candleStream.getCandlesArrayList().get(index);

            if (logPrint) {
                StringBuilder stringBuffer = new StringBuilder();
                stringBuffer.append(currentCandle.getData()).append(" ").append(currentCandle.getTime()).append(" SMA ").append(currentIndicator);//
                System.out.println(stringBuffer);
            }
            float countPapers = tradeAction.getBalance().getPapers();


            if (currentIndicator < indicatorTwo.getArrayListIndicator().get(index) && countPapers == 0) { // currentCandle.getCloseCandle()
            //if (currentIndicator < currentCandle.getCloseCandle() && countPapers == 0) { // currentCandle.getCloseCandle()

                transactionArrayList.add(tradeAction.buy(paperCount, candleStream.getCandlesArrayList().get(index).getCloseCandle(), index)); // поменял свечу для получения цены
                if (logPrint)
                    printLn("Купили акции по цене " + candleStream.getCandlesArrayList().get(index).getCloseCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());
            }


            if (currentIndicator >  indicatorTwo.getArrayListIndicator().get(index) && countPapers > 0) {
          //  if (currentIndicator >  currentCandle.getCloseCandle() && countPapers > 0) {
                transactionArrayList.add(tradeAction.sell(paperCount, candleStream.getCandlesArrayList().get(index + 1).getOpenCandle(), index));
                if (logPrint)
                    printLn("Продали акции в количестве: " + paperCount + " по цене:" + candleStream.getCandlesArrayList().get(index + 1).getOpenCandle() + "   " + candleStream.getCandlesArrayList().get(index).getData().toString());

            }

            index++;
            saveIndicator = currentIndicator;
        }


    }
}

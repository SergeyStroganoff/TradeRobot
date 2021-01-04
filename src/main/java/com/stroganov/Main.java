package com.stroganov;

import com.stroganov.Grafics.GraphicOfBalance;
import com.stroganov.Grafics.GraphicOfTransactions;
import com.stroganov.Grafics.Report;
import com.stroganov.Indicators.IndicatorContainer;
import com.stroganov.Strategies.StartStrategy;
import com.stroganov.Strategies.StartStrategyRSA_StopLoss;
import com.stroganov.Strategies.StrategyParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // write your code here

        String fileName = "Data/SBER_1H-2014-2020.txt";  //SBER_all2020_1H.txt
        CandleStream candleStream = new CandleStream(fileName);
        IndicatorContainer container = new IndicatorContainer(candleStream);
        System.out.println(candleStream.getCandlesArrayList().size());


        Map<Report, StrategyParam> resultMapStrategy = new HashMap<>();
        StartStrategy startStrategy = new StartStrategyRSA_StopLoss(candleStream, container, 100000);
        StrategyParam strategyParam;

        ////

        long startTime = System.nanoTime();


        for (int period = 7; period < 14; period++) {
            for (int sellLine = 68; sellLine <= 72; sellLine++) {
                for (int buyLine = 28; buyLine <= 34; buyLine++) {
                    for (int stopLoss = -5; stopLoss <= 16; stopLoss++) {
                        strategyParam = new StrategyParam(period, buyLine, sellLine,stopLoss);//13,30,68,8 StrategyParam{periodRSA=11, buyLIne=29, sellLine=72, stopLoss=12}
                        resultMapStrategy.put(startStrategy.testStrategy(strategyParam), strategyParam);
                    }
                }
           }
        }

        long finishTime = System.nanoTime();
        long calcTime = finishTime-startTime;
        System.out.println("ВРЕМЯ выполнения расчетов составило:" + (calcTime/1000000));


/////
            Collection<Report> reportCollection = resultMapStrategy.keySet();
            ArrayList<Report> reportArrayList = new ArrayList<>(reportCollection);

            int indexOfBestReport = 0;
            double bestBalance = 0;

            for (int i = 0; i < reportArrayList.size(); i++) {
                double finishBalance = reportArrayList.get(i).getFinishBalance();
                if (bestBalance < finishBalance) {
                    bestBalance = finishBalance;
                    indexOfBestReport = i;
                }
            }

            Report bestReport = reportArrayList.get(indexOfBestReport);
            bestReport.printReport();

            ///////
            System.out.println(resultMapStrategy.get(bestReport).toString());

        System.out.println("Количество перебранных вариантов стратегии:" + resultMapStrategy.size());
        System.out.println("Время расчета одной стратегии составило" + calcTime/resultMapStrategy.size()/1000000);


            GraphicOfTransactions.drawGraphic(bestReport);
            GraphicOfBalance.drawGraphic(bestReport.getTransactionArrayList());


        }
    }




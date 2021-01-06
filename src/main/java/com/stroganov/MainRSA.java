package com.stroganov;

import com.stroganov.Grafics.GraphicOfBalance;
import com.stroganov.Grafics.GraphicOfTransactions;
import com.stroganov.Grafics.Report;
import com.stroganov.Indicators.IndicatorContainer;
import com.stroganov.Indicators.Indicators;
import com.stroganov.Strategies.StartStrategy;
import com.stroganov.Strategies.StartStrategyNow;
import com.stroganov.Strategies.StrategyParam;

import java.util.*;

public class MainRSA {

    public static void main(String[] args) {
        // write your code here

        String fileName = "Data/SBER_all2020_1H.txt";  //SBER_all2020_1H.txt
        CandleStream candleStream = new CandleStream(fileName);
        IndicatorContainer containerRSA = new IndicatorContainer(candleStream, Indicators.RSA);
        System.out.println(candleStream.getCandlesArrayList().size());
        Map<Report, StrategyParam> resultMapStrategy = new HashMap<>();
        StrategyParam strategyParam;

        StartStrategy startStrategy = new StartStrategyNow(candleStream, containerRSA, 100000);

        ////
        long startTime = System.nanoTime();


        for (int periodOne = 10; periodOne < 16; periodOne++) {
            for (int sellLine = 68; sellLine <= 72; sellLine++) {
                for (int buyLine = 28; buyLine <= 34; buyLine++) {
                    //     for (int stopLoss = -5; stopLoss <= 16; stopLoss++) {
                    strategyParam = new StrategyParam(periodOne, 0, buyLine, sellLine, 8);//13,30,68,8 StrategyParam{periodRSA=11, buyLIne=29, sellLine=72, stopLoss=12}
                    resultMapStrategy.put(startStrategy.testStrategy(strategyParam,"RSA_StrategyStopLoss"), strategyParam);
                    //     }
                }
            }
        }

        long finishTime = System.nanoTime();
        long calcTime = finishTime - startTime;
        System.out.println("ВРЕМЯ выполнения расчетов составило:" + (calcTime / 1000000));

        System.out.println("Количество перебранных вариантов стратегии:" + resultMapStrategy.size());
        System.out.println("Время расчета одной стратегии составило" + calcTime / resultMapStrategy.size() / 1000000);


        Report bestReport = getBestReport(resultMapStrategy); //
        bestReport.printReport();


        System.out.println(resultMapStrategy.get(bestReport).toString());

        GraphicOfTransactions.drawGraphic(bestReport);
        GraphicOfBalance.drawGraphic(bestReport.getTransactionArrayList());

    }

    public static Report getBestReport(Map<Report, StrategyParam> resultMapStrategy) {
        Report bestReport = null;

        Collection<Report> reportCollection = resultMapStrategy.keySet();
        ArrayList<Report> reportArrayList = new ArrayList<>(reportCollection);
        bestReport = Collections.max(reportArrayList, Report.compareReportByMaxBalance());

        return bestReport;
    }
}



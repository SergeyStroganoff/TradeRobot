package com.stroganov;

import com.stroganov.Grafics.GraphicOfBalance;
import com.stroganov.Grafics.GraphicOfTransactions;
import com.stroganov.Grafics.Report;
import com.stroganov.Indicators.IndicatorContainer;
import com.stroganov.Indicators.Indicators;
import com.stroganov.Strategies.StartStrategyNow;
import com.stroganov.Strategies.StrategyParam;

import java.util.*;

public class MainSMA {

    public static void main(String[] args) {

        String fileName = "Data/SBER_all2020_1H.txt";  //SBER_all2020_1H.txt //SBER_201101_210106.txt
        CandleStream candleStream = new CandleStream(fileName);
        IndicatorContainer containerSMA = new IndicatorContainer(candleStream, Indicators.SMA);
        System.out.println(candleStream.getCandlesArrayList().size());
        Map<Report, StrategyParam> resultMapStrategy = new HashMap<>();
        StrategyParam strategyParam;

        StartStrategyNow startStrategy = new StartStrategyNow(candleStream, containerSMA, 100000);

        ////
        long startTime = System.nanoTime();


        for (int periodOne = 9; periodOne < 29; periodOne++) {
            //for (int sellLine = 68; sellLine <= 72; sellLine++) {
            for (int buyLine = 0; buyLine < 1; buyLine++) {
                //     for (int stopLoss = -5; stopLoss <= 16; stopLoss++) {
                strategyParam = new StrategyParam(periodOne,0, buyLine, 0, 0);
                resultMapStrategy.put(startStrategy.testStrategy(strategyParam, "SMA_Strategy"), strategyParam);
                //     }
            }
            //  }
        }

        long finishTime = System.nanoTime();
        long calcTime = finishTime - startTime;
        System.out.println("ВРЕМЯ выполнения расчетов составило: " + (calcTime / 1000000));

        System.out.println("Количество перебранных вариантов стратегии: " + resultMapStrategy.size());
        System.out.println("Время расчета одной стратегии составило " + calcTime / resultMapStrategy.size() / 1000000);

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


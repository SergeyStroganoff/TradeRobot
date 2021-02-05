package com.stroganov;

import com.stroganov.Grafics.GraphicOfBalance;
import com.stroganov.Grafics.Report;
import com.stroganov.Indicators.IndicatorContainer;
import com.stroganov.Indicators.Indicators;
import com.stroganov.Strategies.StartStrategyNow;
import com.stroganov.Strategies.Strategies;
import com.stroganov.Strategies.StrategyParam;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class MainSMA {

    public static void main(String[] args) {

        boolean testPaparam = true;

   //     String fileName = "Data/" + "SBER_210101_210201.txt";  //"SBER_210101_1501.txt";//"SBER_180301_180901down.txt";  //SBER_all2020_1H.txt //SBER_201101_210106.txt// SBER-30M-2020.txt //Data/SBER_1H-2014-2020.txt // Data/SBER_201101_210106t.txt
   //     CandleStream candleStream = new CandleStream(fileName);

        LocalDate dateFrom = LocalDate.of(2017,9,1);
        LocalDate dateTo =  LocalDate.of(2018,8,27);
        CandleStream candleStream = new CandleStream("SBER",60,dateFrom,dateTo);
        IndicatorContainer containerSMA = new IndicatorContainer(candleStream, Indicators.SMA);
        System.out.println("Длина свечного стрима: " + candleStream.getCandlesArrayList().size());
        Map<Report, StrategyParam> resultMapStrategy = new HashMap<>();
        StrategyParam strategyParam;

        StartStrategyNow startStrategy = new StartStrategyNow(candleStream, containerSMA, 100000);
        Strategies strategies = Strategies.SMA_STRATEGY_REVERSE;

        ////
        long startTime = System.nanoTime();

        if (testPaparam) {

            for (int periodOne = 8; periodOne < 12; periodOne++) { //9-12
                for (int periodTwo = 5; periodTwo < 8; periodTwo++) { //5-8
                    for (float buyLine = -0.4f; buyLine < 0.2f; buyLine += 0.1) {
                        for (float sellLine = -0.5f; sellLine < 0.0f; sellLine += 0.1) {
                            for (float stopLoss =  -0.0f; stopLoss < 0.5f; stopLoss += 0.02) {
                                strategyParam = new StrategyParam(periodOne, periodTwo, buyLine, sellLine, stopLoss);
                                resultMapStrategy.put(startStrategy.testStrategy(strategyParam, strategies), strategyParam);
                            }
                        }
                    }
                }
            }

        } else {
          //  strategyParam = new StrategyParam(9, 5, -0.1f, -0.45f, 0.04f);
            strategyParam = new StrategyParam(9, 5, -0.0f, 0.1f, -0.0f);
            resultMapStrategy.put(startStrategy.testStrategy(strategyParam, strategies), strategyParam);
        }

        long finishTime = System.nanoTime();
        long calcTime = finishTime - startTime;

        System.out.println("ВРЕМЯ выполнения расчетов составило: " + (calcTime / 1000000));
        System.out.println("Количество перебранных вариантов стратегии: " + resultMapStrategy.size());
        System.out.println("Время расчета одной стратегии составило " + calcTime / resultMapStrategy.size() / 1000000);

        Report bestReport = getBestReport(resultMapStrategy); //
        bestReport.printReport();

        System.out.println(resultMapStrategy.get(bestReport).toString());
        // GraphicOfTransactions.drawGraphic(bestReport);
        GraphicOfBalance.drawGraphic(bestReport.getTransactionArrayList());
        // GraphicOfIndicator.drawGraphic(containerSMA.getIndicatorByPeriod(7).getArrayListIndicator(),containerSMA.getIndicatorByPeriod(5).getArrayListIndicator()); //containerSMA.getIndicatorByPeriod(7).getArrayListIndicator()


        try {
            bestReport.saveReportToFile("saveTransaction.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Report getBestReport(Map<Report, StrategyParam> resultMapStrategy) {
        Report bestReport = null;

        Collection<Report> reportCollection = resultMapStrategy.keySet();
        ArrayList<Report> reportArrayList = new ArrayList<>(reportCollection);
        bestReport = Collections.max(reportArrayList, Report.compareReportByMaxBalance());
       // bestReport = Collections.max(reportArrayList, Report.compareReportByGoodDeal());

        return bestReport;
    }
}


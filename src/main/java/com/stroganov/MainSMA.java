package com.stroganov;

import com.stroganov.Grafics.GraphicOfBalance;
import com.stroganov.Grafics.Report;
import com.stroganov.Indicators.IndicatorContainer;
import com.stroganov.Indicators.Indicators;
import com.stroganov.Strategies.StartStrategyNow;
import com.stroganov.Strategies.Strategies;
import com.stroganov.Strategies.StrategyParam;

import java.io.IOException;
import java.util.*;

public class MainSMA {

    public static void main(String[] args) {

        boolean testPaparam = true;

        String fileName = "Data/" + "SBER_all2020_1H.txt";  //"SBER_210101_1501.txt";//"SBER_180301_180901down.txt";  //SBER_all2020_1H.txt //SBER_201101_210106.txt// SBER-30M-2020.txt //Data/SBER_1H-2014-2020.txt // Data/SBER_201101_210106t.txt
        CandleStream candleStream = new CandleStream(fileName);
        IndicatorContainer containerSMA = new IndicatorContainer(candleStream, Indicators.SMA);
        System.out.println(candleStream.getCandlesArrayList().size());
        Map<Report, StrategyParam> resultMapStrategy = new HashMap<>();
        StrategyParam strategyParam;

        StartStrategyNow startStrategy = new StartStrategyNow(candleStream, containerSMA, 100000);
        Strategies strategies = Strategies.SMA_STRATEGY_REVERSE;

        ////
        long startTime = System.nanoTime();

        if (testPaparam) {

            for (int periodOne = 8; periodOne < 14; periodOne++) { //9-12
                for (int periodTwo = 5; periodTwo < 6; periodTwo++) { //5-8
                    for (float buyLine = -0.2f; buyLine < 0.2f; buyLine += 0.1) {
                        for (float sellLine = -0.6f; sellLine < 0.2f; sellLine += 0.1) {
                            for (float stopLoss =  -0.5f; stopLoss < 0.6f; stopLoss += 0.1) {
                                strategyParam = new StrategyParam(periodOne, periodTwo, buyLine, sellLine, stopLoss);
                                resultMapStrategy.put(startStrategy.testStrategy(strategyParam, strategies), strategyParam);
                            }
                        }
                    }
                }
            }

        } else {
            strategyParam = new StrategyParam(9, 5, -0.1f, -0.4f, 0.0f);
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


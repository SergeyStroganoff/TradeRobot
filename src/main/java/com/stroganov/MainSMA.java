package com.stroganov;

import com.stroganov.Grafics.GraphicOfBalance;
import com.stroganov.Grafics.Report;
import com.stroganov.Indicators.IndicatorContainer;
import com.stroganov.Indicators.Indicators;
import com.stroganov.Strategies.TestStrategy;
import com.stroganov.Strategies.Strategies;
import com.stroganov.Strategies.StrategyParam;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class MainSMA {
    private static final Logger logger = Logger.getLogger(MainSMA.class);

    public static void main(String[] args) {

        logger.info("Модуль MainSMA Запущен успешно");
        boolean testParam = false;

        LocalDate dateFrom = LocalDate.of(2014, 1, 1);
        LocalDate dateTo = LocalDate.of(2021, 2, 11);
        CandleStream candleStream = new CandleStream("SBER", 1440, dateFrom, dateTo);
        IndicatorContainer containerSMA = new IndicatorContainer(candleStream, Indicators.SMA);

        logger.info("Успешно загружена и используется Длина свечного стрима: " + candleStream.getCandlesArrayList().size());

        Map<Report, StrategyParam> resultMapStrategy = new HashMap<>();
        StrategyParam strategyParam;

        TestStrategy runnerStrategy = new TestStrategy(candleStream, containerSMA, 100000);
        Strategies strategy = Strategies.SMA_STRATEGY;

        ////
        long startTime = System.nanoTime();

        if (testParam) {

            for (int periodOne = 8; periodOne < 45; periodOne++) { //9-12
                for (int periodTwo = 5; periodTwo < 8; periodTwo++) { //5-8
                    for (float buyLine = -0.2f; buyLine < 0.4f; buyLine += 0.1) {
                        for (float sellLine = -0.2f; sellLine < 0.4f; sellLine += 0.1) {
                            for (float stopLoss = -0.0f; stopLoss < 0.5f; stopLoss += 0.1) {
                                strategyParam = new StrategyParam(periodOne, periodTwo, buyLine, sellLine, stopLoss);      // new object ore reset the field values !!!
                                resultMapStrategy.put(runnerStrategy.runStrategy(strategyParam, strategy), strategyParam);
                            }
                        }
                    }
                }
            }

        } else {
            //  strategyParam = new StrategyParam(9, 5, -0.1f, -0.45f, 0.04f);
            // {periodOne=8, periodTwo=7, buyLIne=-0.2, sellLine=0.10000002, buyLIneShort=0.0, sellLineShort=0.0, stopLoss=-1.6391278E-8} - ???
            strategyParam = new StrategyParam(8, 5, 0.00f, 0.0f, 0.0f);
            resultMapStrategy.put(runnerStrategy.runStrategy(strategyParam, strategy), strategyParam);
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

        logger.info("Модуль отработал успешно");
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


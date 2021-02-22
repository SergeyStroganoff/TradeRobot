package com.stroganov;

import com.stroganov.Grafics.GraphicOfBalance;
import com.stroganov.Grafics.Report;
import com.stroganov.Indicators.IndicatorContainer;
import com.stroganov.Indicators.Indicators;
import com.stroganov.Strategies.AbstractStrategy;
import com.stroganov.Strategies.Strategies;
import com.stroganov.Strategies.StrategyFabric;
import com.stroganov.Strategies.StrategyParam;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class MainSMA {
    private static final Logger logger = Logger.getLogger(MainSMA.class);

    public static void main(String[] args) {

        logger.setLevel(Level.INFO);

        logger.info("Модуль MainSMA Запущен успешно");
        boolean testParam = false;

        LocalDate dateFrom = LocalDate.of(2021, 1, 1);
        LocalDate dateTo = LocalDate.of(2021, 2, 22);
        CandleStream candleStream = new CandleStream("SBER", 60, dateFrom, dateTo);
        IndicatorContainer indicatorContainer = new IndicatorContainer(candleStream, Indicators.SMA);

        logger.info("Успешно загружена и используется Длина свечного стрима: " + candleStream.getCandlesArrayList().size());

        Map<Report, StrategyParam> resultMapStrategy = new HashMap<>();
        StrategyParam strategyParam;
       // Strategies strategy = Strategies.SMA_STRATEGY_REVERSE;
        Strategies strategy = Strategies.SMA_STRATEGY_REVERSE;
        AbstractStrategy workStrategy = new StrategyFabric().createStrategy(candleStream, indicatorContainer, strategy, 100000);


        ////
        long startTime = System.nanoTime();

        if (testParam) {

            for (int periodOne = 8; periodOne < 18; periodOne++) { //9-12
                for (int periodTwo = 4; periodTwo < 8; periodTwo++) { //5-8
                    for (float buyLine = -0.4f; buyLine < 0.4f; buyLine += 0.1) {
                        for (float sellLine = -0.4f; sellLine < 0.4f; sellLine += 0.1) {
                         //   for (float stopLoss = -0.0f; stopLoss < 0.5f; stopLoss += 0.05) {
                                strategyParam = new StrategyParam(periodOne, periodTwo, buyLine, sellLine, 0);      // new object ore reset the field values !!!
                               // strategyParam = new StrategyParam(periodOne, periodTwo, 0, 0, 0);      // new object ore reset the field values !!!
                                resultMapStrategy.put(workStrategy.testStrategyGetReport(strategyParam), strategyParam);
                          //  }
                        }
                    }
                }
            }
            // relocate into properties ///


        } else {
           // periodOne=9, periodTwo=5, buyLIne=-0.10000002, sellLine=-0.4, buyLIneShort=0.0, sellLineShort=0.0, stopLoss=-0.0}
            strategyParam = new StrategyParam(13, 6, 0.0f, -0.2f, 0.0f);
            resultMapStrategy.put(workStrategy.testStrategyGetReport(strategyParam), strategyParam);
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


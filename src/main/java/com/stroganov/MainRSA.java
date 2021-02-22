package com.stroganov;

import com.stroganov.Grafics.GraphicOfBalance;
import com.stroganov.Grafics.GraphicOfTransactions;
import com.stroganov.Grafics.Report;
import com.stroganov.Indicators.IndicatorContainer;
import com.stroganov.Indicators.Indicators;
import com.stroganov.Strategies.AbstractStrategy;
import com.stroganov.Strategies.Strategies;
import com.stroganov.Strategies.StrategyFabric;
import com.stroganov.Strategies.StrategyParam;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.*;

public class MainRSA {

    private static final Logger logger = Logger.getLogger(MainRSA.class);

    public static void main(String[] args) {


        boolean testParam = true;

        LocalDate dateFrom = LocalDate.of(2021, 1, 1);
        LocalDate dateTo = LocalDate.of(2021, 2, 11);
        CandleStream candleStream = new CandleStream("SBER", 60, dateFrom, dateTo);

        IndicatorContainer indicatorContainer = new IndicatorContainer(candleStream, Indicators.RSA);
        logger.info("Успешно загружена и используется Длина свечного стрима: " + candleStream.getCandlesArrayList().size());
        Map<Report, StrategyParam> resultMapStrategy = new HashMap<>();
        StrategyParam strategyParam;

        Strategies strategy = Strategies.RSA_STRATEGY_STOP;
        AbstractStrategy workStrategy = new StrategyFabric().createStrategy(candleStream,indicatorContainer,strategy,100000);



        ////
        long startTime = System.nanoTime();

        if (testParam) {
            for (int periodOne = 10; periodOne < 16; periodOne++) {
                for (int sellLine = 68; sellLine <= 72; sellLine++) {
                    for (int buyLine = 28; buyLine <= 34; buyLine++) {
                        for (int stopLoss = -5; stopLoss <= 16; stopLoss++) {
                            strategyParam = new StrategyParam(periodOne, 0, buyLine, sellLine, stopLoss);//13,30,68,8 StrategyParam{periodRSA=11, buyLIne=29, sellLine=72, stopLoss=12}
                            resultMapStrategy.put(workStrategy.testStrategyGetReport(strategyParam), strategyParam);
                        }
                    }
                }
            }
        } else {
            strategyParam = new StrategyParam(13, 0, 30, 72, 8);//13,30,68,8 StrategyParam{periodRSA=11, buyLIne=29, sellLine=72, stopLoss=12}
            resultMapStrategy.put(workStrategy.testStrategyGetReport(strategyParam), strategyParam);
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




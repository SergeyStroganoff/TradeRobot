package com.stroganov;

import com.stroganov.Grafics.GraphicOfBalance;
import com.stroganov.Grafics.GraphicOfTransactions;
import com.stroganov.Grafics.Report;
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

        String fileName = "Data/SBER_all2020_1H.txt";  //SBER_all2020_1H.txt
        CandleStream candleStream = new CandleStream(fileName);
        System.out.println(candleStream.getCandlesArrayList().size());


        Map<Report, StrategyParam> resultMapStrategy = new HashMap<>();
        StartStrategy startStrategy = new StartStrategyRSA_StopLoss(candleStream, 100000);
        StrategyParam strategyParam;

        ////



            for (int i = 10; i <= 35; i++) {
                for (int sell = 65; sell <= 100; sell++) {
                    strategyParam = new StrategyParam(13, i, sell, 8);//13,31,71,8
                    resultMapStrategy.put(startStrategy.startStrategy(strategyParam), strategyParam);
                }
            }


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


        //// стратегия
        // Balance balance = new Balance(100000.0);
        // Report report = new Report(balance);
        // TradeAction tradeAction = new TradeAction(balance);

        // AbstractRSA_Strategy rsa_strategyStopLoss = new RSA_StrategyStopLoss(candleStream, tradeAction, 400, 14);
        // rsa_strategyStopLoss.runStrategy(10, 90);
        // if (report.prepareReport(rsa_strategyStopLoss.getTransactionArray())){report.printReport();} else {
        //     System.out.println("С отчетом что то не так!!!");
        // }

         GraphicOfTransactions.drawGraphic(bestReport);
        // GraphicOfBalance.drawGraphic(rsa_strategyStopLoss.getTransactionArray());


    }


}




package com.stroganov;

import com.stroganov.Grafics.GraphicOfBalance;
import com.stroganov.Grafics.GraphicOfTransactions;
import com.stroganov.Strategies.*;
import com.stroganov.Grafics.Report;

public class Main {

    public static void main(String[] args) {
        // write your code here

        String fileName = "Data/SBER_all2020_1H.txt";  //"Data/testcandle14.txt"; //"Data/SBER_200101_200920.txt";
        CandleStream candleStream = new CandleStream(fileName);
        System.out.println(candleStream.getCandlesArrayList().size());


        //// стратегия
        Balance balance = new Balance(100000.0);
        Report report = new Report(balance);
        TradeAction tradeAction = new TradeAction(balance);

        AbstractRSA_Strategy rsa_strategyStopLoss = new RSA_StrategyStopLoss(candleStream, tradeAction, 400, 14);
        rsa_strategyStopLoss.startAbstractStrategy(10, 90);
        if (report.prepareReport(rsa_strategyStopLoss.getTransactionArray())){report.printReport();} else {
            System.out.println("С отчетом что то не так!!!");
        }





       // GraphicOfTransactions.drawGraphic(report);
       // GraphicOfBalance.drawGraphic(rsa_strategyStopLoss.getTransactionArray());









    }

}




package com.stroganov.Grafics;

import com.stroganov.Strategies.Balance;
import com.stroganov.Strategies.Transaction;
import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Report {

    private double startMoney;
    private double finishBalance;
    private Balance balance;
    private ArrayList<Transaction> transactionArrayList;
    int countTransactions;
    private ArrayList<Double> changesAllBalance = new ArrayList<>();
    double bestTransaction;
    double worseTransaction;
    int countGoodDeal;
    int countBadDeal;
    double profitPercentage;


    public Report(Balance startBalance) {
        this.balance = startBalance;
        this.startMoney = balance.getFreeMoney();
    }

    public int getCountTransactions() {
        return countTransactions;
    }

    public ArrayList<Double> getChangesAllBalance() {
        return changesAllBalance;
    }

    public double getBestTransaction() {
        return bestTransaction;
    }

    public double getWorseTransaction() {
        return worseTransaction;
    }

    public ArrayList<Transaction> getTransactionArrayList() {
        return transactionArrayList;
    }

    public double getFinishBalance() {
        return finishBalance;
    }

    public double getProfitPercentage() {
        return profitPercentage;
    }

    public int getCountGoodDeal() {
        return countGoodDeal;
    }


    public void printLn(String string) {
        System.out.println(string);
    }


    public boolean prepareReport(@NotNull ArrayList<Transaction> transactionArrayList) {
        boolean allRight = true;
        this.transactionArrayList = transactionArrayList;
        if (transactionArrayList.isEmpty()) {
            return false;
        }
        double lastTransactionPrice = transactionArrayList.get(transactionArrayList.size() - 1).getPrice();
        finishBalance = balance.getAllBalance(lastTransactionPrice);
        countTransactions = transactionArrayList.size();

        for (int i = 0; i < transactionArrayList.size() - 1; i++) {                         // при односторонних сделках i=+2;
            double balanceFirst = transactionArrayList.get(i).getBalance().getAllBalance();
            double balanceNext = transactionArrayList.get(i + 1).getBalance().getAllBalance();
            changesAllBalance.add(balanceNext - balanceFirst);
        }

        if (changesAllBalance.isEmpty()) return false;
        bestTransaction = Collections.max(changesAllBalance);
        worseTransaction = Collections.min(changesAllBalance);

        for (double deal : changesAllBalance) {

            if (deal > 0) {
                countGoodDeal++;
            } else {
                countBadDeal++;
            }

            profitPercentage = (finishBalance - startMoney) / startMoney * 100;

        }

        return allRight;
    }

    public void printReport() {
        printLn("Начальный баланс средств: " + startMoney);
        printLn("Итоговый баланс средств: " + String.format("%.2f", finishBalance));
        printLn("Количество сделок: " + getCountTransactions());
        printLn("Лучшая сделка: " + String.format("%.2f", getBestTransaction()));
        printLn("Худшая сделка: " + String.format("%.2f", getWorseTransaction()));
        printLn("Количество сделок в плюс: " + countGoodDeal);
        printLn("Количество сделок в минус: " + countBadDeal);
        printLn("Прибыльность стратегии в процентах: " + String.format("%.2f", getProfitPercentage()) + "%");

    }


    public void saveReportToFile(String fileName) throws IOException {

        FileWriter fileWriter = new FileWriter(fileName);

        for (Transaction transaction : transactionArrayList) {

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(transaction.getTradeDirect()).append(";").append(transaction.getCountPapers()).
                    append(";").append(transaction.getPrice()).append(";").append(transaction.getBalance().getAllBalance()).append("\n");
            fileWriter.write(stringBuilder.toString());
        }
    }

    public static Comparator<Report> compareReportByMaxBalance() {
        return (o1, o2) -> (int) (o1.getFinishBalance() - o2.getFinishBalance());
    }

    public static Comparator<Report> compareReportByProfitPercentage() {
        return (o1, o2) -> (int) (o1.getProfitPercentage() - o2.getProfitPercentage());
    }

    public static Comparator<Report> compareReportByGoodDeal() {
        return (o1, o2) -> (int) (o1.getCountGoodDeal() - o2.getCountGoodDeal());
    }


}

package com.stroganov.Grafics;

import com.stroganov.Strategies.Transaction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicOfBalance {
    public static void drawGraphic(ArrayList<Transaction> arrayOfTransaction) {

        XYSeries series = new XYSeries("Transactions");

        for (int i = 0; i < arrayOfTransaction.size(); i+=2) {
            series.add(i, arrayOfTransaction.get(i).getBalance().getAllBalance());
        }

        XYSeries series2 = new XYSeries("Transactions");//

        for (int i = 0; i < arrayOfTransaction.size(); i++) {//
            series2.add(i, arrayOfTransaction.get(i).getBalance().getAllBalance() / 1.5);// данные для второго графика
        }//


        XYSplineRenderer r1 = new XYSplineRenderer();//
        r1.setPrecision(2);//
        r1.setSeriesShapesVisible(0, false);//


        XYDataset xyDataset = new XYSeriesCollection(series);
        XYDataset xyDataset2 = new XYSeriesCollection(series2);//


        JFreeChart chart = ChartFactory.createXYLineChart("График денежного баланса", "Транзакции", "Рубли", null, PlotOrientation.VERTICAL, true, true, true);
        final XYPlot plot = chart.getXYPlot();//

        plot.setDataset(0, xyDataset);
      //  plot.setDataset(1, xyDataset2);
      //  plot.setRenderer(0, r1); //устанавливаем сглаживание


        chart.setBackgroundPaint(Color.white);//
        JFrame frame = new JFrame("TransactionsByStrategy");
        // Помещаем график на фрейм
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(800, 600);
        frame.show();
    }
}



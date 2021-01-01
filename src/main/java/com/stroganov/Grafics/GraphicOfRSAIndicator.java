package com.stroganov.Grafics;

import com.stroganov.Strategies.Transaction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.ArrayList;

public class GraphicOfRSAIndicator {


    public static void drawGraphic(ArrayList<Double> arrayListIndicator) {
        XYSeries series = new XYSeries("Transactions");

        for (int i = 2000; i < arrayListIndicator.size(); i++) {
            series.add(i, arrayListIndicator.get(i));
        }

        XYDataset xyDataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart("График RSI", "RSI Index", "Значение", xyDataset, PlotOrientation.VERTICAL, true, true, true);
        JFrame frame = new JFrame("RSIByStrategy");
        // Помещаем график на фрейм
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(800, 600);
        frame.show();
    }



}

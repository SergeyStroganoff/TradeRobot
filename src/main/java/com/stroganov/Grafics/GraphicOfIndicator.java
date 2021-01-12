package com.stroganov.Grafics;

import com.stroganov.Strategies.Transaction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.ArrayList;

public class GraphicOfIndicator {


    public static void drawGraphic(ArrayList<Double> indicatorOne, ArrayList<Double> indicatorTwo) {
        XYSeries series1 = new XYSeries("Indicator");

        for (int i = 550; i < indicatorOne.size(); i++) {
            series1.add(i, indicatorOne.get(i));
        }


        XYSeries series2 = new XYSeries("Indicator");

        for (int i = 550; i < indicatorTwo.size(); i++) {
            series2.add(i, indicatorTwo.get(i));
        }

        XYDataset xyDataset = new XYSeriesCollection(series1);
        XYDataset xyDataset2 = new XYSeriesCollection(series2);//

        JFreeChart chart = ChartFactory.createXYLineChart("График индикатора", "Index", "Значение", null, PlotOrientation.VERTICAL, true, true, true);

        final XYPlot plot = chart.getXYPlot();//

        plot.setDataset(0, xyDataset);
        plot.setDataset(1, xyDataset2);

        JFrame frame = new JFrame("RSIByStrategy");
        // Помещаем график на фрейм
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(800, 600);
        frame.show();
    }



}

package com.stroganov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CandleStream {


    private final ArrayList<Candle> candlesArrayList = new ArrayList<>();

    public CandleStream(String fileName) {

        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            String stringFromFile = fileReader.readLine();
            System.out.println(stringFromFile + " Заголовок");
            while ((stringFromFile = fileReader.readLine()) != null) {
                String[] buf = stringFromFile.split(",");
                candlesArrayList.add(new Candle(buf[0], Integer.parseInt(buf[1]), DateFromString.GetDateFromString(buf[2]), Integer.parseInt(buf[3]), Float.parseFloat(buf[4]),
                        Float.parseFloat(buf[5]), Float.parseFloat(buf[6]), Float.parseFloat(buf[7]), Float.parseFloat(buf[8])));
                if (candlesArrayList.isEmpty()){
                    System.out.println("Внимание,Список свечей пуст");
                }
            }
        } catch (
                IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<Candle> getCandlesArrayList() {
        return candlesArrayList;
    }
}

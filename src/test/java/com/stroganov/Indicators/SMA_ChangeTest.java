package com.stroganov.Indicators;

import com.stroganov.CandleStream;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SMA_ChangeTest {

    private static final CandleStream testCandleStream = new CandleStream("Data/"+"testcandle14.txt");
    private static final SMA_Change  smaChange = new SMA_Change(testCandleStream,4);

    @Test
    public void calculateIndicator() {
        for (double indicator:smaChange.getArrayListIndicator()
             ) {
            System.out.println(indicator);
        }

        double testDouble=smaChange.getArrayListIndicator().get(4);
        Assert.assertEquals(-1.7325d, testDouble,0.01);

    }
}
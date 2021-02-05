package com.stroganov.Indicators;

import com.stroganov.CandleStream;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SMATest {

    private static final CandleStream testCandleStream = new CandleStream("Data/"+"testcandle14.txt");
    private static final SMA testSMA = new SMA(testCandleStream,4);

    @Test
    public void calculateSMAOneCandle() {

        double testDouble = testSMA.calculateSMAOneCandle(4,3);
        //double test = testSMA.calculateSMAOneCandle();
        Assert.assertEquals(237.6625d, testDouble,0.01);

    }

    @Test
    public void calculateIndicator() {

        double testDouble=testSMA.getArrayListIndicator().get(3);
        Assert.assertEquals(237.6625d, testDouble,0.01);

    }
}






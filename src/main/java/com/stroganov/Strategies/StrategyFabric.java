package com.stroganov.Strategies;

import com.stroganov.CandleStream;
import com.stroganov.Indicators.IndicatorContainer;
import org.apache.log4j.Logger;


public class StrategyFabric {

    private static final Logger logger = Logger.getLogger(StrategyFabric.class);

    public StrategyFabric(){

    }


    public AbstractStrategy createStrategy(CandleStream candleStream, IndicatorContainer container, Strategies strategies, float startMoney){

        AbstractStrategy strategy = null;
       // Balance balance = new Balance(startMoney);


        switch (strategies.name()) {
            case ("RSA_STRATEGY_STOP"):
                strategy = new RSA_StrategyStopLoss(candleStream, 400,  container, startMoney);
                break;


            case ("SMA_STRATEGY"):
                strategy = new SMA_Strategy(candleStream, 400,  container,startMoney);
                break;


            case ("SMA_STRATEGY_REVERSE"):
                strategy = new SMA_StrategyReverse(candleStream,  400, container,startMoney);
                break;


            case ("SMA_STRATEGY_REVERSE_DIFFERENT"):
                strategy = new SMA_StrategyReverseDifferent(candleStream, 400, container,startMoney);
                break;

            default:
                String s = "Тип стратегии указан неверно";
                logger.error(s);
                throw new IllegalArgumentException(s);

        }

       return strategy;

    } //

}

package com.stroganov.Strategies;

import com.stroganov.CandleStream;
import com.stroganov.Indicators.IndicatorContainer;
import org.apache.log4j.Logger;


public class StrategyFabric {

    private static final Logger logger = Logger.getLogger(StrategyFabric.class);

    private IndicatorContainer container;
    CandleStream candleStream;
    float startMoney;

    public StrategyFabric(CandleStream candleStream, IndicatorContainer container, float startMoney) {

        this.candleStream = candleStream;
        this.startMoney = startMoney;
        this.container = container;
    }

    public StrategyFabric(){

    }


    public AbstractStrategy createStrategy(CandleStream candleStream, IndicatorContainer container, Strategies strategies, float startMoney){

        AbstractStrategy strategy = null;
        Balance balance = new Balance(startMoney);
        TradeAction tradeAction = new TradeAction(balance);

        switch (strategies.name()) {
            case ("RSA_STRATEGY_STOP"):
                strategy = new RSA_StrategyStopLoss(candleStream, tradeAction, 400,  container);
                break;


            case ("SMA_STRATEGY"):
                strategy = new SMA_Strategy(candleStream, tradeAction, 400,  container);
                break;


            case ("SMA_STRATEGY_REVERSE"):
                strategy = new SMA_StrategyReverse(candleStream, tradeAction, 400, container);
                break;


            case ("SMA_STRATEGY_REVERSE_DIFFERENT"):
                strategy = new SMA_StrategyReverseDifferent(candleStream, tradeAction, 400, container);
                break;

            default:
                String s = "Тип стратегии указан неверно";
                logger.error(s);
                throw new IllegalArgumentException(s);

        }

       return strategy;

    } //

}

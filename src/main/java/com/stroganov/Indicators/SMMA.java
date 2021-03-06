package com.stroganov.Indicators;

/*
Расчет
Первое значение сглаженного скользящего среднего рассчитывается, как простое скользящее среднее (SMA):
SUM1 = SUM (CLOSE (i), N)
SMMA1 = SUM1 / N
Второе значение рассчитывается по следующей формуле:
SMMA (i) = (SUM1 — SMMA (i — 1) + CLOSE (i)) / N
Последующие скользящие средние рассчитываются по следующей формуле:
PREVSUM = SMMA (i-1) * N
SMMA (i) = (PREVSUM — SMMA (i — 1) + CLOSE (i)) / N
где:
SUM — сумма;
SUM1 — сумма цен закрытия N периодов, отсчитываемая от предыдущего бара;
PREVSUM — сглаженная сумма предыдущего бара;
SMMA (i — 1) — сглаженное скользящее среднее предыдущего бара;
SMMA (i) — сглаженное скользящее среднее текущего бара (кроме первого);
CLOSE (i) — текущая цена закрытия;
N — период сглаживания.
В результате арифметических преобразований формула может быть упрощена:
SMMA (i) = (SMMA (i — 1) * (N — 1) + CLOSE (i)) / N
*/

import com.stroganov.CandleStream; //TODO

public class SMMA extends AbstractIndicator{
    public SMMA(CandleStream candleStream, int period) {
        super(candleStream, period);
    }

    @Override
    public void calculateIndicator() {

    }
}

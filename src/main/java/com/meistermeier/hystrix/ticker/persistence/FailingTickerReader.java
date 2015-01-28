package com.meistermeier.hystrix.ticker.persistence;

import com.meistermeier.hystrix.ticker.model.Ticker;

public class FailingTickerReader implements TickerReader {

    @Override
    public Ticker grabNews() {
        throw new RuntimeException("whole internet is gone. you're doomed.");
    }

}

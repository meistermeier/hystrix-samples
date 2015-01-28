package com.meistermeier.hystrix.ticker.persistence;

import com.meistermeier.hystrix.ticker.model.Ticker;

public class DummyTickerReader implements TickerReader {

    @Override
    public Ticker grabNews() {
        return generateFooNews();
    }

    private Ticker generateFooNews() {
        final Ticker ticker = new Ticker("Foo News");
        ticker.getHeadlines().add("Snow causes white landscape.");
        ticker.getHeadlines().add("Foo news has a new ticker.");
        return ticker;
    }
}

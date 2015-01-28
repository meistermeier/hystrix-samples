package com.meistermeier.hystrix.ticker.persistence;

import com.meistermeier.hystrix.ticker.model.Ticker;

public class LongRunningTickerReader implements TickerReader {

    private static final int FIVE_SECONDS_IN_MILLIS = 5000;

    @Override
    public Ticker grabNews() {
        return generateFooNews();
    }

    private Ticker generateFooNews() {
        final Ticker ticker = new Ticker("Foo News");
        ticker.getHeadlines().add("Snow causes white landscape.");
        ticker.getHeadlines().add("Foo news has a new ticker.");
        try {
            Thread.sleep(FIVE_SECONDS_IN_MILLIS);
        } catch (InterruptedException e) {
            // today you may be ignored
        }
        return ticker;
    }
}

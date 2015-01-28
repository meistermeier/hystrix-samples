package com.meistermeier.hystrix.ticker.service;

import com.meistermeier.hystrix.ticker.model.Ticker;
import com.meistermeier.hystrix.ticker.persistence.TickerReader;

import java.util.ArrayList;
import java.util.List;

public class SimpleTickerService implements TickerService {

    private final TickerReader reader;

    public SimpleTickerService(TickerReader reader) {
        this.reader = reader;
    }

    @Override
    public List<Ticker> getTickers() {
        final List<Ticker> tickers = new ArrayList<>();
        tickers.add(reader.grabNews());
        return tickers;
    }
}

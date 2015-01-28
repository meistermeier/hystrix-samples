package com.meistermeier.hystrix.ticker.service;

import com.meistermeier.hystrix.ticker.model.Ticker;
import com.meistermeier.hystrix.ticker.persistence.TickerReader;

import java.util.ArrayList;
import java.util.List;

public class TickerServiceWithSimpleCommand implements TickerService {

    private final TickerReader reader;

    public TickerServiceWithSimpleCommand(TickerReader reader) {
        this.reader = reader;
    }

    @Override
    public List<Ticker> getTickers() {
        List<Ticker> tickers = new ArrayList<>();
        tickers.add(new TickerReaderCommand(reader).execute());
        return tickers;
    }
}

package com.meistermeier.hystrix.ticker.service;

import com.meistermeier.hystrix.ticker.model.Ticker;
import com.meistermeier.hystrix.ticker.persistence.TickerReader;
import com.netflix.hystrix.HystrixCommand;

import static com.netflix.hystrix.HystrixCommand.Setter.withGroupKey;
import static com.netflix.hystrix.HystrixCommandGroupKey.Factory.asKey;

public class TickerReaderCommand extends HystrixCommand<Ticker> {

    private final TickerReader reader;

    public TickerReaderCommand(TickerReader reader) {
        super(withGroupKey(asKey("TickerReaderCommand")));
        this.reader = reader;
    }

    @Override
    protected Ticker run() throws Exception {
        return reader.grabNews();
    }

}

package com.meistermeier.hystrix.ticker.service;

import com.meistermeier.hystrix.ticker.model.Ticker;
import com.meistermeier.hystrix.ticker.persistence.TickerReader;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties;

import static com.netflix.hystrix.HystrixCommand.Setter.withGroupKey;
import static com.netflix.hystrix.HystrixCommandGroupKey.Factory.asKey;

public class TickerReaderCommandWithCustomizedTimeout extends HystrixCommand<Ticker> {

    private static final int TIMEOUT_IN_MILLIS = 200;
    private final TickerReader reader;

    public TickerReaderCommandWithCustomizedTimeout(TickerReader reader) {
        super(withGroupKey(asKey("TickerReaderCommand")).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter()
                        .withExecutionIsolationThreadTimeoutInMilliseconds(TIMEOUT_IN_MILLIS)));
        this.reader = reader;
    }

    @Override
    protected Ticker run() throws Exception {
        return reader.grabNews();
    }

    @Override
    protected Ticker getFallback() {
        return null;
    }
}

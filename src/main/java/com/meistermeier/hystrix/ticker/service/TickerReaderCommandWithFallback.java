package com.meistermeier.hystrix.ticker.service;

import com.meistermeier.hystrix.ticker.model.Ticker;
import com.meistermeier.hystrix.ticker.persistence.TickerReader;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties;

import static com.netflix.hystrix.HystrixCommand.Setter.withGroupKey;
import static com.netflix.hystrix.HystrixCommandGroupKey.Factory.asKey;

public class TickerReaderCommandWithFallback extends HystrixCommand<Ticker> {

    private final TickerReader reader;

    public TickerReaderCommandWithFallback(TickerReader reader) {
        super(withGroupKey(asKey("TickerReaderCommand")).andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withCircuitBreakerRequestVolumeThreshold(2)
                        .withMetricsRollingStatisticalWindowInMilliseconds(1000))
        );
        this.reader = reader;
    }

    @Override
    protected Ticker run() throws Exception {
        return reader.grabNews();
    }

    @Override
    protected Ticker getFallback() {
        return new Ticker("blubb");
    }

}

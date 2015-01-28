package com.meistermeier.hystrix.ticker.service;

import com.meistermeier.hystrix.ticker.model.Ticker;
import com.meistermeier.hystrix.ticker.persistence.DummyTickerReader;
import com.meistermeier.hystrix.ticker.persistence.FailingTickerReader;
import com.meistermeier.hystrix.ticker.persistence.LongRunningTickerReader;
import com.meistermeier.hystrix.ticker.persistence.TickerReader;
import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TickerServiceIntegrationTest {

    @Test
    public void tickerServiceGetsHottestNews() {
        final TickerReader reader = new DummyTickerReader();
        final List<Ticker> tickers = new SimpleTickerService(reader).getTickers();
        final int expectedSize = 1;

        assertThat(tickers.size(), equalTo(expectedSize));
    }

    @Test(expected = RuntimeException.class)
    public void tickerServiceGetsHottestNewsAndFails() {
        final TickerReader reader = new FailingTickerReader();
        new SimpleTickerService(reader).getTickers();
    }

    @Test(expected = HystrixRuntimeException.class)
    public void tickerServiceGetsHottestNewsAndFailsButHystrixHelpsUsMaybe() {
        new TickerServiceWithSimpleCommand(new FailingTickerReader()).getTickers();
    }

    @Test
    public void tickerServiceGetsHottestNewsAndFailsButHystrixHelpsUsNow() {
        final List<Ticker> tickers = new TickerServiceWithFallbackCommand(new FailingTickerReader()).getTickers();
        final int expectedSize = 1;

        assertThat(tickers.size(), equalTo(expectedSize));
    }

    @Test
    public void tickerServiceGetsHottestNewsButTakesFarTooLong() {
        final List<Ticker> news = new TickerServiceWithTimeoutCommand(new LongRunningTickerReader()).getTickers();
        final int expectedSize = 1;

        assertThat(news.size(), equalTo(expectedSize));
    }

    @Test
    public void circuitBreakerGetsActiveAfterToManyFailures() {

        for (int i = 0; i < 200; i++) {
            new TickerReaderCommandWithFallback(new FailingTickerReader()).execute();
        }
        for (HystrixCommandMetrics hystrixCommandMetrics : HystrixCommandMetrics.getInstances()) {
            System.out
                    .println(HystrixCircuitBreaker.Factory.getInstance(hystrixCommandMetrics.getCommandKey()).isOpen());
        }
    }

    @Test
    public void circuitBreakerGetasActiveAfterToManyFailures() throws Exception {
        for (int i = 0; i < 10; i++) {
            new TickerReaderCommandWithFallback(new FailingTickerReader()).execute();
            final HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey(
                    "TickerReaderCommandWithFallback");
            System.out.println(HystrixCircuitBreaker.Factory.getInstance(commandKey).isOpen());
            Thread.sleep(100);
        }
    }

}

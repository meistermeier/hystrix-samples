package com.meistermeier.hystrix.ticker.persistence;

import com.meistermeier.hystrix.ticker.model.Ticker;

public interface TickerReader {
    Ticker grabNews();
}

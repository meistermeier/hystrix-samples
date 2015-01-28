package com.meistermeier.hystrix.ticker.service;

import com.meistermeier.hystrix.ticker.model.Ticker;

import java.util.List;

public interface TickerService {
    List<Ticker> getTickers();
}

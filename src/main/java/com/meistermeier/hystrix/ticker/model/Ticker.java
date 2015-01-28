package com.meistermeier.hystrix.ticker.model;

import java.util.ArrayList;
import java.util.List;

public class Ticker {

    private final String name;

    private final List<String> headlines = new ArrayList<>();

    public Ticker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getHeadlines() {
        return headlines;
    }

}

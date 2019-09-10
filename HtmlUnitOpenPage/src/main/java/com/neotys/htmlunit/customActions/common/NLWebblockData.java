package com.neotys.htmlunit.customActions.common;

public class NLWebblockData {
    private String unit;
    private double value;
    private long time;
    private String metricName;


    public String getUnit() {
        return unit;
    }

    public double getValue() {
        return value;
    }

    public long getTime() {
        return time;
    }

    public String getMetricName() {
        return metricName;
    }




    public NLWebblockData(String unit, double value, long time, String metricName) {
        super();
        this.unit = unit;
        this.value = value;
        this.time = time;
        this.metricName = metricName;

    }
}

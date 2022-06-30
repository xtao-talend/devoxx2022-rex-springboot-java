package com.xt.springboot.models;

/**
 * Represent information from com.xt.micronaut.kubernetes API response
 */
public class PodInfo {
    public String name;
    public String chart;

    public PodInfo(String aName, String aChart) {
        this.name = aName;
        this.chart = aChart;
    }
}

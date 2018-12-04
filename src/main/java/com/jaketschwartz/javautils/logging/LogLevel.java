package com.jaketschwartz.javautils.logging;

public enum LogLevel {
    TRACE(0),
    DEBUG(1),
    INFO(2),
    WARN(3),
    ERROR(4);

    private int weight;

    LogLevel(int weight) {
        this.weight = weight;
    }

    public int weight() {
        return weight;
    }
}

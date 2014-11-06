package ru.omsk.metro.model;

import org.jetbrains.annotations.NotNull;

/**
 * @author adkozlov
 */
public class TimeInfinity extends Time {

    @NotNull
    private final static TimeInfinity INSTANCE = new TimeInfinity();

    private TimeInfinity() {
        super(Integer.MAX_VALUE);
    }

    @NotNull
    public static TimeInfinity getInstance() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "\u221E";
    }
}

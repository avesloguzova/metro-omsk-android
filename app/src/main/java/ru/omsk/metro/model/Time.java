package ru.omsk.metro.model;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

/**
 * @author adkozlov
 */
public class Time {

    private final int seconds;

    public Time(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    @Override
    public String toString() {
        return "Time{" +
                "seconds=" + seconds +
                '}';
    }

    @NotNull
    public static Time fromJSON(@NotNull JSONObject timeObject, @NotNull String timeProperty) {
        Long time = (Long) timeObject.get(timeProperty);

        return time != null ? new Time(time.intValue()) : TimeInfinity.getInstance();
    }
}

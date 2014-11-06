package ru.omsk.metro.model;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

/**
 * @author adkozlov
 */
public class Station extends NamedSubwayObject {

    @NotNull
    private final StationCoordinate coordinate;
    @NotNull
    private final Time timeToPrevious;
    @NotNull
    private final Time timeToNext;

    public Station(int id, String name, @NotNull StationCoordinate coordinate, @NotNull Time timeToPrevious, @NotNull Time timeToNext) {
        super(id, name);
        this.coordinate = coordinate;
        this.timeToPrevious = timeToPrevious;
        this.timeToNext = timeToNext;
    }

    public Station(int id, String name, @NotNull StationCoordinate coordinate, int timeToPreviousInSeconds, int timeToNextInSeconds) {
       this(id, name, coordinate, new Time(timeToPreviousInSeconds), new Time(timeToNextInSeconds));
    }

    @NotNull
    public StationCoordinate getCoordinate() {
        return coordinate;
    }

    @NotNull
    public Time getTimeToPrevious() {
        return timeToPrevious;
    }

    public int getTimeToPreviousInSecondes() {
        return getTimeToPrevious().getSeconds();
    }

    @NotNull
    public Time getTimeToNext() {
        return timeToNext;
    }

    public int getTimeToNextInSeconds() {
        return getTimeToNext().getSeconds();
    }

    @Override
    public String toString() {
        return "Station{" +
                "coordinate=" + coordinate +
                ", timeToPrevious=" + timeToPrevious +
                ", timeToNext=" + timeToNext +
                '}';
    }

    @NotNull
    public static Station fromJSON(@NotNull JSONObject stationObject) {
        return new Station(getIdFromJSON(stationObject), getNameFromJSON(stationObject),
                StationCoordinate.fromJSON(stationObject),
                Time.fromJSON(stationObject, "prev_time"), Time.fromJSON(stationObject, "next_time"));
    }
}

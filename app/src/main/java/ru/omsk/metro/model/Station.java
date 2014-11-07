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
    private final boolean hasNext;
    private final boolean hasPrevious;

    public Station(int id, String name, @NotNull StationCoordinate coordinate, @NotNull Time timeToPrevious, @NotNull Time timeToNext, boolean hasNext, boolean hasPrevious) {
        super(id, name);
        this.coordinate = coordinate;
        this.timeToPrevious = timeToPrevious;
        this.timeToNext = timeToNext;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
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

    public boolean isHasNext() {
        return hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
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
                Time.fromJSON(stationObject, "prev_time"), Time.fromJSON(stationObject, "next_time"),
                stationObject.get("next_station_id") != null,
                stationObject.get("prev_station_id") != null);
    }
}

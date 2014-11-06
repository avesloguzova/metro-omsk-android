package ru.omsk.metro.model;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

/**
 * @author adkozlov
 */
public class WayStation extends AbstractSubwayObject {

    private final int idFrom;
    private final int idTo;

    @NotNull
    private final Time wayTime;

    public WayStation(int id, int idFrom, int idTo, @NotNull Time wayTime) {
        super(id);
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.wayTime = wayTime;
    }

    public WayStation(int id, int idFrom, int idTo, int wayTimeInSeconds) {
        this(id, idFrom, idTo, new Time(wayTimeInSeconds));
    }

    public int getIdFrom() {
        return idFrom;
    }

    public int getIdTo() {
        return idTo;
    }

    @NotNull
    public Time getWayTime() {
        return wayTime;
    }

    public int getWayTimeInSeconds() {
        return wayTime.getSeconds();
    }

    @NotNull
    public static WayStation fromJSON(@NotNull JSONObject wayStationObject) {
        return new WayStation(getIdFromJSON(wayStationObject),
                getIdFromJSON(wayStationObject, "idFrom"), getIdFromJSON(wayStationObject, "idTo"),
                Time.fromJSON(wayStationObject, "wayTime"));
    }

    @NotNull
    public static int getIdFromJSON(@NotNull JSONObject wayStationObject, @NotNull String idProperty) {
        return ((Long) wayStationObject.get(idProperty)).intValue();
    }
}

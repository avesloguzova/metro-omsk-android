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

    public int getIdFrom() {
        return idFrom;
    }

    public int getIdTo() {
        return idTo;
    }

    public int getWayTimeInSeconds() {
        return wayTime.getSeconds();
    }

    @NotNull
    public static WayStation fromJSON(@NotNull JSONObject wayStationObject) {
        return new WayStation(0, getIdFromJSON(wayStationObject, "from_id"), getIdFromJSON(wayStationObject, "to_id"),
                Time.fromJSON(wayStationObject, "time"));
    }

    @NotNull
    public static int getIdFromJSON(@NotNull JSONObject wayStationObject, @NotNull String idProperty) {
        return ((Long) wayStationObject.get(idProperty)).intValue();
    }
}

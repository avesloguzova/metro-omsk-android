package ru.omsk.metro.objects;

import org.jetbrains.annotations.NotNull;

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
}

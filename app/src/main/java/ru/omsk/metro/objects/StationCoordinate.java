package ru.omsk.metro.objects;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

/**
 * @author adkozlov
 */
public class StationCoordinate {

    private final double x;
    private final double y;

    public StationCoordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "StationCoordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @NotNull
    public static StationCoordinate fromJSON(@NotNull JSONObject coordinateObject) {
        return new StationCoordinate((Double) coordinateObject.get("x"), (Double) coordinateObject.get("y"));
    }
}

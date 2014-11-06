package ru.omsk.metro.model;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author adkozlov
 */
public class SubwayMap {

    private final static Comparator<StationCoordinate> X_COMPARATOR = new Comparator<StationCoordinate>() {
        @Override
        public int compare(StationCoordinate c1, StationCoordinate c2) {
            return Double.compare(c1.getX(), c2.getX());
        }
    };

    private final static Comparator<StationCoordinate> Y_COMPARATOR = new Comparator<StationCoordinate>() {
        @Override
        public int compare(StationCoordinate c1, StationCoordinate c2) {
            return Double.compare(c1.getY(), c2.getY());
        }
    };

    @NotNull
    private final List<Line> lines;
    @NotNull
    private final List<WayStation> wayStations;

    @NotNull
    private final StationCoordinate[] boundingRectangle;

    public SubwayMap(@NotNull List<Line> lines, @NotNull List<WayStation> wayStations) {
        this.lines = lines;
        this.wayStations = wayStations;

        boundingRectangle = createBoundingRectangle(lines);
    }

    @NotNull
    public List<Line> getLines() {
        return Collections.unmodifiableList(lines);
    }

    @NotNull
    public List<WayStation> getWayStations() {
        return Collections.unmodifiableList(wayStations);
    }

    @NotNull
    public StationCoordinate[] getBoundingRectangle() {
        return boundingRectangle;
    }

    @Override
    public String toString() {
        return "SubwayMap{" +
                "lines=" + lines +
                ", wayStations=" + wayStations +
                '}';
    }

    @NotNull
    private static StationCoordinate[] createBoundingRectangle(@NotNull List<Line> lines) {
        List<StationCoordinate> coordinates = new ArrayList<StationCoordinate>();
        for (Line line : lines) {
            for (Station station : line.getStations()) {
                coordinates.add(station.getCoordinate());
            }
        }

        return new StationCoordinate[]{
                new StationCoordinate(Collections.min(coordinates, X_COMPARATOR).getX(), Collections.min(coordinates, Y_COMPARATOR).getY()),
                new StationCoordinate(Collections.max(coordinates, X_COMPARATOR).getX(), Collections.max(coordinates, Y_COMPARATOR).getY())
        };
    }

    @NotNull
    public static SubwayMap fromJSON(@NotNull JSONObject mapObject) {
        return new SubwayMap(getLinesFromJSON(mapObject), null /*getWayStationsFromJSON(mapObject)*/);
    }

    @NotNull
    private static List<Line> getLinesFromJSON(@NotNull JSONObject mapObject) {
        JSONArray lines = (JSONArray) mapObject.get("lines");

        List<Line> result = new ArrayList<Line>();
        for (Object lineObject : lines) {
            result.add(Line.fromJSON((JSONObject) lineObject));
        }
        return result;
    }

    @NotNull
    private static List<WayStation> getWayStationsFromJSON(@NotNull JSONObject mapObject) {
        JSONArray wayStations = (JSONArray) mapObject.get("wayStations");

        List<WayStation> result = new ArrayList<WayStation>();
        for (Object wayStationObject : wayStations) {
            result.add(WayStation.fromJSON((JSONObject) wayStationObject));
        }
        return result;
    }
}

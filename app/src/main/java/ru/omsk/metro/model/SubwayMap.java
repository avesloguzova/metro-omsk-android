package ru.omsk.metro.model;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author adkozlov
 */
public class SubwayMap extends NamedSubwayObject {

    @NotNull
    private final List<Line> lines;
    @NotNull
    private final List<WayStation> wayStations;

    public SubwayMap(@NotNull int id, @NotNull String name, @NotNull List<Line> lines, @NotNull List<WayStation> wayStations) {
        super(id, name);

        this.lines = lines;
        this.wayStations = wayStations;
    }

    @NotNull
    public List<Line> getLines() {
        return Collections.unmodifiableList(lines);
    }

    @NotNull
    public List<WayStation> getWayStations() {
        return Collections.unmodifiableList(wayStations);
    }

    @Override
    public String toString() {
        return "SubwayMap{" +
                "lines=" + lines +
                ", wayStations=" + wayStations +
                '}';
    }

    @NotNull
    public static SubwayMap fromJSON(@NotNull JSONObject mapObject) {
        ;
        return new SubwayMap(((Long) mapObject.get("city_id")).intValue(), getNameFromJSON(mapObject),
                getLinesFromJSON(mapObject), getWayStationsFromJSON(mapObject));
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
        JSONArray wayStations = (JSONArray) mapObject.get("transitions");

        List<WayStation> result = new ArrayList<WayStation>();
        for (Object wayStationObject : wayStations) {
            result.add(WayStation.fromJSON((JSONObject) wayStationObject));
        }
        return result;
    }
}

package ru.omsk.metro.objects;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author adkozlov
 */
public class Line extends NamedSubwayObject {

    private final int color;
    @NotNull
    private final List<Station> stations;

    public Line(int id, String name, int color, @NotNull List<Station> stations) {
        super(id, name);
        this.color = color;
        this.stations = stations;
    }

    public int getColor() {
        return color;
    }

    @NotNull
    public List<Station> getStations() {
        return Collections.unmodifiableList(stations);
    }

    @Override
    public String toString() {
        return "Line{" +
                "color=" + color +
                ", stations=" + stations +
                '}';
    }

    @NotNull
    public static Line fromJSON(@NotNull JSONObject jsonLine) {
        Long color = (Long) jsonLine.get("color");

        JSONArray stationsArray = (JSONArray) jsonLine.get("stations");

        List<Station> stations = new ArrayList<Station>();
        for (Object stationObject : stationsArray) {
            stations.add(Station.fromJSON((JSONObject) stationObject));
        }

        return new Line(getIdFromJSON(jsonLine), getNameFromJSON(jsonLine), color.intValue(), stations);
    }
}

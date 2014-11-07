package ru.omsk.metro.model;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

/**
 * Created by avesloguzova on 06.11.14.
 */
public class City extends NamedSubwayObject {

    public City(int id, String name) {
        super(id, name);
    }

    @NotNull
    public static City fromJSON(@NotNull JSONObject jsonObject) {
        return new City(getIdFromJSON(jsonObject), getNameFromJSON(jsonObject));
    }
}

package ru.omsk.metro.model;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

/**
 * @author adkozlov
 */
public abstract class AbstractSubwayObject {

    private final int id;

    public AbstractSubwayObject(int id) {
        this.id = id;
    }

    public final int getId() {
        return id;
    }

    public static int getIdFromJSON(@NotNull JSONObject object) {
        return ((Long) object.get("id")).intValue();
    }
}

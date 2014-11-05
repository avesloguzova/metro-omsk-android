package ru.omsk.metro.objects;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

/**
 * @author adkozlov
 */
public class NamedSubwayObject extends AbstractSubwayObject {

    @NotNull
    private final String name;

    public NamedSubwayObject(int id, String name) {
        super(id);
        this.name = name;
    }

    @NotNull
    public final String getName() {
        return name;
    }

    @NotNull
    public static String getNameFromJSON(@NotNull JSONObject object) {
        return (String) object.get("name");
    }
}

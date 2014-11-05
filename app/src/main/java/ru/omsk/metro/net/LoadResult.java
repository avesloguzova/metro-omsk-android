package ru.omsk.metro.net;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;

/**
 * Created by avesloguzova on 05.11.14.
 */
public class LoadResult {

    @Nullable
    private final JSONObject json;
    @Nullable
    private final String exceptMessage;

    private LoadResult(@Nullable JSONObject json, @Nullable String exceptMessage) {
        this.json = json;
        this.exceptMessage = exceptMessage;
    }

    public LoadResult(@NotNull JSONObject json) {
        this(json, null);
    }

    public LoadResult(@NotNull String exceptMessage) {
        this(null, exceptMessage);
    }

    @Nullable
    public JSONObject getJSON() {
        return json;
    }

    @Nullable
    public String getExceptMessage() {
        return exceptMessage;
    }

    public boolean isSucceeded() {
        return json != null;
    }

    @Override
    public String toString() {
        return "LoadResult{" +
                "json=" + json +
                ", exceptMessage='" + exceptMessage + '\'' +
                '}';
    }
}

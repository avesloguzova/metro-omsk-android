package ru.omsk.metro.net;

import org.json.simple.JSONObject;

/**
 * Created by avesloguzova on 05.11.14.
 */
public class LoadResult {
    private boolean success;
    private JSONObject object;

    public String getExceptMessage() {
        return exceptMessage;
    }

    private String exceptMessage;

    public boolean isSuccess() {
        return success;
    }

    public JSONObject getObject() {
        return object;
    }

    public LoadResult(boolean success, String exceptMessage) {
        this.success=false;

    }

    public LoadResult(JSONObject object) {
        this.object = object;
        this.success = true;

    }
}

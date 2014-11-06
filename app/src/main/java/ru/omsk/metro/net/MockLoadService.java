package ru.omsk.metro.net;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

/**
 * Created by adkozlov on 06.11.14.
 */
public class MockLoadService extends LoadService {

    private final static String MOCK_JSON = "{\"city_id\": 1, \"transitions\": [{\"to_id\": 52, \"time\": 2, \"from_id\": 51}], \"lines\": [{\"color\": \"#FF0000\", \"stations\": [{\"next_time\": null, \"lat\": 54.99120655714044, \"name\": \"1\", \"y\": 1.0, \"x\": 1.0, \"lng\": 73.3663558959961, \"prev_time\": null, \"id\": 50, \"next_station_id\": 51}, {\"next_time\": null, \"lat\": 54.98116008482771, \"name\": \"2\", \"y\": 0.1428750207558102, \"x\": 0.36259541984732824, \"lng\": 73.33768844604492, \"prev_time\": null, \"id\": 51, \"next_station_id\": null}], \"id\": 1, \"name\": \"\\u041a\\u0440\\u0430\\u0441\\u043d\\u0430\\u044f\"}, {\"color\": \"#00FF00\", \"stations\": [{\"next_time\": null, \"lat\": 54.97948542830157, \"name\": \"3\", \"y\": 0.0, \"x\": 0.36259541984732824, \"lng\": 73.33768844604492, \"prev_time\": null, \"id\": 52, \"next_station_id\": 53}, {\"next_time\": null, \"lat\": 54.983524186888225, \"name\": \"4\", \"y\": 0.3445707868393187, \"x\": 0.0, \"lng\": 73.32138061523438, \"prev_time\": null, \"id\": 53, \"next_station_id\": null}], \"id\": 2, \"name\": \"\\u0417\\u0435\\u043b\\u0435\\u043d\\u0430\\u044f\"}], \"name\": \"\\u041e\\u043c\\u0441\\u043a\"}";

    public MockLoadService(@NotNull Context context) {
        super(context);
    }

    @NotNull
    @Override
    public LoadResult load() throws LoadServiceException {
        return new LoadResult(getResultFromJSON(MOCK_JSON));
    }
}

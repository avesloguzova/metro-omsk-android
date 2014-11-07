package ru.omsk.metro.net;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

/**
 * Created by adkozlov on 06.11.14.
 */
public class MockLoadService extends LoadService {
    private final static String MOCK_JSON_CITY = "{\n" +
            "    \"cities\": [\n" +
            "        {\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"Омск\",\n" +
            "            \"lat\": 55.12707871858353,\n" +
            "            \"lng\": 73.75946044921875\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 2,\n" +
            "            \"name\": \"Санкт-Петербург\",\n" +
            "            \"lat\": 59.924561,\n" +
            "            \"lng\": 30.353781\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private final static String MOCK_JSON = "{" +
            "\"city_id\": 1," +
            "\"transitions\": [" +
            "{" +
            "\"to_id\": 52, \"time\": 2, \"from_id\": 51" +
            "}]," +
            "\"lines\": [" +
            "{" +
            "\"color\": \"#FF0000\"," +
            "\"stations\": [" +
            "{" +
            "\"next_time\": 60, \"lat\": 54.99120655714044, \"name\": \"Первая\", \"y\": 1.0, \"x\": 1.0, \"lng\": 73.3663558959961, \"prev_time\": null, \"id\": 50, \"next_station_id\": 51" +
            "}, {" +
            "\"next_time\": null, \"lat\": 54.98116008482771, \"name\": \"Вторая\", \"y\": 0.1428750207558102, \"x\": 0.36259541984732824, \"lng\": 73.33768844604492, \"prev_time\": 50, \"id\": 51, \"next_station_id\": null" +
            "}]," +
            "\"id\": 1," +
            "\"name\": \"Красная\"" +
            "}, {" +
            "\"color\": \"#00FF00\"," +
            "\"stations\": [" +
            "{" +
            "\"next_time\": 90, \"lat\": 54.97948542830157, \"name\": \"Третья\", \"y\": 0.0, \"x\": 0.36259541984732824, \"lng\": 73.33768844604492, \"prev_time\": null, \"id\": 52, \"next_station_id\": 53" +
            "}, {" +
            "\"next_time\": null, \"lat\": 54.983524186888225, \"name\": \"Четвертая\", \"y\": 0.3445707868393187, \"x\": 0.0, \"lng\": 73.32138061523438, \"prev_time\": 80, \"id\": 53, \"next_station_id\": null" +
            "}]," +
            "\"id\": 2," +
            "\"name\": \"Зеленая\"" +
            "}]," +
            "\"name\": \"Омск\"" +
            "}";

    public MockLoadService(@NotNull Context context) {
        super(context);
    }

    @NotNull
    @Override
    public LoadResult loadCities() throws LoadServiceException {
        return new LoadResult(getResultFromJSON(MOCK_JSON_CITY));
    }

    @NotNull
    @Override
    public LoadResult load(int city_id) throws LoadServiceException {
        return new LoadResult(getResultFromJSON(MOCK_JSON));
    }
}
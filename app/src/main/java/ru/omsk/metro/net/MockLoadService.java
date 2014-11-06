package ru.omsk.metro.net;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

/**
 * Created by adkozlov on 06.11.14.
 */
public class MockLoadService extends LoadService {

    private final static String MOCK_JSON = "{\n" +
            "    \"city_id\": 1,\n" +
            "    \"transitions\": [],\n" +
            "    \"lines\": [\n" +
            "        {\n" +
            "            \"color\": \"#FF0000\",\n" +
            "            \"stations\": [\n" +
            "                {\n" +
            "                    \"next_time\": null, \"coords\": \"0.1,0\", \"name\": \"12\", \"lat\": 55.12707871858353, \"lng\": 73.75946044921875, \"prev_time\": null, \"id\": 21, \"next_station_id\": 12\n" +
            "                }, {\n" +
            "                    \"next_time\": null, \"coords\": \"0.2,0\", \"name\": \"1\", \"lat\": 55.00125051509462, \"lng\": 73.40377807617188, \"prev_time\": null, \"id\": 12, \"next_station_id\": 13\n" +
            "                }, {\n" +
            "                    \"next_time\": null, \"coords\": \"0.3,0\", \"name\": \"2\", \"lat\": 54.89714423283752, \"lng\": 73.3502197265625, \"prev_time\": null, \"id\": 13, \"next_station_id\": 14\n" +
            "                }, {\n" +
            "                    \"next_time\": null, \"coords\": \"0.4,0\", \"name\": \"3\", \"lat\": 54.82679909119997, \"lng\": 73.38729858398438, \"prev_time\": null, \"id\": 14, \"next_station_id\": 23\n" +
            "                }, {\n" +
            "                    \"next_time\": null, \"coords\": \"0.5,0\", \"name\": \"13\", \"lat\": 54.97840178979694, \"lng\": 73.44085693359375, \"prev_time\": null, \"id\": 23, \"next_station_id\": 24\n" +
            "                }, {\n" +
            "                    \"next_time\": null, \"coords\": \"0.6,0\", \"name\": \"132\", \"lat\": 55.10273039662892, \"lng\": 73.311767578125, \"prev_time\": null, \"id\": 24, \"next_station_id\": 26\n" +
            "                }, {\n" +
            "                    \"next_time\": null, \"coords\": \"0.7,0\", \"name\": \"324345\", \"lat\": 54.92319608498875, \"lng\": 73.31451416015625, \"prev_time\": null, \"id\": 26, \"next_station_id\": null\n" +
            "                }\n" +
            "            ],\n" +
            "            \"id\": 1,\n" +
            "            \"name\": \"\\u041a\\u0440\\u0430\\u0441\\u043d\\u0430\\u044f\"\n" +
            "        }, {\n" +
            "            \"color\": \"#00FF00\",\n" +
            "            \"stations\": [\n" +
            "                {\n" +
            "                    \"next_time\": null, \"coords\": \"0,0.1\", \"name\": \"13\", \"lat\": 54.99337311367353, \"lng\": 73.905029296875, \"prev_time\": null, \"id\": 22, \"next_station_id\": 20\n" +
            "                }, {\n" +
            "                    \"next_time\": null, \"coords\": \"0,0.2\", \"name\": \"11\", \"lat\": 54.99809974011551, \"lng\": 73.07830810546875, \"prev_time\": null, \"id\": 20, \"next_station_id\": 25\n" +
            "                }, {\n" +
            "                    \"next_time\": null, \"coords\": \"0,0.3\", \"name\": \"13224\", \"lat\": 73.07830810546875, \"lng\": 54.99809974011551, \"prev_time\": null, \"id\": 25, \"next_station_id\": null\n" +
            "                }\n" +
            "            ],\n" +
            "            \"id\": 2,\n" +
            "            \"name\": \"\\u0417\\u0435\\u043b\\u0435\\u043d\\u0430\\u044f\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"name\": \"\\u041e\\u043c\\u0441\\u043a\"\n" +
            "}";

    public MockLoadService(@NotNull Context context) {
        super(context);
    }

    @NotNull
    @Override
    public LoadResult load() throws LoadServiceException {
        return new LoadResult(getResultFromJSON(MOCK_JSON));
    }
}

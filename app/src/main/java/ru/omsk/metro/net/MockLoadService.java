package ru.omsk.metro.net;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

/**
 * Created by adkozlov on 06.11.14.
 */
public class MockLoadService extends LoadService {

    private final static String MOCK_JSON = "{\"lines\": [\n" +
            "        {\n" +
            "            \"id\": 1, \"name\": \"Кировско-Выборгская\", \"color\": 16711680, \"stations\": [\n" +
            "            {\n" +
            "                \"id\": 1, \"name\": \"Чернышевская\", \"x\": 0.0, \"y\": 1.0, \"toNext\": 60\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 2, \"name\": \"Площадь Восстания\", \"x\": 0.0, \"y\": 0.0, \"toNext\": 40, \"toPrev\": 60\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 3, \"name\": \"Владимирская\", \"x\": 0.0, \"y\": -1.0, \"toPrev\": 60\n" +
            "            }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 3, \"name\": \"Невско-Василеостровская\", \"color\": 65280, \"stations\": [\n" +
            "            {\n" +
            "                \"id\": 4, \"name\": \"Маяковская\", \"x\": 0.0, \"y\": 0.0, \"toNext\": 120\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 5, \"name\": \"Невский Проспект\", \"x\": -1.0, \"y\": 0.0, \"toNext\": 180, \"toPrev\": 120\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 6, \"name\": \"Василеостровская\", \"x\": -2.0, \"y\": 0.0, \"toPrev\": 180\n" +
            "            }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]}";

    public MockLoadService(@NotNull Context context) {
        super(context);
    }

    @NotNull
    @Override
    public LoadResult load() throws LoadServiceException {
        return new LoadResult(getResultFromJSON(MOCK_JSON));
    }
}

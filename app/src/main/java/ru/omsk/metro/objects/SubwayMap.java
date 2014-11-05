package ru.omsk.metro.objects;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author adkozlov
 */
public class SubwayMap {

    @NotNull
    public static JSONObject parseFile(String path) throws ParseException {
        BufferedReader reader = null;
        StringBuilder builder = null;

        try {
            reader = new BufferedReader(new FileReader(path));

            builder = new StringBuilder();
            while (reader.ready()) {
                builder.append(reader.readLine() + "\n");
            }

        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(builder.toString());
    }

    @NotNull
    public static List<Line> fromJSON(JSONObject json) {
        JSONArray lines = (JSONArray) json.get("lines");

        List<Line> result = new ArrayList<Line>();
        for (Object lineObject : lines) {
            result.add(Line.fromJSON((JSONObject) lineObject));
        }

        return result;
    }
}

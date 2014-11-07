package ru.omsk.metro.net;

import android.content.Context;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import ru.omsk.metro.R;

/**
 * Created by avesloguzova on 05.11.14.
 */
public class LoadService {

    @NotNull
    private final Context context;
    @NotNull
    private final HttpClient client = new DefaultHttpClient();

    public LoadService(@NotNull Context context) {
        this.context = context;
    }

    @NotNull
    protected static JSONObject getResultFromJSON(@NotNull String json) throws LoadServiceException {
        try {
            JSONParser parser = new JSONParser();

            return (JSONObject) parser.parse(json);
        } catch (ParseException e) {
            throw new LoadServiceException(e);
        }
    }

    @NotNull
    protected static String readJSONFromStream(@NotNull InputStream inputStream) throws LoadServiceException {
        BufferedReader reader = null;
        try {
            // json is UTF-8 by default
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (IOException e) {
            throw new LoadServiceException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new LoadServiceException(e);
                }
            }
        }
    }

    @NotNull
    private static String readJSONFromResponse(@NotNull HttpResponse response) throws LoadServiceException {
        InputStream inputStream = null;

        try {
            inputStream = response.getEntity().getContent();

            return readJSONFromStream(inputStream);
        } catch (IOException e) {
            throw new LoadServiceException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new LoadServiceException(e);
                }
            }
        }
    }

    @NotNull
    public URI getURI(String path) throws LoadServiceException {
        try {
            return new URL(
                    context.getString(R.string.API_PROTOCOL),
                    context.getString(R.string.API_HOST),
                    Integer.valueOf(context.getString(R.string.API_PORT)), path
            ).toURI();
        } catch (MalformedURLException e) {
            throw new LoadServiceException(e);
        } catch (URISyntaxException e) {
            throw new LoadServiceException(e);
        }
    }

    @NotNull
    public LoadResult load(int city_id) throws LoadServiceException {
        HttpGet get = new HttpGet(getURI(String.format(context.getString(R.string.API_PATH_MODEL), city_id)));
        HttpResponse response = null;

        try {
            response = client.execute(get);
        } catch (IOException e) {
            throw new LoadServiceException(e);
        }

        return new LoadResult(getResultFromJSON(readJSONFromResponse(response)));
    }

    /**
     * Load JSON contain information about available cities
     *
     * @return LoadResult
     * @throws LoadServiceException
     */
    @NotNull
    public LoadResult loadCities() throws LoadServiceException {
        HttpGet get = new HttpGet(getURI(context.getString(R.string.API_PATH_CITY)));
        HttpResponse response = null;

        try {
            response = client.execute(get);
        } catch (IOException e) {
            throw new LoadServiceException(e);
        }

        return new LoadResult(getResultFromJSON(readJSONFromResponse(response)));
    }
}

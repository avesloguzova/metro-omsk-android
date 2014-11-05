package ru.omsk.metro;

import android.content.Context;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Created by avesloguzova on 05.11.14.
 */
public class LoadService {
    private Context context;

    public LoadService(Context context) {
        this.context = context;
    }

    private HttpClient client = new DefaultHttpClient();

    public URI getURI() throws LoadServiceException {
        try {

            return new URL(
                    context.getString(R.string.API_PROTOCOL),
                    context.getString(R.string.API_HOST),
                    Integer.valueOf(context.getString(R.string.API_PORT)),
                    context.getString(R.string.API_PATH)).toURI();
        } catch (MalformedURLException e) {
            throw new LoadServiceException(e);
        } catch (URISyntaxException e) {
            throw new LoadServiceException(e);
        }


    }

    public LoadResult load() throws LoadServiceException {
        HttpGet get = new HttpGet(getURI());
        HttpResponse response = null;
        try {
            response = client.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new LoadResult( getResultFromJSON(readJSONFromResponse(response)));
    }

    private JSONObject getResultFromJSON(String s) {

        Object obj= JSONValue.parse(s);
        return (JSONObject)obj;
    }

    private String readJSONFromResponse(HttpResponse response) throws LoadServiceException {
        try {
            HttpEntity entity = response.getEntity();

            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (IOException e) {
            throw new LoadServiceException(e);
        }
    }
}

package ru.omsk.metro.net;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by adkozlov on 06.11.14.
 */
public class MockLoadService extends LoadService {

    private final static String PATH = "testData/spbJsonTest.json";

    public MockLoadService(@NotNull Context context) {
        super(context);
    }

    @NotNull
    @Override
    public LoadResult load() throws LoadServiceException {
        return new LoadResult(getResultFromJSON(readJSONFromFile(PATH)));
    }

    @NotNull
    private static String readJSONFromFile(@NotNull String path) throws LoadServiceException {
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(path);

            return readJSONFromStream(inputStream);
        } catch (FileNotFoundException e) {
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
}

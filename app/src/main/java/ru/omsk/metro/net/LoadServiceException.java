package ru.omsk.metro.net;

import java.net.MalformedURLException;

/**
 * Created by avesloguzova on 05.11.14.
 */
public class LoadServiceException extends Exception {
    public LoadServiceException(Throwable throwable) {
        super(throwable);
    }

    public LoadServiceException() {
    }

    public LoadServiceException(String detailMessage) {
        super(detailMessage);
    }

    public LoadServiceException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}

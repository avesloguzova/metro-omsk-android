package ru.omsk.metro.net;

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

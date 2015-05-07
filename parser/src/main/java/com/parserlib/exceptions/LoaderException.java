package com.parserlib.exceptions;

/**
 * Created by retor on 07.05.2015.
 */
public class LoaderException extends Exception {
    public LoaderException(Throwable cause) {
        super(cause);
    }

    public LoaderException(String message) {
        super(message);
    }

    public LoaderException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.parser.exceptions;

/**
 * Created by Admin on 07.05.15.
 */
public class ParserException extends Exception {
    public ParserException(Throwable cause) {
        super(cause);
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }
}

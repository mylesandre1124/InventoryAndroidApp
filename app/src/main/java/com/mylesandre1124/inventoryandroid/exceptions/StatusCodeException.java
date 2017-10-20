package com.mylesandre1124.inventoryandroid.exceptions;

/**
 * Created by Myles on 10/19/17.
 */
public class StatusCodeException extends Exception {

    public StatusCodeException(int code) {
        super("Status code: " + code + " ");
    }
}

package com.kinory.meltzer.spaminspector.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Gilad Kinory on 07/10/2017.
 * e-mail: giladkinory2000@gmail.com
 *
 *
 * A class that manages the communication with the server.
 */

public class ServerConnector {

    private Context context;
    private RequestQueue queue;
    private static final String SERVER_ADDRESS = "http://52.58.93.236/";

    public ServerConnector(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    public Context getContext() {
        return context;
    }

    /**
     * Performs a simple http request (with a string response).
     * @param url The URL of the request.
     */
    private void httpRequest(String url) {
        httpRequest(url, System.out::println, Throwable::printStackTrace);
    }

    /**
     * Performs a simple http request (with a string response).
     * @param url The URL of the request.
     * @param listener The listener to get the results.
     */
    private void httpRequest(String url, ResultListener<String> listener) {
        httpRequest(url, listener, Throwable::printStackTrace);
    }

    /**
     * Performs a simple http request (with a string response).
     * @param url The URL of the request.
     * @param listener The listener to get the results.
     * @param errorListener A listener to be called if an error has occurred
     */
    private void httpRequest(String url, ResultListener<String> listener,
                            ResultListener<VolleyError> errorListener) {
        StringRequest request = new StringRequest(url, listener::onResult, errorListener::onResult);
        queue.add(request);
    }

    /**
     * Checks if a given message is a valid message or not.
     * @param message The message to check.
     * @param listener A listener to be called with the results:
     *                 true = valid message
     *                 false = spam message
     */
    public void checkIsValidMessage(String message, ResultListener<Boolean> listener) {
        // Constructs the url for the request
        String url = SERVER_ADDRESS + Utils.parseToURL(message);

        // Sends the request
        httpRequest(url, result -> listener.onResult(Utils.parseBooleanString(result)));
    }

    /**
     * Reports a given message as either valid or spam.
     * @param message The message to report.
     * @param isValid true = report as valid
     *                false = report as spam
     */
    public void reportMessage(String message, boolean isValid) {
        // Constructs the url for the request
        String url = SERVER_ADDRESS + (isValid ? "ham" : "spam") + "/report/" + Utils.parseToURL(message);

        // Sends the request
        httpRequest(url);
    }
}

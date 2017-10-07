package com.kinory.meltzer.spaminspector.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
     * @param listener The listener to get the results.
     */
    public void httpRequest(String url, ResultListener<String> listener) {
        httpRequest(url, listener, Throwable::printStackTrace);
    }

    /**
     * Performs a simple http request (with a string response).
     * @param url The URL of the request.
     * @param listener The listener to get the results.
     * @param errorListener A listener to be called if an error has occurred
     */
    public void httpRequest(String url, ResultListener<String> listener,
                            ResultListener<VolleyError> errorListener) {
        StringRequest request = new StringRequest(url, listener::onResult, errorListener::onResult);
        queue.add(request);
    }
}

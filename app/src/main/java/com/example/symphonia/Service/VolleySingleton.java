package com.example.symphonia.Service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Class that handles HTTP requests for REST APIs
 *
 * @author Hossam Alaa
 * @version 1.0
 * @since 11-4-2020
 */

public class VolleySingleton {

    /**
     * instance of volley singleton class
     */
    private static VolleySingleton instance;
    /**
     * context of activity that calls request
     */
    private static Context ctx;
    /**
     * queue that holds all requests
     */
    private RequestQueue requestQueue;

    /**
     * constructor of volley singleton
     *
     * @param context holds context of activity that calls request
     */
    private VolleySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    /**
     * initializes an instance of volley singleton,
     * synchronized to prevent making many treads for same request
     *
     * @param context context of activity to call request
     * @return returns an instance
     */
    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    /**
     * getter for queue of requests
     *
     * @return returns requests queue
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * adds new request to queue
     *
     * @param req holds request to be added
     * @param <T> template type for method
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}

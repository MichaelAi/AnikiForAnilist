package org.anikiteam.anikiforanilist.base;

import org.anikiteam.anikiforanilist.core.NetworkCall;
import org.anikiteam.anikiforanilist.core.interfaces.NetworkRequestListener;
import org.anikiteam.anikiforanilist.core.RetrofitApiBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

public class BaseNetworkController<T> {

    private final String BASE_URL = "https://graphql.anilist.co";

    private List<NetworkCall> enqueuedCalls;

    private List<NetworkRequestListener> onRequestStart; //executed on call
    private List<NetworkRequestListener> onRequestEnd; //executed on response

    public void addNetworkStartListenter(NetworkRequestListener listener){
        if(onRequestStart == null) onRequestStart = new ArrayList<>();
        onRequestStart.add(listener);
    }
    public void addNetworkendListenter(NetworkRequestListener listener){
        if(onRequestEnd == null) onRequestEnd = new ArrayList<>();
        onRequestEnd.add(listener);
    }
    public void clearNetworkStartListeners(){
        if(onRequestStart != null) onRequestStart.clear();
    }
    public void clearNetworkEndListeners(){
        if(onRequestEnd != null) onRequestEnd.clear();
    }

    /**
     * Adds the call to the list of enqueued calls in order to be cancelled if desired.
     * On response, the call is removed.
     * @param call A NetworkCall
     */
    public void execute(final NetworkCall call){
        if(enqueuedCalls == null) enqueuedCalls = new ArrayList<>();
        call.setOnEnd(() -> {
            if(enqueuedCalls != null) enqueuedCalls.remove(call);
            if(onRequestEnd != null)
                for(NetworkRequestListener listener : onRequestEnd) listener.onNetworkRequestTrigger();
        });
        enqueuedCalls.add(call);

        if(onRequestStart != null)
            for(NetworkRequestListener listener : onRequestStart) listener.onNetworkRequestTrigger();
        call.execute();
    }

    /**
     * Cancels all calls of this controller
     */
    public void cancelAll() {
        if (enqueuedCalls != null)
            for (NetworkCall call : enqueuedCalls)
                call.cancel();
    }

    /**
     * Builds the relevant retrofit object.
     * @param clazz The interface to be built
     * @return the generated object
     */
    protected T buildApi(Class<T> clazz){
        RetrofitApiBuilder builder = new RetrofitApiBuilder();
        return builder.setBaseUrl(BASE_URL)
                .setLanguage("en-us")
                .setTimeout(5000)
                .forceSSL(false)
                .enableLogging(false)
                .build(clazz);
    }

}

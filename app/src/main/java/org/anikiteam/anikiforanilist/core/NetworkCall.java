package org.anikiteam.anikiforanilist.core;

import android.support.annotation.NonNull;

import org.anikiteam.anikiforanilist.core.interfaces.NetworkRequestListener;
import org.anikiteam.anikiforanilist.core.interfaces.NetworkResponseCallback;
import org.anikiteam.anikiforanilist.base.model.NetworkError;
import org.anikiteam.anikiforanilist.debug.Logging;

import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

public class NetworkCall<T> implements Callback<T> {

    private Call<T> call;
    private NetworkResponseCallback<T> responseCallback;
    @Setter private NetworkRequestListener onEnd; //executed on end of the action

    public NetworkCall(Call<T> call, NetworkResponseCallback<T> responseCallback){
        this.call = call;
        this.responseCallback = responseCallback;
    }

    public void execute(){
        call.enqueue(this);
    }

    private void retry(){
        Call retryCall = call.clone();
    }

    public void cancel(){
        if(call.isExecuted() && !call.isCanceled())
            call.cancel();
    }

    public void onErrorEvent(NetworkError networkError) {
        responseCallback.onNetworkError(networkError);
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if(response.isSuccessful()){
            responseCallback.onNetwortResponse(response.body());
        } else {
            // todo: refresh expired token on authenticated calls here
            responseCallback.onNetworkError(new NetworkError(response.code(), response.message()));
        }
        if(onEnd != null) onEnd.onNetworkRequestTrigger();
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        Logging.log(t);
        responseCallback.onNetworkError(new NetworkError(-3, ""));
        if(onEnd != null) onEnd.onNetworkRequestTrigger();
    }
}

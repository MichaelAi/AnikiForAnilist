package org.anikiteam.anikiforanilist.core.interfaces;

import org.anikiteam.anikiforanilist.base.model.NetworkError;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

public interface NetworkResponseCallback<T> {

    void onNetwortResponse(T result);
    void onNetworkError(NetworkError error);
}

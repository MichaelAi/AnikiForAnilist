package org.anikiteam.anikiforanilist.features.home.interfaces;

import android.content.Context;

import org.anikiteam.anikiforanilist.core.interfaces.NetworkResponseCallback;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

public interface HomeDataProvider {

    void getMediaByTypeAndId(Context context, String type, String id, NetworkResponseCallback networkResponseCallback);
}

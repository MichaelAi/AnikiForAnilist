package org.anikiteam.anikiforanilist.features.home.network;

import android.content.Context;

import org.anikiteam.anikiforanilist.core.NetworkCall;
import org.anikiteam.anikiforanilist.base.BaseNetworkController;
import org.anikiteam.anikiforanilist.core.interfaces.NetworkResponseCallback;
import org.anikiteam.anikiforanilist.core.GetMediaByTypeAndIdQuery;
import org.anikiteam.anikiforanilist.features.home.interfaces.HomeDataProvider;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

@SuppressWarnings("unchecked")
public class HomeDataController extends BaseNetworkController implements HomeDataProvider {

    private HomeDataApi api;

    public void getMediaByTypeAndId(Context context, String type, String id, NetworkResponseCallback callback){
        if(api == null) buildApi(HomeDataApi.class);
        GetMediaByTypeAndIdQuery query = new GetMediaByTypeAndIdQuery(context,type,id);
        NetworkCall call = new NetworkCall<>(api.queryMedia(query.query()),callback);
        execute(call);
    }


}

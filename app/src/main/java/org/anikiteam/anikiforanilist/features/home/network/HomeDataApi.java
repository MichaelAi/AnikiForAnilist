package org.anikiteam.anikiforanilist.features.home.network;

import org.anikiteam.anikiforanilist.base.model.Data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

public interface HomeDataApi {

    @POST()
    Call<Data> queryMedia(@Body String query);

}

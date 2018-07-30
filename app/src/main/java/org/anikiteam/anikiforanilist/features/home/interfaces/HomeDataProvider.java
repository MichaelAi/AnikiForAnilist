package org.anikiteam.anikiforanilist.features.home.interfaces;

import com.apollographql.apollo.ApolloCall;

import org.anikiteam.aniki.GetMediaQuery;
import org.anikiteam.aniki.SearchMediaByTypeAndStatusQuery;
import org.anikiteam.aniki.SearchMediaQuery;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

public interface HomeDataProvider {

    void cancel();
    void getNowAiringAnime(ApolloCall.Callback<SearchMediaByTypeAndStatusQuery.Data> callback);
}

package org.anikiteam.anikiforanilist.features.home.network;

import com.apollographql.apollo.ApolloCall;

import org.anikiteam.aniki.SearchMediaByTypeAndStatusQuery;
import org.anikiteam.aniki.SearchMediaQuery;
import org.anikiteam.aniki.type.MediaStatus;
import org.anikiteam.aniki.type.MediaType;
import org.anikiteam.anikiforanilist.AnikiApp;
import org.anikiteam.anikiforanilist.features.home.interfaces.HomeDataProvider;
import org.anikiteam.anikiforanilist.services.GraphQlService;

import javax.inject.Inject;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

@SuppressWarnings("unchecked")
public class HomeDataController implements HomeDataProvider {

    @Inject GraphQlService graphQlService;

    public HomeDataController(){
        AnikiApp.getAppComponent().inject(this);
    }

    @Override
    public void cancel() {

    }

    @Override
    public void getNowAiringAnime(ApolloCall.Callback<SearchMediaByTypeAndStatusQuery.Data> callback) {
        graphQlService.searchByMediaByTypeAndStatus(MediaType.ANIME, MediaStatus.RELEASING, 1, callback);
    }
}

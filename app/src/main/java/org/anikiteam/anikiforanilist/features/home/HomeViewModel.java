package org.anikiteam.anikiforanilist.features.home;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.view.View;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.anikiteam.aniki.GetMediaQuery;
import org.anikiteam.aniki.SearchMediaByTypeAndStatusQuery;
import org.anikiteam.aniki.SearchMediaQuery;
import org.anikiteam.aniki.type.MediaType;
import org.anikiteam.anikiforanilist.AnikiApp;
import org.anikiteam.anikiforanilist.features.home.interfaces.HomeDataProvider;
import org.anikiteam.anikiforanilist.features.home.model.HomeListItem;
import org.anikiteam.anikiforanilist.features.home.network.HomeDataController;
import org.anikiteam.anikiforanilist.services.GraphQlService;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

public class HomeViewModel extends ViewModel {


    private HomeListItem testHomeItem;

    private HomeDataProvider homeDataProvider;
    @Inject GraphQlService graphQlService;

    @Getter public MutableLiveData<String> testTitle;
    @Getter public MutableLiveData<ArrayList<SearchMediaByTypeAndStatusQuery.Medium>> nowAiringAnimeList;

    public HomeViewModel(){
        AnikiApp.getAppComponent().inject(this);
        testTitle = new MutableLiveData<>();
        nowAiringAnimeList = new MutableLiveData<>();
        homeDataProvider = new HomeDataController();
    }


//    public void testQuery1() {
//        ApolloCall.Callback<GetMediaQuery.Data> callback = new ApolloCall.Callback<GetMediaQuery.Data>() {
//            @Override
//            public void onResponse(@NotNull Response<GetMediaQuery.Data> response) {
//                testTitle.postValue(response.data().Media().title().english());
//            }
//
//            @Override
//            public void onFailure(@NotNull ApolloException e) {
//
//            }
//        };
//        graphQlService.searchByMediaByTypeAndString("cowboy",1,callback);
//    }

    public void fetchNowAiringAnime(){

        ApolloCall.Callback<SearchMediaByTypeAndStatusQuery.Data> callback = new ApolloCall.Callback<SearchMediaByTypeAndStatusQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<SearchMediaByTypeAndStatusQuery.Data> response) {
                nowAiringAnimeList.postValue(new ArrayList<>(response.data().Page().media()));
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        };
        homeDataProvider.getNowAiringAnime(callback);
    }

}

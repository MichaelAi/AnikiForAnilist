package org.anikiteam.anikiforanilist.features.home;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.anikiteam.aniki.SearchMediaByTypeAndStatusQuery;
import org.anikiteam.anikiforanilist.features.home.interfaces.HomeDataProvider;
import org.anikiteam.anikiforanilist.features.home.model.HomeListGroup;
import org.anikiteam.anikiforanilist.features.home.model.HomeListItem;
import org.anikiteam.anikiforanilist.features.home.network.HomeDataController;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

public class HomeViewModel extends ViewModel {

    private HomeDataProvider homeDataProvider;

    @Getter public MutableLiveData<String> testTitle;
    @Getter public MutableLiveData<ArrayList<HomeListGroup>> nowAiringAnimeList;

    public HomeViewModel(){
        testTitle = new MutableLiveData<>();
        nowAiringAnimeList = new MutableLiveData<>();
        homeDataProvider = new HomeDataController();
    }

    public void fetchNowAiringAnime(){

        List<HomeListGroup> data = new ArrayList<>();
        HomeListGroup homeListGroup = new HomeListGroup("group 1");
        homeListGroup.setMedia(new ArrayList<>());
        data.add(homeListGroup);

        ApolloCall.Callback<SearchMediaByTypeAndStatusQuery.Data> callback = new ApolloCall.Callback<SearchMediaByTypeAndStatusQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<SearchMediaByTypeAndStatusQuery.Data> response) {
                data.get(0).setMedia(new ArrayList<>(response.data().Page().media()));
                nowAiringAnimeList.postValue((ArrayList<HomeListGroup>) data);
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        };
        homeDataProvider.getNowAiringAnime(callback);
    }

}

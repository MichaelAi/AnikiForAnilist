package org.anikiteam.anikiforanilist.features.home;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.anikiteam.aniki.GetMediaQuery;
import org.anikiteam.aniki.SearchMediaQuery;
import org.anikiteam.aniki.type.MediaType;
import org.anikiteam.anikiforanilist.AnikiApp;
import org.anikiteam.anikiforanilist.services.GraphQlService;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

public class HomeViewModel extends ViewModel {

    @Inject GraphQlService graphQlService;

    @Getter public MutableLiveData<String> testTitle;

    public HomeViewModel(){
        testTitle = new MutableLiveData<>();
        AnikiApp.getAppComponent().inject(this);
    }


    public void testQuery1() {
        ApolloCall.Callback<SearchMediaQuery.Data> callback = new ApolloCall.Callback<SearchMediaQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<SearchMediaQuery.Data> response) {
                Collection<SearchMediaQuery.Medium> result = response.data().Page().media();
                for(SearchMediaQuery.Medium item : result){
                    Log.d("Apollo",item.title().english()
                            + " " +
                            item.title().native_()
                            + " "
                            + item.status());
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        };

        graphQlService.searchByMediaByTypeAndString("cowboy",1,callback);
    }

    public void testQuery2() {
        ApolloCall.Callback<GetMediaQuery.Data> callback = new ApolloCall.Callback<GetMediaQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetMediaQuery.Data> response) {
                testTitle.postValue(response.data().Media().title().english());
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        };
        graphQlService.getMediaByIdAndType(1,MediaType.ANIME,callback);
    }

}

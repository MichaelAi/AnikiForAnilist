package org.anikiteam.anikiforanilist.services;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;

import org.anikiteam.aniki.GetMediaQuery;
import org.anikiteam.aniki.SearchMediaByTypeAndStatusQuery;
import org.anikiteam.aniki.SearchMediaQuery;
import org.anikiteam.aniki.type.MediaStatus;
import org.anikiteam.aniki.type.MediaType;
import org.anikiteam.anikiforanilist.core.ApolloApiBuilder;

/**
 * Created by Mike Ai on 22-Jul-18.
 */

public class GraphQlService {

    ApolloClient apolloClient;
    private final String BASE_URL = "https://graphql.anilist.co"; //todo: move this

    public GraphQlService(){
        ApolloApiBuilder builder = new ApolloApiBuilder();
        apolloClient = builder.setLanguage("en-US")
                .forceSSL(true)
                .setTimeout(10000)
                .setBaseUrl(BASE_URL)
                .build();
    }

    public void getMediaByIdAndType(int id, MediaType type, ApolloCall.Callback<GetMediaQuery.Data> callback){
        GetMediaQuery query = GetMediaQuery.builder()
                .id(id)
                .type(type)
                .build();
        apolloClient.query(query).enqueue(callback);
    }

    public void searchByMediaByTypeAndString(String search, int page, ApolloCall.Callback<SearchMediaQuery.Data> callback){
        SearchMediaQuery query = SearchMediaQuery.builder()
                .search(search)
                .page(page)
                .perPage(10)
                .build();
        apolloClient.query(query).enqueue(callback);
    }

    public void searchByMediaByTypeAndStatus(MediaType type, MediaStatus status, int page, ApolloCall.Callback<SearchMediaByTypeAndStatusQuery.Data> callback){
        SearchMediaByTypeAndStatusQuery query = SearchMediaByTypeAndStatusQuery.builder()
                .type(type)
                .status(status)
                .page(page)
                .perPage(10)
                .build();
        apolloClient.query(query).enqueue(callback);
    }
}

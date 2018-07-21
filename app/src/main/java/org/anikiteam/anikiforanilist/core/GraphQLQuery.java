package org.anikiteam.anikiforanilist.core;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import lombok.Getter;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

abstract class GraphQLQuery {

    private final String GRAPHQL_EXT = ".graphql";

    private String query;

    void parseFile(Context context, String filename){
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(context.getAssets().open(filename + GRAPHQL_EXT)))){
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);}
        } catch (IOException e) {
            // todo: log exception
        }
        query = stringBuilder.toString();
    }

    // consider using a graphql library
    void setArgument(String name, String value){
        if(value.contains("$")) value = value.replace("$","");
        if(query == null) throw new NullPointerException();
        if(query.substring(query.indexOf("Media")).contains("$"+name)){
            query = query.substring(query.indexOf("Media")).replace("$"+name,value);
        }
    }

    public String query(){
        return query;
    }

}

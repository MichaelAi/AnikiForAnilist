package org.anikiteam.anikiforanilist.di;

import android.content.Context;
import android.support.annotation.NonNull;

import org.anikiteam.anikiforanilist.AnikiApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mike Ai on 22-Jul-18.
 */

@Module
public class AppModule {
    private Context appContext;
    public AppModule(@NonNull AnikiApp appContext){
        this.appContext = appContext;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return appContext;
    }
}

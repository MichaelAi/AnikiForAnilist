package org.anikiteam.anikiforanilist;

import android.app.Application;

import org.anikiteam.anikiforanilist.di.AppComponent;
import org.anikiteam.anikiforanilist.di.AppModule;
import org.anikiteam.anikiforanilist.di.DaggerAppComponent;

import lombok.Getter;

/**
 * Created by Mike Ai on 18-Jul-18.
 */

public class AnikiApp extends Application {

    @Getter private static AppComponent appComponent;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }
}

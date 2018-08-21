package org.anikiteam.anikiforanilist.di;

import org.anikiteam.anikiforanilist.features.home.HomeViewModel;
import org.anikiteam.anikiforanilist.features.home.network.HomeDataController;
import org.anikiteam.anikiforanilist.features.home.view.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Mike Ai on 22-Jul-18.
 */

@Component(modules = {AppModule.class,
        MediaModule.class})
@Singleton
public interface AppComponent {
    void inject(HomeDataController homeDataController);
}

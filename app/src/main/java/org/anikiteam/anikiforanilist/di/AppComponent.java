package org.anikiteam.anikiforanilist.di;

import org.anikiteam.anikiforanilist.features.home.view.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Mike Ai on 22-Jul-18.
 */

@Component(modules = {AppModule.class,
        GraphQlModule.class})
@Singleton
public interface AppComponent {
    void inject(HomeActivity homeActivity);
}

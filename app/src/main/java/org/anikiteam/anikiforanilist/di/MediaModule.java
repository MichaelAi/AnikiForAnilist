package org.anikiteam.anikiforanilist.di;

import org.anikiteam.anikiforanilist.services.GraphQlService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mike Ai on 22-Jul-18.
 */

@Module
@Singleton
public class GraphQlModule {

    @Provides
    @Singleton
    public GraphQlService providesGraphQlService(){
        return new GraphQlService();
    }

}

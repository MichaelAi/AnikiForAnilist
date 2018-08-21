package org.anikiteam.anikiforanilist.di;

import org.anikiteam.anikiforanilist.services.MediaService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mike Ai on 22-Jul-18.
 */

@Module
@Singleton
public class MediaModule {

    @Provides
    @Singleton
    public MediaService providesMediaService(){
        return new MediaService();
    }

}

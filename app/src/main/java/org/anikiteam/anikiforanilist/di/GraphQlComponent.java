package org.anikiteam.anikiforanilist.di;

import dagger.Component;

/**
 * Created by Mike Ai on 22-Jul-18.
 */

@Component(modules = { GraphQlModule.class, AppModule.class })
public interface GraphQlComponent {
}

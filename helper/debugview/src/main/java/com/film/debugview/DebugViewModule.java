package com.film.debugview;

import com.film.app.core.base.ViewContainer;
import dagger.Binds;
import dagger.Module;

@Module(includes = { DebugViewDataModule.class, MockApiModule.class })
public abstract class DebugViewModule {
    @Binds abstract ViewContainer provideViewContainer(DebugViewContainer debugViewContainer);
}

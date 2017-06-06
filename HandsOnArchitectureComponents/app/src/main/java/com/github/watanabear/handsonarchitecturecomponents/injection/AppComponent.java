package com.github.watanabear.handsonarchitecturecomponents.injection;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ryo on 2017/06/06.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    interface Injectable {
        void inject(AppComponent appComponent);
    }
}

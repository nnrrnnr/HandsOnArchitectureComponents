package com.github.watanabear.handsonarchitecturecomponents.dagger;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by watanabear on 2017/05/30.
 */
@Singleton
@Component(modules = {HandOnModule.class})
public interface HandOnComponent {



    interface Injectable {
        void inject(HandOnComponent handOnComponent);
    }
}

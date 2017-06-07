package com.github.watanabear.handsonarchitecturecomponents.injection;

import com.github.watanabear.handsonarchitecturecomponents.ui.event.add.EventAddViewModel;
import com.github.watanabear.handsonarchitecturecomponents.ui.event.list.EventListViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ryo on 2017/06/06.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(EventListViewModel eventListViewModel);

    void inject(EventAddViewModel eventAddViewModel);

    interface Injectable {
        void inject(AppComponent appComponent);
    }
}

package com.github.watanabear.handsonarchitecturecomponents.injection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.github.watanabear.handsonarchitecturecomponents.MyApp;

/**
 * Created by ryo on 2017/06/07.
 */

public class MyAppFactory extends ViewModelProvider.NewInstanceFactory {
    private MyApp app;

    public MyAppFactory(MyApp app) {
        this.app = app;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        T t = super.create(modelClass);
        if (t instanceof AppComponent.Injectable) {
            ((AppComponent.Injectable) t).inject(app.getAppComponent());
        }
        return t;
    }
}

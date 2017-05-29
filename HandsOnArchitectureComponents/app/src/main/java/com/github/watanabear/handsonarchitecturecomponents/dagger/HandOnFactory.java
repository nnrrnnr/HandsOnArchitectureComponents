package com.github.watanabear.handsonarchitecturecomponents.dagger;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.github.watanabear.handsonarchitecturecomponents.HandOnApplication;

/**
 * Created by watanabear on 2017/05/30.
 */

public class HandOnFactory extends ViewModelProvider.NewInstanceFactory {

    private HandOnApplication application;

    public HandOnFactory(HandOnApplication application) {
        this.application = application;
    }

    public <T extends ViewModel> T create(Class<T> modelClass) {
        T t = super.create(modelClass);
        if(t instanceof HandOnComponent.Injectable) {
            ((HandOnComponent.Injectable) t).inject(application.getHandOnComponent());
        }
        return t;
    }
}

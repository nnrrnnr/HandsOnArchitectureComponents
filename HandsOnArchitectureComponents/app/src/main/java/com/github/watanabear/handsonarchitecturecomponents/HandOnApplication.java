package com.github.watanabear.handsonarchitecturecomponents;

import android.app.Application;

import com.github.watanabear.handsonarchitecturecomponents.dagger.DaggerHandOnComponent;
import com.github.watanabear.handsonarchitecturecomponents.dagger.HandOnComponent;
import com.github.watanabear.handsonarchitecturecomponents.dagger.HandOnModule;
import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * Created by watanabear on 2017/05/30.
 */

public class HandOnApplication extends Application {

    private final HandOnComponent handOnComponent = createHandOnComponent();

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
    }

    protected HandOnComponent createHandOnComponent() {
        return DaggerHandOnComponent.builder()
                .handOnModule(new HandOnModule(this))
                .build();
    }

    public HandOnComponent getHandOnComponent() {
        return handOnComponent;
    }
}

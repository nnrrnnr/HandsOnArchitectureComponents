package com.github.watanabear.handsonarchitecturecomponents;

import android.app.Application;

import com.github.watanabear.handsonarchitecturecomponents.injection.AppComponent;
import com.github.watanabear.handsonarchitecturecomponents.injection.AppModule;
import com.github.watanabear.handsonarchitecturecomponents.injection.DaggerAppComponent;
import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * Created by ryo on 2017/06/06.
 */

public class MyApp extends Application {

    private final AppComponent appComponent = createAppComponent();

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
    }

    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() { return appComponent; }
}

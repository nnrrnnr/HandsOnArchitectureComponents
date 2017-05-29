package com.github.watanabear.handsonarchitecturecomponents.dagger;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.github.watanabear.handsonarchitecturecomponents.HandOnApplication;
import com.github.watanabear.handsonarchitecturecomponents.database.EventDataBase;
import com.github.watanabear.handsonarchitecturecomponents.repository.EventRepository;
import com.github.watanabear.handsonarchitecturecomponents.repository.EventRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by watanabear on 2017/05/30.
 */
@Module
public class HandOnModule {

    private HandOnApplication application;

    public HandOnModule(HandOnApplication application) {
        this.application = application;
    }

    @Provides
    Context applicationContext() {
        return application;
    }

    @Singleton
    @Provides
    EventDataBase provideEventDataBase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), EventDataBase.class, "event_db").build();
    }

    @Singleton
    @Provides
    EventRepository provideEventRepository(EventDataBase eventDataBase) {
        return new EventRepositoryImpl(eventDataBase);
    }

}

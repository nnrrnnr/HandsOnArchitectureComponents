package com.github.watanabear.handsonarchitecturecomponents.injection;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.github.watanabear.handsonarchitecturecomponents.MyApp;
import com.github.watanabear.handsonarchitecturecomponents.db.EventDatabase;
import com.github.watanabear.handsonarchitecturecomponents.repository.EventRepository;
import com.github.watanabear.handsonarchitecturecomponents.repository.EventRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ryo on 2017/06/06.
 */
@Module
public class AppModule {

    private MyApp myApp;

    public AppModule(MyApp myApp) {
        this.myApp = myApp;
    }

    @Provides
    Context appContext() { return myApp; }

    @Provides
    @Singleton
    EventRepository provideEventRepository(EventDatabase eventDatabase) {
        return new EventRepositoryImpl(eventDatabase);
    }

    @Provides
    @Singleton
    EventDatabase provideEventDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), EventDatabase.class, "event_db").build();
    }

}

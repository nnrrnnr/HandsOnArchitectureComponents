package com.github.watanabear.handsonarchitecturecomponents.repository;

import android.arch.lifecycle.LiveData;

import com.github.watanabear.handsonarchitecturecomponents.db.EventDatabase;
import com.github.watanabear.handsonarchitecturecomponents.entity.Event;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Created by ryo on 2017/06/06.
 */

public class EventRepositoryImpl implements EventRepository {

    @Inject
    EventDatabase eventDatabase;

    public EventRepositoryImpl(EventDatabase eventDatabase) {
        this.eventDatabase = eventDatabase;
    }

    @Override
    public Completable addEvent(Event event) {
        return Completable.fromAction(() -> eventDatabase.eventDao().addEvent(event));
    }

    @Override
    public LiveData<List<Event>> getEvents() {
        return eventDatabase.eventDao().getEvents(LocalDateTime.now());
    }

    @Override
    public Completable deleteEvent(Event event) {
        return Completable.fromAction(() -> eventDatabase.eventDao().deleteEvent(event));
    }
}

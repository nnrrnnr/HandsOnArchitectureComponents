package com.github.watanabear.handsonarchitecturecomponents.repository;

import android.arch.lifecycle.LiveData;

import com.github.watanabear.handsonarchitecturecomponents.database.EventDataBase;
import com.github.watanabear.handsonarchitecturecomponents.model.Event;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;

/**
 * Created by watanabear on 2017/05/30.
 */

public class EventRepositoryImpl implements EventRepository {

    @Inject
    EventDataBase eventDataBase;

    public EventRepositoryImpl(EventDataBase eventDataBase) {
        this.eventDataBase = eventDataBase;
    }

    @Override
    public Completable addEvent(Event event) {
        return Completable.fromAction(() -> eventDataBase.eventDao().addEvent(event));
    }

    @Override
    public LiveData<List<Event>> getEvents() {
        return eventDataBase.eventDao().getEvents(LocalDateTime.now());
    }

    @Override
    public Completable deleteEvent(Event event) {
        return Completable.fromAction(() -> eventDataBase.eventDao().deleteEvent(event));
    }
}

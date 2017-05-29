package com.github.watanabear.handsonarchitecturecomponents.repository;

import android.arch.lifecycle.LiveData;

import com.github.watanabear.handsonarchitecturecomponents.model.Event;

import java.util.List;

import io.reactivex.Completable;

/**
 * Created by watanabear on 2017/05/30.
 */

public interface EventRepository {

    Completable addEvent(Event event);

    LiveData<List<Event>> getEvents();

    Completable deleteEvent(Event event);
}

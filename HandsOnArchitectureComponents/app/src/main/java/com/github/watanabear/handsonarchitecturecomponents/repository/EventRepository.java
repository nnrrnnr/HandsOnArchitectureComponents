package com.github.watanabear.handsonarchitecturecomponents.repository;

import android.arch.lifecycle.LiveData;

import com.github.watanabear.handsonarchitecturecomponents.entity.Event;

import java.util.List;

import io.reactivex.Completable;

/**
 * Created by ryo on 2017/06/06.
 */

public interface EventRepository {

    Completable addEvent(Event event);

    LiveData<List<Event>> getEvents();

    Completable deleteEvent(Event event);
}

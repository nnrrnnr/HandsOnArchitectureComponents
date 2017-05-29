package com.github.watanabear.handsonarchitecturecomponents.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.github.watanabear.handsonarchitecturecomponents.model.Event;

import org.threeten.bp.LocalDateTime;

import java.util.List;

/**
 * Created by watanabear on 2017/05/30.
 */
@Dao
public interface EventDao {
    @Query("SELECT * FROM " + Event.TABLE_NAME + " WHERE " + Event.DATE_FIELD + " > :minDate")
    LiveData<List<Event>> getEvents(LocalDateTime minDate);

    void add(Event event);

    void delete(Event event);

    void update(Event event);
}

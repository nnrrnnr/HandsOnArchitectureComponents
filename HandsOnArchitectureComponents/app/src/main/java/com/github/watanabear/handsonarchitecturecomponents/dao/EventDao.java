package com.github.watanabear.handsonarchitecturecomponents.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.github.watanabear.handsonarchitecturecomponents.entity.Event;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by ryo on 2017/06/06.
 */
@Dao
public interface EventDao {
    @Query("SELECT * FROM " + Event.TABLE_NAME + " WHERE " + Event.DATE_FIELD + " > :minDate")
    LiveData<List<Event>> getEvents(LocalDateTime minDate);

    @Insert(onConflict = REPLACE)
    void addEvent(Event event);

    @Delete
    void deleteEvent(Event event);

    @Update(onConflict = REPLACE)
    void updateEvent(Event event);

}

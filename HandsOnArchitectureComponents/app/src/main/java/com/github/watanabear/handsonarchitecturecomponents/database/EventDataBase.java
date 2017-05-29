package com.github.watanabear.handsonarchitecturecomponents.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.github.watanabear.handsonarchitecturecomponents.converter.DateTypeConverter;
import com.github.watanabear.handsonarchitecturecomponents.dao.EventDao;
import com.github.watanabear.handsonarchitecturecomponents.model.Event;

/**
 * Created by watanabear on 2017/05/30.
 */
@Database(entities = {Event.class}, version = 1)
@TypeConverters(DateTypeConverter.class)
public abstract class EventDataBase extends RoomDatabase {

    public abstract EventDao eventDao();
}

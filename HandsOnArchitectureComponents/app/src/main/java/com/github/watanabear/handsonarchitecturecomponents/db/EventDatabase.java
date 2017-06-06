package com.github.watanabear.handsonarchitecturecomponents.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.github.watanabear.handsonarchitecturecomponents.dao.EventDao;
import com.github.watanabear.handsonarchitecturecomponents.entity.Event;

/**
 * Created by ryo on 2017/06/06.
 */
@Database(entities = {Event.class}, version = 1)
@TypeConverters(DateTypeConverter.class)
public abstract class EventDatabase extends RoomDatabase {

    public abstract EventDao eventDao();
}

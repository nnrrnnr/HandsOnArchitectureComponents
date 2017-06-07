package com.github.watanabear.handsonarchitecturecomponents.ui.event.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.github.watanabear.handsonarchitecturecomponents.entity.Event;
import com.github.watanabear.handsonarchitecturecomponents.injection.AppComponent;
import com.github.watanabear.handsonarchitecturecomponents.repository.EventRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by ryo on 2017/06/07.
 */

public class EventListViewModel extends ViewModel implements AppComponent.Injectable{

    @Inject
    EventRepository eventRepository;

    private LiveData<List<Event>> events = new MutableLiveData<>();

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.inject(this);
        events = eventRepository.getEvents();
    }

    public LiveData<List<Event>> getEvents() {
        return events;
    }

    public void deleteEvent(Event event) {
        eventRepository.deleteEvent(event)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Timber.d("onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.d("onError");
                    }
                });
    }
}

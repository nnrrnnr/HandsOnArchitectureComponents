package com.github.watanabear.handsonarchitecturecomponents.ui.event.add;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.github.watanabear.handsonarchitecturecomponents.entity.Event;
import com.github.watanabear.handsonarchitecturecomponents.injection.AppComponent;
import com.github.watanabear.handsonarchitecturecomponents.repository.EventRepository;

import org.threeten.bp.LocalDateTime;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by ryo on 2017/06/08.
 */

public class EventAddViewModel extends ViewModel implements AppComponent.Injectable {

    @Inject
    EventRepository eventRepository;
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> description = new MutableLiveData<>();
    private MutableLiveData<LocalDateTime> eventDateTime = new MutableLiveData<>();

    public EventAddViewModel() {
        eventDateTime.setValue(LocalDateTime.now());
    }

    public LiveData<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public LiveData<String> getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }

    public MutableLiveData<LocalDateTime> getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime.setValue(eventDateTime);
    }

    public void addEvent() {
        Event e = new Event(0, name.getValue(), description.getValue(), eventDateTime.getValue());
        eventRepository.addEvent(e).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Timber.d("onSubscribe");
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete - successfully added event");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.d("onError - add:", e);
                    }
                });
    }

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.inject(this);
    }
}

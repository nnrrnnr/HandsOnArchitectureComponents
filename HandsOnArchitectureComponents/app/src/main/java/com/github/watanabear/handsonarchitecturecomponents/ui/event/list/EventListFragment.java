package com.github.watanabear.handsonarchitecturecomponents.ui.event.list;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.watanabear.handsonarchitecturecomponents.MyApp;
import com.github.watanabear.handsonarchitecturecomponents.R;
import com.github.watanabear.handsonarchitecturecomponents.entity.Event;
import com.github.watanabear.handsonarchitecturecomponents.injection.MyAppFactory;
import com.github.watanabear.handsonarchitecturecomponents.ui.event.add.EventAddActivity;

import java.util.ArrayList;

public class EventListFragment extends LifecycleFragment {

    private static final String TAG = "EventListFragment";
    private EventAdapter adapter;
    private EventListViewModel eventListViewModel;

    private View.OnClickListener deleteClickListener = v -> {
        Event e = (Event) v.getTag();
        eventListViewModel.deleteEvent(e);
    };

    private View.OnClickListener itemClickListener = v -> {
        Event e = (Event) v.getTag();
        Toast.makeText(getContext(), "clicked:" + e.getName(), Toast.LENGTH_LONG).show();
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event_list, container, false);
        setupRecyclerView(v);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_add);
        // TODO: 2017/06/07  activity
        fab.setOnClickListener(v1 -> startActivity(new Intent(getContext(), EventAddActivity.class)));

        eventListViewModel = ViewModelProviders.of(this,
                new MyAppFactory((MyApp) getActivity().getApplication())).get(EventListViewModel.class);
        eventListViewModel.getEvents().observe(this, events -> {
            Log.d(TAG, "onCreateView: " + events);
            adapter.setItems(events);
        });
        return v;
    }

    private void setupRecyclerView(View v) {
        RecyclerView recycler = (RecyclerView) v.findViewById(R.id.recycler_view_list_events);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);

        adapter = new EventAdapter(
                new ArrayList<>(), getContext(), itemClickListener, deleteClickListener);
        recycler.setAdapter(adapter);
        final DividerItemDecoration deco =
                new DividerItemDecoration(recycler.getContext(), manager.getOrientation());
        recycler.addItemDecoration(deco);
    }

}

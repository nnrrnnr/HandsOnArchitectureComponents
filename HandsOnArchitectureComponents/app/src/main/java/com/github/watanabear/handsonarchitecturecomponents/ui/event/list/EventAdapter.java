package com.github.watanabear.handsonarchitecturecomponents.ui.event.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.watanabear.handsonarchitecturecomponents.R;
import com.github.watanabear.handsonarchitecturecomponents.entity.Event;

import java.util.List;

/**
 * Created by ryo on 2017/06/07.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{

    private final Context context;
    private List<Event> items;
    private View.OnClickListener deleteClickListener;
    private View.OnClickListener viewClickListener;

    EventAdapter(List<Event> items, Context context,
                 View.OnClickListener viewClickListener, View.OnClickListener deleteClickListener) {
        this.items = items;
        this.context = context;
        this.viewClickListener = viewClickListener;
        this.deleteClickListener = deleteClickListener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_event, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event item = items.get(position);
        holder.eventTextView.setText(item.getName());
        holder.descriptionTextView.setText(item.getDescription());
        holder.countdownTextView.setText(context.getString(R.string.days_until, item.getDaysUntil()));
        holder.itemView.setTag(item);
        holder.deleteButton.setTag(item);
        holder.deleteButton.setOnClickListener(deleteClickListener);
        holder.itemView.setOnClickListener(viewClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void setItems(List<Event> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {

        TextView eventTextView;
        TextView countdownTextView;
        TextView descriptionTextView;
        ImageButton deleteButton;

        EventViewHolder(View v) {
            super(v);
            eventTextView = (TextView) v.findViewById(R.id.text_view_event_name);
            countdownTextView = (TextView) v.findViewById(R.id.text_view_countdown);
            descriptionTextView = (TextView) v.findViewById(R.id.text_view_event_description);
            deleteButton = (ImageButton) v.findViewById(R.id.button_delete);
        }
    }
}

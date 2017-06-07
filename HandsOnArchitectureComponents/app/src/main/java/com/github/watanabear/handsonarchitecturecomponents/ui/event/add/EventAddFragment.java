package com.github.watanabear.handsonarchitecturecomponents.ui.event.add;


import android.app.DatePickerDialog;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.github.watanabear.handsonarchitecturecomponents.MyApp;
import com.github.watanabear.handsonarchitecturecomponents.R;
import com.github.watanabear.handsonarchitecturecomponents.injection.MyAppFactory;

import org.threeten.bp.LocalDateTime;

public class EventAddFragment extends LifecycleFragment implements DatePickerDialog.OnDateSetListener{

    private EditText editTextTitle, editTextDescription;
    private Button buttonAddEvent, buttonSetDate;
    private TextView textViewCurrentDate;
    private EventAddViewModel eventAddViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event_add, container, false);
        setupViews(v);
        setupClickListeners();
        setupViewModel();
        return v;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        eventAddViewModel.setEventDateTime(LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0));
        textViewCurrentDate.setText(eventAddViewModel.getEventDateTime().getValue().toString());
    }

    private void setupViewModel() {
        eventAddViewModel = ViewModelProviders.of(this,
                new MyAppFactory((MyApp) getActivity().getApplication())).get(EventAddViewModel.class);
        editTextTitle.setText(eventAddViewModel.getName().getValue());
        editTextDescription.setText(eventAddViewModel.getDescription().getValue());
        textViewCurrentDate.setText(eventAddViewModel.getEventDateTime().getValue().toString());
    }

    private void setupClickListeners() {
        editTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                eventAddViewModel.setName(s.toString());
            }
        });
        editTextDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                eventAddViewModel.setDescription(s.toString());
            }
        });
        buttonAddEvent.setOnClickListener(v -> {
            eventAddViewModel.addEvent();
            getActivity().finish();
        });
        buttonSetDate.setOnClickListener(v -> {
            LocalDateTime time = eventAddViewModel.getEventDateTime().getValue();
            DatePickerDialog dialog = new DatePickerDialog(
                    getContext(),this, time.getYear(), time.getMonthValue() -1, time.getDayOfMonth()
            );
            dialog.show();
        });
    }

    private void setupViews(View view) {
        buttonAddEvent = (Button) view.findViewById(R.id.button_add);
        editTextTitle = (EditText) view.findViewById(R.id.edit_text_title);
        editTextDescription = (EditText) view.findViewById(R.id.edit_text_description);
        buttonSetDate = (Button) view.findViewById(R.id.button_set_date);
        textViewCurrentDate = (TextView) view.findViewById(R.id.text_view_date_set);
    }




}

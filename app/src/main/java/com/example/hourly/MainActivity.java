package com.example.hourly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.hourlysharedprefs";
    private boolean mButtonStatePressed;
    private Button mButtonNotify;
    private NumberPicker mPickerMinutes;
    private NumberPicker mPickerHours;

    private final String BUTTON_STATE_KEY = "button_state";
    private final String PICKER_VALUE_HOURS_KEY = "picker_value_hours";
    private final String PICKER_VALUE_MINUTES_KEY = "picker_value_minutes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load preferences
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        //init Views
        InitializePickers();
        InitializeButton();
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putBoolean(BUTTON_STATE_KEY, mButtonStatePressed);
        preferencesEditor.putInt(PICKER_VALUE_HOURS_KEY, mPickerHours.getValue());
        preferencesEditor.putInt(PICKER_VALUE_MINUTES_KEY, mPickerMinutes.getValue());
        preferencesEditor.apply(); // this is asynchronously. for synchronously use commit()
    }

    private void InitializeButton() {
        mButtonNotify = findViewById(R.id.button_notify);
        mButtonStatePressed = mPreferences.getBoolean(BUTTON_STATE_KEY, false);
        updateButtonView();
    }

    private void InitializePickers() {
        mPickerHours = findViewById(R.id.picker_hours);
        mPickerMinutes = findViewById(R.id.picker_minutes);

        // set available values
        mPickerHours.setMinValue(0);
        mPickerHours.setMaxValue(24);
        mPickerMinutes.setMinValue(1);
        mPickerMinutes.setMaxValue(60);

        // set defaults
        mPickerHours.setValue(mPreferences.getInt(PICKER_VALUE_HOURS_KEY, 0));
        mPickerMinutes.setValue(mPreferences.getInt(PICKER_VALUE_MINUTES_KEY, 30));

    }

    public void notify(View view) {
        mButtonStatePressed = !mButtonStatePressed;
        updateButtonView();
    }

    private void updateButtonView() {
        int color = mButtonStatePressed ? R.color.buttonPushed : R.color.buttonClear;
        mButtonNotify.setBackgroundColor(getResources().getColor(color));

        int picker_hours_value = mPickerHours.getValue();
        int picker_minutes_value = mPickerMinutes.getValue();
        Toast.makeText(this, "Notification are " + (mButtonStatePressed ? "On" : "Off") + " with timer " + picker_hours_value + ":" + picker_minutes_value, Toast.LENGTH_SHORT).show();
    }
}

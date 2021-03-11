    package com.example.hourly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

    public class MainActivity extends AppCompatActivity {
        private boolean mButtonPressed;
        private Button mButtonNotify;
        private SharedPreferences mPreferences;
        private String sharedPrefFile = "com.example.android.hourlysharedprefs";
        private final String BUTTON_STATE_KEY = "button_state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init views
        mButtonNotify = findViewById(R.id.button_notify);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        // Restore prefs
        mButtonPressed = mPreferences.getBoolean(BUTTON_STATE_KEY, false);
        updateButton();
    }

        @Override
        protected void onPause() {
            super.onPause();

            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putBoolean(BUTTON_STATE_KEY, mButtonPressed);
            preferencesEditor.apply(); // this is asynchronously. for synchronously use commit()
        }

        public void notify(View view) {
            mButtonPressed = !mButtonPressed;
            updateButton();
        }

        private void updateButton(){
            int color = mButtonPressed ? R.color.buttonPushed : R.color.buttonClear;
            mButtonNotify.setBackgroundColor(getResources().getColor(color));
            Toast.makeText(this, "Reminders are " + (mButtonPressed ? "On" : "Off"), Toast.LENGTH_SHORT).show();
        }
    }

package com.mykotlinapps.myapplication.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mykotlinapps.myapplication.R
import com.mykotlinapps.myapplication.ui.fragments.SettingsFragment

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings_container, SettingsFragment())
                .commit()
        }
    }
}

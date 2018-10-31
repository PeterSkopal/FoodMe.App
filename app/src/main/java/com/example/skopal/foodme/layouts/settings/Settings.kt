package com.example.skopal.foodme.layouts.settings


import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.example.skopal.foodme.MainActivity
import com.example.skopal.foodme.R

class Settings : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        (activity as MainActivity).setActionBarTitle(getString(R.string.title_settings))
        addPreferencesFromResource(R.xml.preferences)
    }

}

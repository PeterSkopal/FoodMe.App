package com.example.skopal.foodme.layouts.settings


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skopal.foodme.MainActivity
import com.example.skopal.foodme.R

class Settings : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        (activity as MainActivity).setActionBarTitle(getString(R.string.title_settings))
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

}

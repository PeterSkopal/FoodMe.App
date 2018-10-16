package com.example.skopal.foodme.layouts.settings


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.skopal.foodme.LoginActivity
import com.example.skopal.foodme.MainActivity
import com.example.skopal.foodme.R

class Settings : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        (activity as MainActivity).setActionBarTitle(getString(R.string.title_settings))

        val button = view.findViewById<Button>(R.id.logout_button)
        button.setOnClickListener {
            (activity as MainActivity).removeKeys()
            (activity as MainActivity).changeActivity(LoginActivity::class.java)
        }

        return view
    }

}

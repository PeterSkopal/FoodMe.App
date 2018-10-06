package com.example.skopal.foodme.layouts.mykitchen


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skopal.foodme.MainActivity
import com.example.skopal.foodme.R

class MyKitchen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        (activity as MainActivity).setActionBarTitle(getString(R.string.title_kitchen))
        return inflater.inflate(R.layout.fragment_my_kitchen, container, false)
    }

}

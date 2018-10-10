package com.example.skopal.foodme.layouts.scanner


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.skopal.foodme.MainActivity
import com.example.skopal.foodme.R
import com.example.skopal.foodme.services.FoodMeApiPing
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

class Scanner : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_scanner, container, false)

        (activity as MainActivity).setActionBarTitle(getString(R.string.title_scanner))

        val text = view.findViewById<TextView>(R.id.scanner_text)

        FoodMeApiPing().getPing { res ->
            GlobalScope.launch(Dispatchers.Main) {
                text.text = "${text.text}: $res"
            }
        }

        return view
    }
    
}

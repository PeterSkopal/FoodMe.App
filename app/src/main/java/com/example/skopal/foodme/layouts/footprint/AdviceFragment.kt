package com.example.skopal.foodme.layouts.footprint

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skopal.foodme.MainActivity
import com.example.skopal.foodme.R
import com.example.skopal.foodme.classes.AdviceList
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_advice_list.*

class AdviceFragment : Fragment() {

    val gson = Gson()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_advice_list, container, false)
        (activity as MainActivity).setActionBarTitle(getString(R.string.title_advice))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        advice_list?.apply {

            val text = resources.openRawResource(R.raw.advice)
                    .bufferedReader().use { it.readText() }

            val arr = gson.fromJson(text, AdviceList::class.java)

            adapter = MyAdviceRecyclerViewAdapter(arr.advice)
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            val itemDecor = DividerItemDecoration(context, resources.configuration.orientation)
            this.addItemDecoration(itemDecor)
        }
    }

}


package com.example.skopal.foodme.layouts.mykitchen


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.skopal.foodme.MainActivity
import com.example.skopal.foodme.R

class MyKitchen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_my_kitchen, container, false)

        (activity as MainActivity).setActionBarTitle(getString(R.string.title_kitchen))

        val card = view.findViewById<CardView>(R.id.card)
        card.setOnClickListener {
            (activity as MainActivity).setActionBarTitle(getString(R.string.title_groceries))
            (activity as MainActivity).changeScreen(GroceryFragment(), R.id.main_frame, true)
        }

        val button = view.findViewById<Button>(R.id.generate_recipe_button)
        button.setOnClickListener {
            (activity as MainActivity).changeScreen(RecipeFragment(), R.id.main_frame, true)
        }

        return view
    }

}

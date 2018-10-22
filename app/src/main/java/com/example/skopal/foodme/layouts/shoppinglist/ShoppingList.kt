package com.example.skopal.foodme.layouts.shoppinglist


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skopal.foodme.MainActivity
import com.example.skopal.foodme.R

class ShoppingList : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        (activity as MainActivity).setActionBarTitle(getString(R.string.title_shopping_list))
        val view = inflater.inflate(R.layout.fragment_shopping_list, container, false)

        (activity as MainActivity).addScreen(ShoppingItemFragment(), R.id.shopping_list_frame)

        return view
    }

}

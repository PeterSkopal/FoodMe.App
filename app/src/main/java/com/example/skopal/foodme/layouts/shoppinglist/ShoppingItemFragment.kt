package com.example.skopal.foodme.layouts.shoppinglist

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skopal.foodme.MainActivity
import com.example.skopal.foodme.R
import com.example.skopal.foodme.classes.GroceryItem
import com.example.skopal.foodme.services.FoodMeApiGrocery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ShoppingItemFragment.OnListFragmentInteractionListener] interface.
 */
class ShoppingItemFragment : Fragment() {

    private var columnCount = 1
    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shoppingitem_list, container, false)
        (activity as MainActivity).setActionBarTitle(getString(R.string.title_shopping_list))

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                val baseContext = (activity as MainActivity).baseContext

                (activity as MainActivity).showSpinner(getString(R.string.loading_shopping_list))
                FoodMeApiGrocery(baseContext).getGroceries{ res ->
                    GlobalScope.launch(Dispatchers.Main) {

                        (activity as MainActivity).hideSpinner()
                        adapter = MyShoppingItemRecyclerViewAdapter(res, listener)
                    }
                }
                val itemDecor = DividerItemDecoration(context, resources.configuration.orientation)
                view.addItemDecoration(itemDecor)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: GroceryItem?)
    }

}

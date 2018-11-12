package com.example.skopal.foodme.layouts.mykitchen

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.preference.PreferenceManager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skopal.foodme.MainActivity
import com.example.skopal.foodme.R
import com.example.skopal.foodme.classes.RecipeItem
import com.example.skopal.foodme.services.FoodMeApiGrocery
import com.example.skopal.foodme.services.SpoonacularApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [RecipeFragment.OnListFragmentInteractionListener] interface.
 */
class RecipeFragment : Fragment() {

    private var columnCount = 1
    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                val baseContext = (activity as MainActivity).baseContext
                val prefs = PreferenceManager.getDefaultSharedPreferences(baseContext)

                var intolerance: String? = null
                val foodPreference = prefs.getString(getString(R.string.food_preference_key), null)
                val foodIntolerance = prefs.getStringSet(getString(R.string.food_intolerance_key), null)
                if (foodIntolerance !== null) {
                    intolerance = foodIntolerance.toString().replace(Regex("^.|.$"), "")
                }

                (activity as MainActivity).showSpinner()
                FoodMeApiGrocery(baseContext).getGroceries { groceries ->
                    SpoonacularApi(baseContext).getRecipeSearch(
                            groceries.joinToString { it.name },
                            foodPreference, intolerance)
                    { res ->
                        GlobalScope.launch(Dispatchers.Main) {

                            (activity as MainActivity).hideSpinner()
                            adapter = MyRecipeRecyclerViewAdapter(res, listener)
                        }
                    }
                }
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
        fun onListFragmentInteraction(item: RecipeItem?)
    }

}


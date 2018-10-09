package com.example.skopal.foodme

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

import com.example.skopal.foodme.layouts.footprint.Footprint
import com.example.skopal.foodme.layouts.mykitchen.GroceryFragment
import com.example.skopal.foodme.layouts.mykitchen.MyKitchen
import com.example.skopal.foodme.layouts.mykitchen.RecipeFragment
import com.example.skopal.foodme.layouts.mykitchen.dummy.DummyContent
import com.example.skopal.foodme.layouts.scanner.Scanner
import com.example.skopal.foodme.layouts.settings.Settings
import com.example.skopal.foodme.layouts.shoppinglist.ShoppingList
import com.example.skopal.foodme.utils.inTransaction

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), GroceryFragment.OnListFragmentInteractionListener, RecipeFragment.OnListFragmentInteractionListener {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_my_kitchen -> {
                replaceFragment(MyKitchen(), R.id.main_frame)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_shopping_list -> {
                replaceFragment(ShoppingList(), R.id.main_frame)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_scanner -> {
                replaceFragment(Scanner(), R.id.main_frame)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_footprint -> {
                replaceFragment(Footprint(), R.id.main_frame)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                replaceFragment(Settings(), R.id.main_frame)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.setSelectedItemId(R.id.navigation_my_kitchen)
    }

    /*
     * Callback function from My Groceries Fragment in 'My Kitchen'
     */
    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        TODO("not implemented click on items in grocery list")
        //To change body of created functions use File | Settings | File Templates.
    }


    /*
     * Private Utils
     */

    /**
     * Adding or Replacing a fragment helper functions
     * https://medium.com/thoughts-overflow/how-to-add-a-fragment-in-kotlin-way-73203c5a450b
     */
    private fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }

    private fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, addToStack: Boolean = false) {
        setBackArrowVisible(addToStack)
        supportFragmentManager.inTransaction {
            if (addToStack) addToBackStack(null)
            replace(frameId, fragment)
        }
    }

    private fun setBackArrowVisible(bool: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(bool)
    }

    /**
     * Publicly available functions
     */
    fun changeScreen(fragment: Fragment, frameId: Int, addToStack: Boolean = false) {
        replaceFragment(fragment, frameId, addToStack)
    }

    fun setActionBarTitle(title: String) {
        supportActionBar!!.title = title
    }


    /**
     * Pops the back stack when user presses back button in the Application Action Bar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        supportFragmentManager.popBackStack()
        if (supportFragmentManager.backStackEntryCount <= 1) setBackArrowVisible(false)
        return true
    }
}

package com.example.skopal.foodme

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.skopal.foodme.classes.GroceryItem
import com.example.skopal.foodme.classes.RecipeItem
import com.example.skopal.foodme.classes.ShoppingItem
import com.example.skopal.foodme.constants.SecureKey
import com.example.skopal.foodme.layouts.footprint.Footprint
import com.example.skopal.foodme.layouts.mykitchen.GroceryFragment
import com.example.skopal.foodme.layouts.mykitchen.MyKitchen
import com.example.skopal.foodme.layouts.mykitchen.RecipeFragment
import com.example.skopal.foodme.layouts.scanner.Scanner
import com.example.skopal.foodme.layouts.settings.Settings
import com.example.skopal.foodme.layouts.shoppinglist.ShoppingItemFragment
import com.example.skopal.foodme.layouts.shoppinglist.ShoppingList
import com.example.skopal.foodme.services.KeyService
import com.example.skopal.foodme.utils.inTransaction
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),
        GroceryFragment.OnListFragmentInteractionListener,
        RecipeFragment.OnListFragmentInteractionListener,
        ShoppingItemFragment.OnListFragmentInteractionListener {

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

    private lateinit var keyService: KeyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        keyService = KeyService(baseContext)
        login()

        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_my_kitchen
    }

    /*
     * Callback function from My Groceries Fragment in 'My Kitchen'
     */
    override fun onListFragmentInteraction(item: RecipeItem?) {
        //TODO("not implemented click on items in grocery list")
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onListFragmentInteraction(item: GroceryItem?) {
        //TODO("not implemented click on items in grocery list")
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

    private fun login() {
        val token = keyService.getKey(SecureKey.USER_TOKEN)
        val email = keyService.getKey(SecureKey.USER_MAIL)

        if (token === null || email === null) {
            changeActivity(LoginActivity::class.java)
        }
    }

    /**
     * Publicly available functions
     */
    fun changeScreen(fragment: Fragment, frameId: Int, addToStack: Boolean = false) {
        replaceFragment(fragment, frameId, addToStack)
    }

    fun addScreen(fragment: Fragment, frameId: Int) {
        addFragment(fragment, frameId)
    }

    fun <T> changeActivity(activity: Class<T>) {
        val intent = Intent(baseContext, activity)
        startActivity(intent)
    }

    fun setActionBarTitle(title: String) {
        supportActionBar!!.title = title
    }

    fun removeKeys() {
        keyService.removeKey(SecureKey.USER_TOKEN)
        keyService.removeKey(SecureKey.USER_MAIL)
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

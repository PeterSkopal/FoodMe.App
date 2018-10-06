package com.example.skopal.foodme

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.example.skopal.foodme.layouts.footprint.Footprint
import com.example.skopal.foodme.layouts.mykitchen.MyKitchen
import com.example.skopal.foodme.layouts.scanner.Scanner
import com.example.skopal.foodme.layouts.settings.Settings
import com.example.skopal.foodme.layouts.shoppinglist.ShoppingList
import com.example.skopal.foodme.utils.inTransaction

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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

        navigation.disableShiftMode()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.setSelectedItemId(R.id.navigation_my_kitchen);
    }

    /**
     * Disable shift mode on Android BottomNavigationView
     * https://stackoverflow.com/questions/40176244/how-to-disable-bottomnavigationview-shift-mode
     */
    @SuppressLint("RestrictedApi")
    fun BottomNavigationView.disableShiftMode() {
        val menuView = getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView::class.java.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShiftingMode(false)
                // set once again checked value, so view will be updated
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
            Log.e(ContentValues.TAG, "Unable to get shift mode field", e)
        } catch (e: IllegalStateException) {
            Log.e(ContentValues.TAG, "Unable to change value of shift mode", e)
        }
    }

    /**
     * Adding or Replacing a fragment helper functions
     * https://medium.com/thoughts-overflow/how-to-add-a-fragment-in-kotlin-way-73203c5a450b
     */
    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int){
        supportFragmentManager.inTransaction { add(frameId, fragment) }
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
        supportFragmentManager.inTransaction { replace(frameId, fragment) }
    }

    fun setActionBarTitle(title: String) {
        supportActionBar!!.title = title
    }
}

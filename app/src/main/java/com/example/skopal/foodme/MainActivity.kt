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

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.setSelectedItemId(R.id.navigation_my_kitchen);
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

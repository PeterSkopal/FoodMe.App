package com.example.skopal.foodme

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_my_kitchen -> {
                message.setText(R.string.title_kitchen)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_shopping_list -> {
                message.setText(R.string.title_shopping_list)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_scanner -> {
                message.setText(R.string.title_scanner)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_footprint -> {
                message.setText(R.string.title_footprint)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                message.setText(R.string.title_settings)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}

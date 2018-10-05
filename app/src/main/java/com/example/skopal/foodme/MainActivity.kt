package com.example.skopal.foodme

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
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

        navigation.disableShiftMode()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    /**
     * disable shift mode on Android BottomNavigationView
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
}

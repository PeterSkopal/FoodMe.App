package com.example.skopal.foodme

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.RelativeLayout
import com.example.skopal.foodme.classes.GroceryItem
import com.example.skopal.foodme.classes.LineAmount
import com.example.skopal.foodme.classes.RecipeItem
import com.example.skopal.foodme.constants.SecureKey
import com.example.skopal.foodme.layouts.components.EditTextDialog
import com.example.skopal.foodme.layouts.footprint.Footprint
import com.example.skopal.foodme.layouts.mykitchen.MyKitchen
import com.example.skopal.foodme.layouts.mykitchen.RecipeFragment
import com.example.skopal.foodme.layouts.mykitchen.RecipeInstruction
import com.example.skopal.foodme.layouts.scanner.ReceiptVerificationFragment
import com.example.skopal.foodme.layouts.scanner.Scanner
import com.example.skopal.foodme.layouts.settings.Settings
import com.example.skopal.foodme.layouts.shoppinglist.ShoppingItemFragment
import com.example.skopal.foodme.layouts.shoppinglist.ShoppingList
import com.example.skopal.foodme.services.FoodMeApiGrocery
import com.example.skopal.foodme.services.KeyService
import com.example.skopal.foodme.utils.inTransaction
import kotlinx.android.synthetic.main.activity_main.*
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import kotlinx.android.synthetic.main.loading_spinner.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(),
        MyKitchen.OnListFragmentInteractionListener,
        RecipeFragment.OnListFragmentInteractionListener,
        ShoppingItemFragment.OnListFragmentInteractionListener,
        ReceiptVerificationFragment.OnListFragmentInteractionListener {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        popStack() // popping all fragments on top of the bottom one.
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
                replaceFragment(Scanner.newInstance(), R.id.main_frame)
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

    private fun getPermissions() = runWithPermissions(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    ) {}

    private lateinit var keyService: KeyService
    private lateinit var spinner: RelativeLayout
    private var stackInitLevel: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getPermissions()

        setContentView(R.layout.activity_main)
        spinner = findViewById(R.id.loading_frame)
        setUpBackStackListener()

        keyService = KeyService(baseContext)
        val token = keyService.getKey(SecureKey.USER_TOKEN)
        val email = keyService.getKey(SecureKey.USER_MAIL)

        if (token === null || email === null) {
            changeActivity(LoginActivity::class.java)
        } else {

            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
            navigation.selectedItemId = R.id.navigation_my_kitchen
        }

    }

    /*
     * Callback function from My Groceries Fragment in 'My Kitchen'
     */
    override fun onListFragmentInteraction(item: RecipeItem?) {
        if (item !== null) {
            val fragment = RecipeInstruction.newInstance(item.id, item.image)
            replaceFragment(fragment, R.id.main_frame, true)
        }
    }

    override fun onListFragmentInteraction(item: GroceryItem?) {}

    /*
     * Callback function from ReceiptVerification Fragment in 'Scanner'
     */
    override fun onListFragmentInteraction(item: LineAmount?, values: List<LineAmount>?) {

        if (item !== null) { // click on any grocery
            val dialog = EditTextDialog.newInstance(
                    title = "Edit the Grocery Title",
                    text = item.description,
                    hint = "Grocery",
                    isMultiline = false)
            dialog.onOk = {
                item.description = dialog.editText.text.toString()
            }
            dialog.show(supportFragmentManager, "editDescription")

        } else if (values !== null) { // click on add groceries to my kitchen button
            showSpinner()

            val arr = mutableListOf<Map<String, String>>()

            for (i in values) {
                if (i.include) {
                    arr.add(mapOf("name" to i.description))
                }
            }

            FoodMeApiGrocery(baseContext).addGroceries(arr.toList()) { status ->
                GlobalScope.launch(Dispatchers.Main) {
                    hideSpinner()
                    var screen: Fragment = MyKitchen()
                    if (!status) {
                        screen = Scanner()
                    }
                    changeScreen(screen, R.id.main_frame)
                }
            }
        }
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

    fun showSpinner(text: String? = "") {
        spinner.loading_text.text = text
        spinner.visibility = VISIBLE
    }

    fun hideSpinner() {
        spinner.visibility = GONE
    }

    /**
     * Pops the back stack when user presses back button in the Application Action Bar
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (supportFragmentManager.backStackEntryCount > stackInitLevel) {
            supportFragmentManager.popBackStack()
        }
        return true
    }

    /**
     * Sets the initial level on the back stack, as well as setting a listener on the back stack
     * level, not displaying the back arrow in the top bar on the bottom level.
     */
    private fun setUpBackStackListener() {
        stackInitLevel = supportFragmentManager.backStackEntryCount

        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == stackInitLevel) {
                setBackArrowVisible(false)
            }
        }
    }

    /**
     * Pops the stack above init stack level when clicking on any bottom navigation bar option.
     */
    private fun popStack() {
        for (i in stackInitLevel until supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
    }

}

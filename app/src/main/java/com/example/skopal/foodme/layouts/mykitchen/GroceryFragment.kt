package com.example.skopal.foodme.layouts.mykitchen

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
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
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import android.support.v7.widget.helper.ItemTouchHelper
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [GroceryFragment.OnListFragmentInteractionListener] interface.
 */
class GroceryFragment : Fragment() {

    private var columnCount = 1
    private var listener: OnListFragmentInteractionListener? = null
    private var mRecyclerView: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_grocery_list, container, false)

        val baseContext = (activity as MainActivity).baseContext
        mRecyclerView = view as RecyclerView

        setUpItemTouchHelper(baseContext)
        setUpAnimationDecoratorHelper()

        // Set the adapter
        with(view) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }

            FoodMeApiGrocery(baseContext).getGroceries { res ->
                GlobalScope.launch(Dispatchers.Main) {
                    adapter = MyGroceryRecyclerViewAdapter(res, listener)
                }
            }
            val itemDecor = DividerItemDecoration(context, resources.configuration.orientation)
            view.addItemDecoration(itemDecor)
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
        fun onListFragmentInteraction(item: GroceryItem?)
    }

    /**
     * Inspiration from https://github.com/nemanja-kovacevic/recycler-view-swipe-to-delete
     *
     * This is the standard support library way of implementing "swipe to delete" feature. You can do custom drawing in onChildDraw method
     * but whatever you draw will disappear once the swipe is over, and while the items are animating to their new position the recycler view
     * background will be visible. That is rarely an desired effect.
     */
    private fun setUpItemTouchHelper(baseContext: Context) {

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            var background: Drawable? = null
            var xMark: Drawable? = null
            var xMarkMargin: Int = 0
            var initiated: Boolean = false

            private fun init() {
                background = ColorDrawable(Color.RED)
                xMark = ContextCompat.getDrawable(baseContext, R.drawable.ic_clear_24dp)
                xMark!!.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
                xMarkMargin = this@GroceryFragment.resources.getDimension(R.dimen.ic_clear_margin).toInt()
                initiated = true
            }

            // not important, we don't want drag & drop
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val position = viewHolder.adapterPosition
                val adapter = recyclerView.adapter as MyGroceryRecyclerViewAdapter?
                return if (adapter!!.isUndoOn() && adapter.isPendingRemoval(position)) {
                    0
                } else super.getSwipeDirs(recyclerView, viewHolder)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val swipedPosition = viewHolder.adapterPosition
                val adapter = mRecyclerView!!.adapter as MyGroceryRecyclerViewAdapter
                val undoOn = adapter.isUndoOn()
                if (undoOn) {
                    adapter.pendingRemoval(swipedPosition)
                } else {
                    adapter.remove(swipedPosition)
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val itemView = viewHolder.itemView

                if (viewHolder.adapterPosition == -1) {
                    return
                }

                if (!initiated) {
                    init()
                }

                // draw red background
                background!!.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                background!!.draw(c)

                // draw x mark
                val itemHeight = itemView.bottom - itemView.top
                val intrinsicWidth = xMark!!.intrinsicWidth
                val intrinsicHeight = xMark!!.intrinsicWidth

                val xMarkLeft = itemView.right - xMarkMargin - intrinsicWidth
                val xMarkRight = itemView.right - xMarkMargin
                val xMarkTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                val xMarkBottom = xMarkTop + intrinsicHeight
                xMark!!.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom)

                xMark!!.draw(c)

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        }
        val mItemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        mItemTouchHelper.attachToRecyclerView(mRecyclerView)
    }

    /**
     * We're gonna setup another ItemDecorator that will draw the red background in the empty space while the items are animating to thier new positions
     * after an item is removed.
     */
    private fun setUpAnimationDecoratorHelper() {
        mRecyclerView!!.addItemDecoration(object : RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            var background: Drawable? = null
            var initiated: Boolean = false

            private fun init() {
                background = ColorDrawable(Color.RED)
                initiated = true
            }

            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

                if (!initiated) {
                    init()
                }

                // only if animation is in progress
                if (parent.itemAnimator!!.isRunning) {

                    var lastViewComingDown: View? = null
                    var firstViewComingUp: View? = null

                    val left = 0
                    val right = parent.width

                    var top = 0
                    var bottom = 0

                    val childCount = parent.layoutManager!!.childCount
                    for (i in 0 until childCount) {
                        val child = parent.layoutManager!!.getChildAt(i)
                        if (child!!.translationY < 0) {

                            lastViewComingDown = child
                        } else if (child.translationY > 0) {

                            if (firstViewComingUp == null) {
                                firstViewComingUp = child
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.bottom + lastViewComingDown.translationY.toInt()
                        bottom = firstViewComingUp.top + firstViewComingUp.translationY.toInt()
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.bottom + lastViewComingDown.translationY.toInt()
                        bottom = lastViewComingDown.bottom
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.top
                        bottom = firstViewComingUp.top + firstViewComingUp.translationY.toInt()
                    }

                    background!!.setBounds(left, top, right, bottom)
                    background!!.draw(c)

                }
                super.onDraw(c, parent, state)
            }
        })
    }
}

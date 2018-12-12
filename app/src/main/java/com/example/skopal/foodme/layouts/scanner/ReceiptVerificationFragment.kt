package com.example.skopal.foodme.layouts.scanner

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skopal.foodme.MainActivity
import com.example.skopal.foodme.R
import com.example.skopal.foodme.classes.LineAmount
import com.example.skopal.foodme.services.ReceiptRecognitionApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

const val TAG = "ReceiptVerification"

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ReceiptVerificationFragment.OnListFragmentInteractionListener] interface.
 */
class ReceiptVerificationFragment : Fragment() {

    private var columnCount = 1
    private var listener: OnListFragmentInteractionListener? = null

    private lateinit var filePath: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_receiptverification_list, container, false)

        if (arguments !== null) {
            filePath = arguments!!.getString(ARG_PARAM)!!
        }

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                (activity as MainActivity).showSpinner(getString(R.string.loading_scanning_receipt))
                ReceiptRecognitionApi((activity as MainActivity).baseContext).parseReceipt(File(filePath)) { receipt ->
                    if (receipt !== null) {
                        GlobalScope.launch(Dispatchers.Main) {

                            (activity as MainActivity).hideSpinner()
                            adapter = MyReceiptVerificationRecyclerViewAdapter(receipt.lineAmounts, listener)
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
        fun onListFragmentInteraction(item: LineAmount?, values: List<LineAmount>?)
    }


    companion object {
        private const val ARG_PARAM = "file"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param file as File.
         * @return A new instance of fragment ReceiptVerificationFragment.
         */
        fun newInstance(file: File): ReceiptVerificationFragment {
            val fragment = ReceiptVerificationFragment()
            val args = Bundle()
            args.putString(ARG_PARAM, file.path)
            fragment.arguments = args
            return fragment
        }
    }

}

package com.app.addviu.view.searchDailog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.R
import kotlinx.android.synthetic.main.search_dialog_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

class SearchableDialog(
    private val activity: Activity,
    private val searchListItems: ArrayList<SearchListItem>,
    private val dTitle: String,
    private val editValue: String,private val searchBoxVisibility:Int
) {
    private var alertDialog: AlertDialog? = null
    private var searchListAdapter: SearchDialogAdapter? = null
    private var listView: RecyclerView? = null

    /***
     *
     * @param searchItemSelected
     * return selected position & item
     */
    fun setOnItemSelected(searchItemSelected: OnSearchItemSelected) {
        searchListAdapter = SearchDialogAdapter(searchListItems, searchItemSelected)
        listView?.adapter = searchListAdapter
    }

    /***
     *
     * show the seachable dialog
     */
    fun show() {
        val adb = AlertDialog.Builder(activity)
        @SuppressLint("InflateParams") val view =
            activity.layoutInflater.inflate(R.layout.search_dialog_layout, null)
        view.spinerTitle.text = dTitle
        listView = view.list
        view.list.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        if(searchBoxVisibility == 0){
            view.searchBox.visibility = GONE
        }
        view.searchBox.hint = editValue
        adb.setView(view)
        alertDialog = adb.create()
        alertDialog?.setCancelable(false)
        alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                val filteredValues = ArrayList<SearchListItem>()
                for (i in searchListItems.indices) {
                        val item = searchListItems[i]
                        if (item.title.toLowerCase(Locale.getDefault()).trim { it <= ' ' }
                                .contains(
                                    view.searchBox.text.toString().toLowerCase(Locale.getDefault())
                                        .trim { it <= ' ' }
                                )
                        ) {
                            filteredValues.add(item)
                        }
                }
                searchListAdapter?.updateList(filteredValues)
            }
        })
        view.close.setOnClickListener { alertDialog?.dismiss() }
        alertDialog?.show()
    }

    fun dismiss() {
        if (alertDialog?.isShowing!!) {
            alertDialog?.dismiss()
        }
    }

}
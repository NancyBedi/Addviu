package com.app.addviu.view.searchDailog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.R
import kotlinx.android.synthetic.main.activity_video_upload_screen.view.*
import kotlinx.android.synthetic.main.sender_single_item.view.*

class SearchDialogAdapter internal constructor(
    private var searchListItems: ArrayList<SearchListItem>,
    private val onSearchItemSelected: OnSearchItemSelected
) : RecyclerView.Adapter<SearchDialogAdapter.ViewHolder>() {

    inner class ViewHolder(row: View) :
        RecyclerView.ViewHolder(row), View.OnClickListener {

        var mTxtTitle = row.text1!!

        override fun onClick(view: View) {
            val pos = adapterPosition
            val searchListItem = searchListItems[pos]
            try {
                onSearchItemSelected.onClick(pos, searchListItem)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        init {
            row.setOnClickListener(this)
        }
    }

    override fun getItemCount(): Int {
        return searchListItems.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val output = searchListItems[position]
        holder.mTxtTitle.text = output.title
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        arg1: Int
    ): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView =
            inflater.inflate(R.layout.sender_single_item, parent, false)
        return ViewHolder(contactView)
    }

    fun updateList(list: ArrayList<SearchListItem>) {
        searchListItems = list
        notifyDataSetChanged()
    }

}
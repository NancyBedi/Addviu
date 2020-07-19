package com.app.addviu.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.addviu.R
import com.app.addviu.data.helper.SharedPrefsHelper
import com.app.addviu.model.homeModel.AccountData
import com.app.addviu.view.activity.HomeScreen
import com.app.addviu.view.activity.MyChannels
import com.app.addviu.view.activity.SignInScreen
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.account_list_adapter.view.*


class AccountListAdapter(private val imageLoader: ImageLoader,
                         private val mainList: ArrayList<AccountData>,
                         val context: Context,
                         val sharedPrefsHelper: SharedPrefsHelper
) :
    RecyclerView.Adapter<AccountListAdapter.ViewHolder>() {

    private var contactView: View? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        contactView = inflater.inflate(R.layout.account_list_adapter, parent, false)

        return ViewHolder(contactView!!)
    }

    override fun getItemCount(): Int {
        return mainList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mainList[position]

        holder.textTitle.text = data.title

        imageLoader.displayImage("drawable://".plus(data.image),holder.imageIcon)


    }

    inner class ViewHolder(row: View) : RecyclerView.ViewHolder(row),View.OnClickListener {

        val textTitle = row.textTitle
        val imageIcon = row.imageIcon

        init{
            row.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val data = mainList[adapterPosition]
            if(data.title == "My Channels"){
                context.startActivity(Intent(context,MyChannels::class.java))
            }else if(data.title == "Logout"){
                sharedPrefsHelper.deleteAllSharedPrefData()
                val intent = Intent(context, HomeScreen::class.java)
                context.startActivity(intent)
                (context as HomeScreen).finish()
            }
        }
    }
}
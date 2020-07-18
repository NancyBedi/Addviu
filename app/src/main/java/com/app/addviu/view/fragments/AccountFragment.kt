package com.app.addviu.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.USER_NAME
import com.app.addviu.model.homeModel.AccountData
import com.app.addviu.model.homeModel.HomeBean
import com.app.addviu.model.homeModel.HomeData
import com.app.addviu.view.adapter.AccountListAdapter
import com.app.addviu.view.adapter.HomeListAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.account_fragment_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.recyclerView

class AccountFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.account_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textName.text = sharedPrefsHelper?.get(USER_NAME,"")


        val arrayList = ArrayList<AccountData>()


        arrayList.add(AccountData(R.drawable.television,"My Channels"))
//        arrayList.add(AccountData(R.drawable.film,"My Videos"))
//        arrayList.add(AccountData(R.drawable.question,"Help"))
        arrayList.add(AccountData(R.drawable.logout,"Logout"))



        val accountAdapter = AccountListAdapter(imageLoader, arrayList, activity!!)
        recyclerView.adapter = accountAdapter

    }

}
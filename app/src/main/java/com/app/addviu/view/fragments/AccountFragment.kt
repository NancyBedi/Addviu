package com.app.addviu.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.addviu.R
import com.app.addviu.data.helper.USER_IMAGE
import com.app.addviu.data.helper.USER_NAME
import com.app.addviu.model.CommonSuccess
import com.app.addviu.model.homeModel.AccountData
import com.app.addviu.view.activity.HomeScreen
import com.app.addviu.view.activity.ProfilePage
import com.app.addviu.view.adapter.AccountListAdapter
import com.app.addviu.view.viewInterface.ResponseCallback
import kotlinx.android.synthetic.main.account_fragment_layout.*
import kotlinx.android.synthetic.main.account_fragment_layout.userImage
import kotlinx.android.synthetic.main.home_fragment_layout.recyclerView
import kotlinx.android.synthetic.main.home_screen_actionbar.*

class
AccountFragment : BaseFragment(), ResponseCallback {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (context as HomeScreen).searchIcon.visibility = View.GONE
        return inflater.inflate(R.layout.account_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textName.text = sharedPrefsHelper?.get(USER_NAME,"")
        if (sharedPrefsHelper?.get(USER_IMAGE, "")!!.isNotEmpty()) {
            imageLoader.displayImage(
                sharedPrefsHelper?.get(USER_IMAGE, ""),
                userImage,
                roundProfilePic()
            )
        }else{
            imageLoader.displayImage(
                "drawable://"+ R.drawable.circle_user,
                userImage,
                roundProfilePic()
            )
        }

        val arrayList = ArrayList<AccountData>()

        arrayList.add(AccountData(R.drawable.television,"My Channels"))
//        arrayList.add(AccountData(R.drawable.film,"My Videos"))
//        arrayList.add(AccountData(R.drawable.question,"Help"))
        arrayList.add(AccountData(R.drawable.logout,"Logout"))

        val accountAdapter = AccountListAdapter(imageLoader, arrayList, activity!!, sharedPrefsHelper!!, this)
        recyclerView.adapter = accountAdapter

        relativeLayout.setOnClickListener {
            val intent = Intent(activity, ProfilePage::class.java)
            startActivity(intent)
        }
    }

    override fun <T> success(t: T) {
        if (t is CommonSuccess){
            if (t.status == 1){
               logout()
            }else{
                logout()
            }
        }
    }

    override fun failure(t: Throwable) {
        logout()
    }

    fun logout(){
        sharedPrefsHelper?.deleteAllSharedPrefData()
        val intent = Intent(context, HomeScreen::class.java)
        context?.startActivity(intent)
        (context as HomeScreen).finish()
    }
}
package com.app.addviu.presenter

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.IS_LOGIN
import com.app.addviu.data.helper.IS_SIGN_CLICKED
import com.app.addviu.data.helper.SIGN_IN_CODE
import com.app.addviu.data.helper.SharedPrefsHelper
import com.app.addviu.view.activity.HomeScreen
import com.app.addviu.view.activity.RewardsScreen
import com.app.addviu.view.activity.SignInScreen
import com.app.addviu.view.fragments.*
import com.app.addviu.view.viewInterface.HomeInterface
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomePresenter(private val context: Context,private val fragmentManager: FragmentManager) : HomeInterface,
BottomNavigationView.OnNavigationItemSelectedListener{

    private var sharedPrefsHelper: SharedPrefsHelper? = null
    var previousFragment:BaseFragment? = null

    init {
        sharedPrefsHelper = AppController.instance?.sharedHelper
    }

    override fun loadFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val baseFragment: BaseFragment?

        when (item.itemId) {
            R.id.homeMenu -> {
                (context as HomeScreen).count = 0
                baseFragment = HomeFragment(context as HomeScreen)
                previousFragment = baseFragment
                loadFragment(baseFragment)
                return true
            }
            R.id.trendingMenu -> {
                (context as HomeScreen).count = 0
                baseFragment = TrendingFragment(context as HomeScreen)
//                baseFragment = TrendPagination(context as HomeScreen)
                previousFragment = baseFragment
                loadFragment(baseFragment)
                return true
            }
            R.id.signInMenu -> {
                (context as HomeScreen).count = 0
                if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
                    val currentFragment = fragmentManager.findFragmentById(R.id.frameLayout)
                    if (currentFragment is AccountFragment && currentFragment.isVisible) {
                        if (previousFragment is HomeFragment) {
                            (context as HomeScreen).setHomeSelected()
//                        } else if (previousFragment is TrendPagination) {
                        } else if (previousFragment is TrendingFragment) {
                            (context as HomeScreen).setTrendingSelected()
                        }
                        return false
                    } else {
                        baseFragment = AccountFragment()
//                        (context as HomeScreen).setHomeSelected()
//                        baseFragment = HomeFragment(context as HomeScreen)
                        loadFragment(baseFragment)
                        return true
                    }
                } else {
                    (context as HomeScreen).startActivityForResult(Intent(context, SignInScreen::class.java),
                        SIGN_IN_CODE)
                    return false
                }
            }
            R.id.rewardsMenu -> {
                (context as HomeScreen).count = 0
//                context.startActivity(Intent(context, JokVokHome::class.java))
                Util.comingSoonDialog(context, "Coming Soon")
                return true
            }
            R.id.highViewMenu -> {
                (context as HomeScreen).count = 0
                baseFragment = NotificationFragment()
                previousFragment = baseFragment
                loadFragment(baseFragment)
                return true
            }
        }
        return false
    }

}
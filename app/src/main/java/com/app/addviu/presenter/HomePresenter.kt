package com.app.addviu.presenter

import android.content.Context
import android.content.Intent
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.IS_LOGIN
import com.app.addviu.data.helper.IS_SIGN_CLICKED
import com.app.addviu.data.helper.SharedPrefsHelper
import com.app.addviu.view.activity.HomeScreen
import com.app.addviu.view.activity.RewardsScreen
import com.app.addviu.view.activity.SignInScreen
import com.app.addviu.view.fragments.AccountFragment
import com.app.addviu.view.fragments.BaseFragment
import com.app.addviu.view.fragments.HomeFragment
import com.app.addviu.view.fragments.TrendingFragment
import com.app.addviu.view.viewInterface.HomeInterface
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomePresenter(private val context: Context) : HomeInterface {

    private var sharedPrefsHelper: SharedPrefsHelper? = null
    var previousFragment:BaseFragment? = null

    init {
        sharedPrefsHelper = AppController.instance?.sharedHelper
    }

    override fun setBottomNavigationClicks(
        frameLayout: FrameLayout,
        bottomNavigationView: BottomNavigationView,fragmentManager: FragmentManager
    ) {

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            val baseFragment: BaseFragment?

            when (item.itemId) {
                R.id.homeMenu -> {
                    baseFragment = HomeFragment(context as HomeScreen)
                    previousFragment = baseFragment
                    loadFragment(baseFragment,fragmentManager)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.trendingMenu -> {
                    baseFragment = TrendingFragment()
                    previousFragment = baseFragment
                    loadFragment(baseFragment,fragmentManager)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.signInMenu -> {
                    if (sharedPrefsHelper?.get(IS_LOGIN, false)!!) {
                        val currentFragment = fragmentManager.findFragmentById(R.id.frameLayout)
                        if(currentFragment is AccountFragment && currentFragment.isVisible){
                            if(previousFragment is HomeFragment) {
                                loadFragment(previousFragment!!, fragmentManager)
                                bottomNavigationView.menu.getItem(0).isChecked = true
                            }else if(previousFragment is TrendingFragment){
                                loadFragment(previousFragment!!, fragmentManager)
                                bottomNavigationView.menu.getItem(1).isChecked = true
                            }
                        }else {
                            baseFragment = AccountFragment()
                            loadFragment(baseFragment, fragmentManager)
                        }
                    } else {
                        context.startActivity(Intent(context, SignInScreen::class.java))
                        sharedPrefsHelper?.put(IS_SIGN_CLICKED,true)
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.rewardsMenu -> {
                    Util.comingSoonDialog(context, "Coming Soon")
//                    context.startActivity(Intent(context,RewardsScreen::class.java))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.highViewMenu -> {
                    Util.comingSoonDialog(context, "Coming Soon")
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    override fun loadFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }

}
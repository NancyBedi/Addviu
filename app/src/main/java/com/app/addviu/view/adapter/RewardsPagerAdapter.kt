package com.app.addviu.view.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.addviu.view.fragments.BaseFragment
import com.app.addviu.view.fragments.MyProfileFragment
import com.app.addviu.view.fragments.SubscribeChannelFragment


class RewardsPagerAdapter(
    fm: FragmentManager?
) : FragmentPagerAdapter(fm!!) {

    override fun getItem(position: Int): Fragment {
        var fragment: BaseFragment? = null
        if (position == 0) {

            fragment = MyProfileFragment()


        } else if (position == 1) {

            fragment = SubscribeChannelFragment()

        }
        return fragment!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0) {
            title = "MY PROFILE"
        } else if (position == 1) {
            title = "SUBSCRIBED CHANNELS"
        }
        return title
    }


}
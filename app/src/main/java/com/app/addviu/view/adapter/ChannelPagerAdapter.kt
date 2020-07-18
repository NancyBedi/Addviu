package com.app.addviu.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.addviu.view.fragments.*

class ChannelPagerAdapter(
    fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {

    override fun getItem(position: Int): Fragment {
        var fragment: BaseFragment? = null
        if (position == 0) {

            fragment = AddChannelFragment()


        } else if (position == 1) {

            fragment = MyChannelFragment()

        }
        return fragment!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0) {
            title = "ADD CHANNEL"
        } else if (position == 1) {
            title = "MY CHANNELS"
        }
        return title
    }
}
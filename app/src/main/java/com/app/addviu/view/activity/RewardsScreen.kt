package com.app.addviu.view.activity


import android.os.Bundle
import android.view.View
import com.app.addviu.R
import com.app.addviu.presenter.RewardsPresenter
import com.app.addviu.view.BaseActivity
import kotlinx.android.synthetic.main.activity_rewards_screen.*

class RewardsScreen : BaseActivity(), View.OnClickListener {

    private val rewardsPresenter = RewardsPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards_screen)

        initViews()
        setClickListeners()
    }

    private fun setClickListeners() {
        banCamera.setOnClickListener(this)
        userCamera.setOnClickListener(this)
        backImage.setOnClickListener(this)
    }

    private fun initViews() {
        rewardsPresenter.setUpViewPagerwithTabLayout(viewPager, supportFragmentManager, tabLayout)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.banCamera -> {

            }
            R.id.userCamera -> {

            }
            R.id.backImage -> {
                finish()
            }
        }
    }
}

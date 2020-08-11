package com.app.addviu.presenter

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.USER_NAME
import com.app.addviu.model.CommonSuccess
import com.app.addviu.model.userDetailModel.UserDetailBean
import com.app.addviu.model.userModel.SignUpBean
import com.app.addviu.model.videoModel.CategoryBean
import com.app.addviu.view.activity.MyChannels
import com.app.addviu.view.activity.ProfilePage
import com.app.addviu.view.adapter.ChannelPagerAdapter
import com.app.addviu.view.viewInterface.ProfileInterface
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.addviu.view.viewInterface.RewardsInterface
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.activity_my_profile.userImage
import kotlinx.android.synthetic.main.my_profile_layout.*
import okhttp3.MultipartBody

class ProfilePresenter (val context: Context): ResponseCallback, ProfileInterface {

    override fun <T> success(t: T) {
        if (t is UserDetailBean){
            if (t.status == 1){
                if (t.data.avatar.isNullOrEmpty()){
                    (context as ProfilePage).imageLoader.displayImage(
                        "drawable://"+ R.drawable.circle_user,
                        context.userImage,
                        context.roundProfilePic()
                    )
                }else{
                    (context as ProfilePage).imageLoader.displayImage(t.data.avatar, context.userImage, context.roundProfilePic())
                }
                if(t.data.banner.isNullOrEmpty()){
                    (context as ProfilePage).imageLoader.displayImage(
                        "drawable://"+ R.drawable.dummy_bnr,
                        context.bannerImage,
                        context.profilePic()
                    )
                }else{
                    (context as ProfilePage).imageLoader.displayImage(t.data.banner, context.bannerImage)
                }
            }else{
                Util.showToast("Service Error Occurred please try again later", context)
            }
        }else if (t is SignUpBean){
            if (t.status == 1){
                getUserDetails()
            }else{
                Util.showToast(t.message, context)
            }
        }else if (t is CommonSuccess){
            if (t.status == 1){
                Util.showToast("Removed", context)
                (context as ProfilePage).imageLoader.displayImage(
                    "drawable://"+ R.drawable.circle_user,
                    context.userImage,
                    context.roundProfilePic()
                )
                getUserDetails()
            }else{
                Util.showToast(t.message, context)
            }
        }
    }

    override fun failure(t: Throwable) {

    }

    override fun getUserDetails() {
        AppController.instance?.dataManager?.userDetails(this, null)
    }

    override fun updateImage(builder: MultipartBody.Builder) {
        AppController.instance?.dataManager?.updateImage(builder, this, context)
    }

    override fun uploadBanner(builder: MultipartBody.Builder) {
        AppController.instance?.dataManager?.updateBanner(builder, this, context)
    }

    override fun removeBanner(builder: HashMap<String, String>) {
        AppController.instance?.dataManager?.removeBanner(builder, this, context)
    }

    override fun removeImage(builder: HashMap<String, String>) {
        AppController.instance?.dataManager?.removeImage(builder, this, context)
    }
}
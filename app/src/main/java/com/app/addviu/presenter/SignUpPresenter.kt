package com.app.addviu.presenter

import android.app.Activity.RESULT_OK
import android.content.Context
import com.app.addviu.AppController
import com.app.addviu.data.helper.*
import com.app.addviu.model.CommonSuccess
import com.app.addviu.model.userModel.SignInBean
import com.app.addviu.model.userModel.SignUpBean
import com.app.addviu.view.activity.ForgotScreen
import com.app.addviu.view.activity.SignInScreen
import com.app.addviu.view.activity.SignUpScreen
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.addviu.view.viewInterface.SignUpInterface
import com.app.naxtre.mvvmfinal.data.helper.Util

class SignUpPresenter(val context: Context) :
    ResponseCallback, SignUpInterface {

    private var sharedPrefsHelper: SharedPrefsHelper? = null

    init {
        sharedPrefsHelper = AppController.instance?.sharedHelper
    }

    override fun <T> success(t: T) {
        if (t is SignUpBean) {
            Util.showToast(t.message, context)
            if (t.status == 1) {
                if(context is SignUpScreen) {
                    context.finish()
                }else if(context is ForgotScreen){
                    context.finish()
                }
            }
        }
//        else if (t is SignInBean) {
//            if (t.status == 1) {
//                sharedPrefsHelper?.put(IS_LOGIN, true)
//                sharedPrefsHelper?.put(LOGIN_TOKEN, t.data.token)
//                sharedPrefsHelper?.put(USER_ID, t.data.id)
//                sharedPrefsHelper?.put(USER_NAME, t.data.name)
//                sharedPrefsHelper?.put(USER_IMAGE,t.data.avatar)
//                sharedPrefsHelper?.put(IS_SIGN_CLICKED,true)
//                (context as SignInScreen).setResult(RESULT_OK)
//                context.finish()
//            } else {
//                Util.showToast(t.message, context)
//            }
//        }
        else if (t is SignInBean){
            if (t.status == 1){
                (context as SignInScreen).signOut()
                sharedPrefsHelper?.put(IS_LOGIN, true)
                sharedPrefsHelper?.put(LOGIN_TOKEN, t.data.token)
                sharedPrefsHelper?.put(USER_ID, t.data.id)
                sharedPrefsHelper?.put(USER_NAME, t.data.name)
                sharedPrefsHelper?.put(USER_IMAGE,t.data.avatar)
                sharedPrefsHelper?.put(IS_SIGN_CLICKED,true)
                (context as SignInScreen).setResult(RESULT_OK)
                context.finish()
                Util.showToast(t.message, context)
            }else{
                Util.showToast("Please Register you email with Addviu.", context)
                (context as SignInScreen).updateUi(context.account)
            }
        }
    }

    override fun failure(t: Throwable) {
        Util.showToast(t.message.toString(), context)
    }

    override fun signUpUser(map: HashMap<String, String>) {
        AppController.instance?.dataManager?.signUpUser(map, this, context)
    }

    override fun signInUser(map: HashMap<String, String>) {
        AppController.instance?.dataManager?.signInUser(map, this, context)
    }

    override fun googleLogin(map: HashMap<String, String>) {
        AppController.instance?.dataManager?.googleLogin(map, this, context)
    }

    override fun forgotPassword(email: String) {
        AppController.instance?.dataManager?.forgotPassword(email, this, context)
    }
}
package com.app.addviu.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.app.addviu.R
import com.app.addviu.presenter.SignUpPresenter
import com.app.addviu.view.BaseActivity
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.activity_sign_in_screen.*

class SignInScreen : BaseActivity(), View.OnClickListener {

    private val signInPresenter = SignUpPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_screen)

        setClickListeners()
    }

    private fun setClickListeners() {
        signUpText.setOnClickListener(this)
        backImage.setOnClickListener(this)
        btnSignIn.setOnClickListener(this)
        forgotPassText.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.signUpText -> {
                startActivity(Intent(this, SignUpScreen::class.java))
            }
            R.id.backImage -> {
                finish()
            }
            R.id.btnSignIn -> {
                if (checkValidation()) {
                    val map = HashMap<String, String>()
                    map["email_or_contact"] = emailEditText.text.toString()
                    map["password"] = passEditText.text.toString()
                    signInPresenter.signInUser(map)
                }
            }
            R.id.forgotPassText ->{
                startActivity(Intent(this, ForgotScreen::class.java))
            }
        }
    }

    private fun checkValidation(): Boolean {


        if (Util.checkEmpty(emailEditText, "Email", this)) {
            return false
        }


        if (Util.checkEmpty(passEditText, "Password", this)) {
            return false
        }

        return true
    }
}

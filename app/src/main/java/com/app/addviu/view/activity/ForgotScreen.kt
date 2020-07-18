package com.app.addviu.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.addviu.R
import com.app.addviu.presenter.SignUpPresenter
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.activity_forgot_screen.*

class ForgotScreen : AppCompatActivity(), View.OnClickListener {

    private val forgotPresenter = SignUpPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_screen)


        setClickListeners()
    }

    private fun setClickListeners() {
        backImage.setOnClickListener(this)
        btnSend.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.backImage -> {
                finish()
            }
            R.id.btnSend -> {
                if (Util.checkEmpty(emailEditText, "Email", this)) {
                    return
                } else {
                    if (Util.isEmailValid(emailEditText.text.toString())) {
                        forgotPresenter.forgotPassword(emailEditText.text.toString())
                    } else {
                        Util.showToast("Please enter a valid email.", this)
                    }
                }
            }
        }
    }
}

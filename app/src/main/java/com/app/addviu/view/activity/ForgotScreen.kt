package com.app.addviu.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.addviu.R
import com.app.addviu.presenter.SignUpPresenter
import com.app.addviu.view.BaseActivity
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.activity_forgot_screen.*
import kotlinx.android.synthetic.main.activity_forgot_screen.backImage
import kotlinx.android.synthetic.main.activity_forgot_screen.emailEditText
import kotlinx.android.synthetic.main.activity_sign_up_screen.*

class ForgotScreen : BaseActivity(), View.OnClickListener {

    private val forgotPresenter = SignUpPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_screen)
        Util.fullScreen(this)
        val layoutParams = backImage.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.topMargin = statusBarHeight
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

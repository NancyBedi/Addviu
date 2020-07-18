package com.app.addviu.view.activity

import android.os.Bundle
import android.view.View
import com.app.addviu.R
import com.app.addviu.presenter.SignUpPresenter
import com.app.addviu.view.BaseActivity
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.activity_sign_up_screen.*

class SignUpScreen : BaseActivity(), View.OnClickListener {

    private val signUpPresenter = SignUpPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_screen)

        setClickListeners()
    }


    private fun setClickListeners() {
        btnSignUp.setOnClickListener(this)
        backImage.setOnClickListener(this)
        dobEditText.setOnClickListener(this)
        ageEditText.setOnClickListener(this)
        alreadyAccText.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSignUp -> {
                if (checkValidation()) {
                    val map = HashMap<String, String>()
                    map["name"] = nameEditText.text.toString()
                    map["email"] = emailEditText.text.toString()
                    map["password"] = passEditText.text.toString()
                    map["contact"] = contactEditText.text.toString()
                    map["date_of_birth"] = dobEditText.text.toString()
                    signUpPresenter.signUpUser(map)
                }
            }
            R.id.backImage, R.id.alreadyAccText -> {
                finish()
            }
            R.id.dobEditText -> {
                Util.showDatePickerDialog(this, dobEditText, ageEditText)
            }
            R.id.ageEditText -> {
                Util.showToast("Please select date of birth first.", this)
            }
        }
    }

    private fun checkValidation(): Boolean {

        if (Util.checkEmpty(nameEditText, "Name", this)) {
            return false
        }

        if (Util.checkEmpty(emailEditText, "Email", this)) {
            return false
        }

        if (!Util.isEmailValid(emailEditText.text.toString())) {
            Util.showToast("Please enter a valid email.", this)
            return false
        }

        if (Util.checkEmpty(contactEditText, "Contact", this)) {
            return false
        }

        if (Util.checkEmpty(passEditText, "Password", this)) {
            return false
        }

        if (Util.checkEmpty(confirmPassEditText, "Confirm Password", this)) {
            return false
        }

        if (passEditText.text.toString() != confirmPassEditText.text.toString()) {
            Util.showToast("Passwords does not match.", this)
            return false
        }

        if (Util.checkEmpty(dobEditText, "Date of Birth", this)) {
            return false
        }

        return true
    }
}

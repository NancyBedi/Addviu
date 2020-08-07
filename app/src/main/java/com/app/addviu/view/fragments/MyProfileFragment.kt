package com.app.addviu.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.data.helper.USER_ID
import com.app.addviu.data.helper.USER_NAME
import com.app.addviu.model.CommonSuccess
import com.app.addviu.model.userDetailModel.UserData
import com.app.addviu.model.userDetailModel.UserDetailBean
import com.app.addviu.view.activity.ProfilePage
import com.app.addviu.view.viewInterface.ResponseCallback
import com.app.naxtre.mvvmfinal.data.helper.Util
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.my_profile_layout.*
import kotlinx.android.synthetic.main.my_profile_layout.confirmPassEditText
import kotlinx.android.synthetic.main.my_profile_layout.contactEditText
import kotlinx.android.synthetic.main.my_profile_layout.dobEditText
import kotlinx.android.synthetic.main.my_profile_layout.emailEditText
import kotlinx.android.synthetic.main.my_profile_layout.nameEditText
import kotlinx.android.synthetic.main.my_profile_layout.passEditText

class MyProfileFragment:BaseFragment(), ResponseCallback, View.OnClickListener {

    var savedData = UserData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getDetails()
        return inflater.inflate(R.layout.my_profile_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dobEditText.setOnClickListener(this)
        editProfile.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
        isEnableView(false)

    }

    override fun <T> success(t: T) {
        if (t is UserDetailBean){
            if (t.status == 1){
                savedData = t.data
                (context as ProfilePage).userName.text = t.data.name
                nameEditText.setText(t.data.name)
                contactEditText.setText(t.data.contact)
                emailEditText.setText(t.data.email)
                dobEditText.setText(t.data.dateOfBirth)
                sharedPrefsHelper?.put(USER_NAME,t.data.name)
            }else{
                Util.showToast("Service Error Occurred please try again later", activity!!)
            }
        }else if (t is CommonSuccess){
            if (t.status == 1){
                getDetails()
                isEnableView(false)
            }else{
                Util.showToast("Service Error Occurred please try again later", activity!!)
            }
        }
    }

    override fun failure(t: Throwable) {
    }

    fun getDetails(){
        AppController.instance?.dataManager?.userDetails(this, activity)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.dobEditText -> {
                Util.selectDate(dobEditText, activity!!)
            }
            R.id.editProfile ->{
                isEnableView(true)
            }
            R.id.btnSubmit ->{
                if (checkValidation()){
                    if (checkIfChangesMade(savedData)) {
                        updateUserDetails()
                    }
                }
            }
        }
    }

    fun isEnableView(isEnable:Boolean){
        if (isEnable){
            editProfile.visibility = GONE
            btnSubmit.visibility = VISIBLE
        }else{
            editProfile.visibility = VISIBLE
            btnSubmit.visibility = GONE
        }
        nameEditText.isEnabled = isEnable
        contactEditText.isEnabled = isEnable
        emailEditText.isEnabled = isEnable
        passEditText.isEnabled = isEnable
        confirmPassEditText.isEnabled = isEnable
        dobEditText.isEnabled = isEnable
    }

    fun updateUserDetails(){
        val map = HashMap<String, String>()
        map["user_id"] = sharedPrefsHelper?.get(USER_ID, 0).toString()
        map["name"] = nameEditText.text.toString()
        map["contact"] = contactEditText.text.toString()
        map["date_of_birth"] =dobEditText.text.toString()
        map["email"] = emailEditText.text.toString()
        map["password"] = passEditText.text.toString()
        map["password_confirmation"] = confirmPassEditText.text.toString()
        AppController.instance?.dataManager?.userUpdate(map, this, activity)
    }
    private fun checkValidation(): Boolean {

        if (Util.checkEmpty(nameEditText, "Name", activity!!)) {
            return false
        }

        if (Util.checkEmpty(emailEditText, "Email", activity!!)) {
            return false
        }

        if (!Util.isEmailValid(emailEditText.text.toString())) {
            Util.showToast("Please enter a valid email.", activity!!)
            return false
        }

        if (Util.checkEmpty(contactEditText, "Contact", activity!!)) {
            return false
        }

        if (passEditText.text.toString() != confirmPassEditText.text.toString()) {
            Util.showToast("Passwords does not match.", activity!!)
            return false
        }

        if (Util.checkEmpty(dobEditText, "Date of Birth", activity!!)) {
            return false
        }

        return true
    }

    private fun checkIfChangesMade(savedData: UserData): Boolean {

        if (savedData.name.equals(nameEditText.text.toString().trim()) &&
            savedData.contact.equals(contactEditText.text.toString().trim()) &&
            savedData.dateOfBirth.equals(dobEditText.text.toString().trim()) &&
            savedData.email.equals(emailEditText.text.toString().trim()) &&
            passEditText.text.toString().isEmpty() &&
            confirmPassEditText.text.toString().isEmpty()
        ) {
            Util.showToast("No changes have been made.", activity!!)
            return false
        }

        return true
    }
}
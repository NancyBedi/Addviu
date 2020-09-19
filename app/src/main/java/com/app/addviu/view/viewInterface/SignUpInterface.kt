package com.app.addviu.view.viewInterface

interface SignUpInterface {

    fun signUpUser(map:HashMap<String,String>)

    fun signInUser(map:HashMap<String,String>)
    fun googleLogin(map:HashMap<String,String>)
    fun forgotPassword(email:String)

}
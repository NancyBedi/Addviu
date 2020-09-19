package com.app.addviu.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.addviu.R
import com.app.addviu.data.helper.RC_SIGN_IN
import com.app.addviu.presenter.SignUpPresenter
import com.app.addviu.view.BaseActivity
import com.app.naxtre.mvvmfinal.data.helper.Util
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_sign_in_screen.*

class SignInScreen : BaseActivity(), View.OnClickListener {

    private val signInPresenter = SignUpPresenter(this)
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var account: GoogleSignInAccount
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_screen)
        Util.fullScreen(this)
        val layoutParams = backImage.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.topMargin = statusBarHeight

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
//            updateUi(account)
        }
        setClickListeners()
    }

    private fun setClickListeners() {
        signUpText.setOnClickListener(this)
        signUp.setOnClickListener(this)
        backImage.setOnClickListener(this)
        btnSignIn.setOnClickListener(this)
        forgotPassText.setOnClickListener(this)
        fb.setOnClickListener(this)
        google.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.signUpText, R.id.signUp -> {
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
            R.id.forgotPassText -> {
                startActivity(Intent(this, ForgotScreen::class.java))
            }
            R.id.fb -> {
                Util.comingSoonDialog(this, "Coming Soon..")
            }
            R.id.google -> {
                signIn()
            }
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            account =
                completedTask.getResult(ApiException::class.java)!!
            // Signed in successfully, show authenticated UI.
            val map = HashMap<String, String>()
            map["gmail"] = account.email.toString()
            signInPresenter.googleLogin(map)
//            updateUi(account!!)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google", "signInResult:failed code=" + e.statusCode)
            Util.showToast("Google Signin error please try again", this)
//            updateUi(null)
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
    fun updateUi(account: GoogleSignInAccount) {
        val name =  account.displayName.toString()
        val email = account.email.toString()
//        val pic = account.photoUrl
//        val id = account.id
        signOut()
        val intent = Intent(this, SignUpScreen::class.java)
        intent.putExtra("name", name)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
//                Util.showToast("Get yourself Register with Addviu", this)
            }
    }
}

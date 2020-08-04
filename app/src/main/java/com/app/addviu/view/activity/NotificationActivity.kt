package com.app.addviu.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.addviu.AppController
import com.app.addviu.R
import com.app.addviu.view.BaseActivity
import com.app.addviu.view.viewInterface.ResponseCallback

class NotificationActivity : BaseActivity(), ResponseCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

    }

    override fun <T> success(t: T) {

    }

    override fun failure(t: Throwable) {
    }
}
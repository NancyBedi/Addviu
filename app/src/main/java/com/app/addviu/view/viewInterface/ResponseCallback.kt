package com.app.addviu.view.viewInterface


interface ResponseCallback {

    fun <T> success(t: T)

    fun failure(t: Throwable)

}

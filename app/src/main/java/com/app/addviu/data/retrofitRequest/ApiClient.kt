package com.app.addviu.data.retrofitRequest

import com.app.addviu.data.helper.BASE_URL
import com.app.addviu.data.helper.LOGIN_TOKEN
import com.app.addviu.data.helper.SharedPrefsHelper
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

class ApiClient(val sharedPrefsHelper: SharedPrefsHelper) {

    var retrofit: Retrofit? = null
    private var httpClient: OkHttpClient.Builder? = null

    init {

        setHttpClient()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient!!.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    private fun setHttpClient() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient = OkHttpClient.Builder()
        httpClient?.connectTimeout(20, TimeUnit.SECONDS)?.writeTimeout(20, TimeUnit.SECONDS)
            ?.readTimeout(30, TimeUnit.SECONDS)
        httpClient?.addInterceptor(object : Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                val request: Request =
                    chain.request().newBuilder().addHeader("Authorization","Bearer ".
                    plus( sharedPrefsHelper[LOGIN_TOKEN, ""]!!)
                    ).build()
                return chain.proceed(request)
            }
        })
        httpClient?.addInterceptor(logging)  // <-- this is the important line!
    }
}
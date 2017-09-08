package com.nrohmen.learnkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers



class MainActivity : AppCompatActivity() {

    private var image: ImageView? = null
    private var username: TextView? = null
    private var company: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.image) as ImageView
        username = findViewById(R.id.username) as TextView
        company = findViewById(R.id.company) as TextView

        //initialized gson
        val gson = GsonBuilder().create()

        //initialized retrofit
        val retrofit: Retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.github.com/")
                .build()

        val service: Service = retrofit.create(
                Service::class.java)

        //get data from github by username
        service.getUser("ennur")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { user ->
                            getData(user)
                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )


    }

    private fun getData(savedUser: Github?) {
        Glide.with(this).load(savedUser?.avatarUrl).into(image)
        username!!.text = savedUser?.name
        company!!.text = savedUser?.company
    }
}
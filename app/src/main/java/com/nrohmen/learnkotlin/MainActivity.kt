package com.nrohmen.learnkotlin

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.nrohmen.learnkotlin.databinding.ActivityMainBinding
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
                R.layout.activity_main)

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
                            getData(binding, user)
                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )


    }

    private fun getData(binding: ActivityMainBinding, savedUser: Github?) {
        Glide.with(this).load(savedUser?.avatarUrl).into(binding.image)
        binding.username.text = savedUser?.name
        binding.company.text = savedUser?.company
    }
}
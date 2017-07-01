package com.nrohmen.learnkotlin

import retrofit.http.GET
import retrofit.http.Path
import rx.Observable

/**
 * Created by root on 7/1/17.
 */
interface Service {
    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Observable<Github>
}
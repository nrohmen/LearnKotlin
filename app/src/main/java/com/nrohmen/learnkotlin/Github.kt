package com.nrohmen.learnkotlin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by root on 7/1/17.
 */
open class Github {

    @SerializedName("avatar_url")
    @Expose
    open var avatarUrl: String? = null

    @SerializedName("name")
    @Expose
    open var name: String? = null

    @SerializedName("company")
    @Expose
    open var company: String? = null

}

package com.e4ekta.network_module.src.model


import com.google.gson.annotations.SerializedName

data class CarousellNewsResponseItem(
    @SerializedName("banner_url")
    val bannerUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("time_created")
    val timeCreated: Int,
    @SerializedName("title")
    val title: String
)
package com.ummi.recipe_hu.model


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Response(
    @SerializedName("categories")
    var categories: List<Category>?
) : Parcelable
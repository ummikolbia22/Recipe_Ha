package com.ummi.recipe_hu.Service

import com.ummi.recipe_hu.model.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("categories.php")
    fun getRecipesData(
        @Query("categories") category: String?,
        @Query("strCategory") strCategory: String?,
        @Query("strCategoryDescription") strCategoryDescription: String?,
        @Query("idCategory") idCategory: String?


    ): Call<Response>

}
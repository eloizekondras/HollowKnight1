package com.example.hollowknight.network

import com.example.hollowknight.data.Character
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Base URL deve terminar com uma barra "/" se você usar apenas "charms" no @GET
const val BASE_URL = "http://10.0.2.2:3000/"

// Configurando o Moshi para converter JSON automaticamente
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Retrofit configurado com Moshi
private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface OpenHollowKnightAPIService{
    @GET("api/characters")
    suspend fun getCharms(): List<Character>
}

object OpenHollowKnightAPI {
    val retrofitService: OpenHollowKnightAPIService by lazy {
        retrofit.create(OpenHollowKnightAPIService::class.java)
    }
}

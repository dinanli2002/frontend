package com.example.myapplication.retrofit

import com.example.myapplication.estructuresDades.LoginUsuari
import com.example.myapplication.estructuresDades.Usuari
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface APIservice {
    @GET
    suspend fun getUsuaris(@Url url:String): Response<List<Usuari>>
    @Headers("Accept:application/json","Content-Type:application/json")
    @POST("{ruta}/api/usuarios/login")
    suspend fun postLogin(@Path("ruta") ruta:String, @Body loginUsuari: LoginUsuari):Response<Usuari>
}
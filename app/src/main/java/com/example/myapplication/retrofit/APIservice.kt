package com.example.myapplication.retrofit

import com.example.myapplication.estructuresDades.RegistroUsuari
import com.example.myapplication.estructuresDades.Usuari
import com.example.myapplication.estructuresDades.EditarUsuari
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Url

interface APIservice {
    @GET
    suspend fun getUsuaris(@Url url:String):Response<List<Usuari>>
    @Headers("Accept:application/json","Content-Type:application/json")
    @POST("{ruta}/create/usuario")
    suspend fun postRegistro(@Path("ruta") ruta:String, @Body registroUsuari: RegistroUsuari):Response<Usuari>
    @Multipart
    @POST("api/usuarios/login")
    suspend fun postLogin(@Part("username") username:RequestBody, @Part("password") password:RequestBody):Response<Usuari>
    @PUT("{ruta}/update/usuario/6")
    suspend fun putEditar(@Path("ruta") ruta: String, @Body editarUsuari: EditarUsuari):Response<Usuari>
}
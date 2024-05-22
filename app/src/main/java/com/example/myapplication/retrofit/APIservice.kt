package com.example.myapplication.retrofit

import com.example.myapplication.estructuresDades.RegistroUsuari
import com.example.myapplication.estructuresDades.Usuari
import com.example.myapplication.estructuresDades.EditarUsuari
import com.example.myapplication.estructuresDades.LoginUsuari
import com.example.myapplication.estructuresDades.CreateKid
import com.example.myapplication.estructuresDades.Task
import com.example.myapplication.estructuresDades.TaskResponse
import com.example.myapplication.estructuresDades.createTask
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Url
import okhttp3.OkHttpClient

interface APIservice {
    @GET
    suspend fun getUsuaris(@Url url:String):Response<List<Usuari>>
    @Headers("Accept:application/json","Content-Type:application/json")
    @POST("{ruta}/create/userTutor")
    suspend fun postRegistro(@Path("ruta") ruta:String, @Body registroUsuari: RegistroUsuari):Response<Usuari>
    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun postLogin(@Field("username") username: String, @Field("password") password: String, @Field("grant_type") grantType: String = "password"):Response<LoginUsuari>
    @PUT("{ruta}/update/usuario/6")
    suspend fun putEditar(@Path("ruta") ruta: String, @Body editarUsuari: EditarUsuari):Response<Usuari>
    @POST("{ruta}/create/userKid")
    suspend fun postCreateKid(@Path("ruta") ruta:String,@Body createKid:CreateKid):Response<Usuari>
    @POST("{ruta}/task/create/1")
    suspend fun postCreateTask(@Path("ruta") ruta:String,@Body createTask: createTask):Response<TaskResponse>
    @PUT("{ruta}/rewards/tutor")
    suspend fun put(@Path("ruta") ruta: String)
}
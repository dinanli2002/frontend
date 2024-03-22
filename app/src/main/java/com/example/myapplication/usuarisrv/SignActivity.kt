package com.example.myapplication.usuarisrv

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.estructuresDades.Rutes
import com.example.myapplication.estructuresDades.Usuari
import com.example.myapplication.retrofit.APIservice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val buttonClick = findViewById<Button>(R.id.button)
        buttonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun postLoginUsuari(view: View) {
        val inputLogin = findViewById<EditText>(R.id.editTextTextPassword)
        val inputPass = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val nomLogin = inputLogin.text.toString()
        val passLogin = inputPass.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val con = Retrofit.Builder().baseUrl(Rutes.baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            val username: RequestBody =
                nomLogin.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val password: RequestBody =
                passLogin.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            var resposta = con.create(APIservice::class.java).postLogin(username, password)
            if (resposta.isSuccessful) {
                println("la resposta!");
                var usuari = resposta.body() ?: Usuari("", "", -1, "")
                if (usuari.edat < 0) {
                    println("Login incorrecte")
                } else {
                    println("Login correcte")
                }
                println(resposta.body())
            } else {
                println(resposta.errorBody()?.string())
            }
        }
    }
}
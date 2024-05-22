package com.example.myapplication.usuarisrv

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.estructuresDades.EditarUsuari
import com.example.myapplication.estructuresDades.Rutes
import com.example.myapplication.estructuresDades.Usuari
import com.example.myapplication.retrofit.APIservice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditarActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar)
    }
    fun postEditarUsuario(view: View){
        val inputName = findViewById<EditText>(R.id.editTextTextUsername)
        val inputLogin = findViewById<EditText>(R.id.editTextTextPassword)
        val inputPass = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val username = inputName.text.toString()
        val email = inputLogin.text.toString()
        val password = inputPass.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val con = Retrofit.Builder().baseUrl(Rutes.basaUrl).addConverterFactory(
                GsonConverterFactory.create()).client(client).build()
            var resposta = con.create(APIservice::class.java).putEditar("api", EditarUsuari(username, email, password))
            if(resposta.isSuccessful){
                println("la resposta!");
                var usuari = resposta.body()?: Usuari("","",-1,"")
                if(usuari.edat<0){
                    println("Login incorrecte")
                }else{
                    println("Login correcte")
                }
                println(resposta.body())
            } else{
                println(resposta.errorBody()?.string())
            }
        }
    }
    }
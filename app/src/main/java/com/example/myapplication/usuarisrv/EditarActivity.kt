package com.example.myapplication.usuarisrv

import android.content.Context
import android.content.SharedPreferences
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
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditarActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar)
    }
    fun putEditarUsuario(view: View){
        val inputName = findViewById<EditText>(R.id.editTextTextUsername)
        val inputLogin = findViewById<EditText>(R.id.editTextTextPassword)
        val inputPass = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val userIdInput = findViewById<EditText>(R.id.editTextUserId)
        val username = inputName.text.toString()
        val email = inputLogin.text.toString()
        val password = inputPass.text.toString()
        val userId = userIdInput.text.toString().toInt()
        val token =getTokenFromStorage(this@EditarActivity)
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val authInterceptor = Interceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer $token")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(authInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(Rutes.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            val service = retrofit.create(APIservice::class.java)
            var resposta = service.putEditar("api", userId, EditarUsuari(username, email, password))
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
    private fun getTokenFromStorage(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("access_token", null)
    }
    }
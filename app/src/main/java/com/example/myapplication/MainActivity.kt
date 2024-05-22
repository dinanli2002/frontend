package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.estructuresDades.Rutes
import com.example.myapplication.estructuresDades.Usuari
import com.example.myapplication.retrofit.APIservice
import com.example.myapplication.usuarisrv.EditarActivity
import com.example.myapplication.usuarisrv.PaginaPrincipal
import com.example.myapplication.usuarisrv.RegistroActivity
import com.example.myapplication.usuarisrv.SignActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun goToRegistro(view: View) {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
        fun postLoginUsuari(view: View) {
            val inputLogin = findViewById<EditText>(R.id.editTextTextPassword)
            val inputPass = findViewById<EditText>(R.id.editTextTextEmailAddress)
            val nomLogin = inputLogin.text.toString()
            val passLogin = inputPass.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val interceptor = Interceptor { chain ->
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .header("Authorization", Credentials.basic("kotlinapp", "12345")) // Replace username and password with your credentials
                        .method(original.method, original.body)
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                val client = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
                val retrofit = Retrofit.Builder()
                    .baseUrl(Rutes.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                val service = retrofit.create(APIservice::class.java)
                val response = service.postLogin(nomLogin, passLogin)
                if (response.isSuccessful) {
                    val token = response.body()?.access_token
                    if (token != null) {
                        saveTokenToStorage(this@MainActivity, token)
                        println("token guardado:")
                        println(getTokenFromStorage(this@MainActivity))
                        startActivity(Intent(this@MainActivity, PaginaPrincipal::class.java))
                    }
                    println("La respuesta es exitosa")
                } else {
                    println("Error en la respuesta: ${response.errorBody()?.string()}")
                }
            }
        }
        private fun saveTokenToStorage(context: Context, token: String) {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("access_token", token)
            editor.apply()
        }
        // Función para obtener el token de SharedPreferences
        private fun getTokenFromStorage(context: Context): String? {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            return sharedPreferences.getString("access_token", null)
        }
    }
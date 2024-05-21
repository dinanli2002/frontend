package com.example.myapplication.usuarisrv

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.estructuresDades.Rutes
import com.example.myapplication.estructuresDades.createTask
import com.example.myapplication.retrofit.APIservice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TereaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tarea)
    }
    fun postCreateTask(view: View) {
        val inputNombre = findViewById<EditText>(R.id.editTextTitle)
        val inputDescripcion = findViewById<EditText>(R.id.editTextDescription)
        val inputmonedas = findViewById<EditText>(R.id.editTextMonedas)
        val nomLogin = inputNombre.text.toString()
        val passLogin = inputDescripcion.text.toString()
        val monedas = inputmonedas.text.toString().toInt()
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(Rutes.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            val service = retrofit.create(APIservice::class.java)
            val response = service.postCreateTask("api", createTask(nomLogin,passLogin,monedas))
            if (response.isSuccessful){
                println("Task created successfully")
            }
            else{
                println("Error creating task: ${response.errorBody()?.string()}")
            }
            }
        }
        }
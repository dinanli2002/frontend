package com.example.myapplication.usuarisrv

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TereaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tarea)
    }
    fun postCreateTerea(view: View) {
        val inputNombre = findViewById<EditText>(R.id.editTextTitle)
        val inputDescripcion = findViewById<EditText>(R.id.editTextDescription)
        val inputmonedas = findViewById<EditText>(R.id.editTextMonedas)
        val userIdInput = findViewById<EditText>(R.id.editTextUserId)
        val nombre = inputNombre.text.toString()
        val description = inputDescripcion.text.toString()
        val monedas = inputmonedas.text.toString().toInt()
        val userId = userIdInput.text.toString().toInt()
        val token =getTokenFromStorage(this@TereaActivity)
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
            val response = service.postCreateTask("api", userId, createTask(nombre,description,monedas))
            if (response.isSuccessful){
                val task = response.body()?.task
                if (task != null) {
                    val intent = Intent(this@TereaActivity, PaginaPrincipal::class.java)
                    intent.putExtra("nombre", task.nombre)
                    intent.putExtra("description", task.descripcion)
                    intent.putExtra("monedas", task.monedas)
                    startActivity(intent)
                }
                println("Task created successfully")
            }
            else{
                println("Error creating task: ${response.errorBody()?.string()}")
            }
            }
        }
    private fun getTokenFromStorage(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("access_token", null)
    }
        }
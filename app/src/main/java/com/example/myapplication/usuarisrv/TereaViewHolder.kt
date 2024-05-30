package com.example.myapplication.usuarisrv

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.estructuresDades.Rutes
import com.example.myapplication.estructuresDades.Task
import com.example.myapplication.retrofit.APIservice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TereaViewHolder(view: View,private val clickListener:TereaRvAdapter.ClickListener?)
    :RecyclerView.ViewHolder(view),View.OnClickListener {
    val tv_rv_nom = view.findViewById<TextView>(R.id.tvNombre)
    val tv_rv_descripcion = view.findViewById<TextView>(R.id.tvDescripcion)
    val tv_rv_monedas = view.findViewById<TextView>(R.id.tvMonedas)
    val but1= view.findViewById<Button>(R.id.btnMark)
    val but2= view.findViewById<Button>(R.id.btnDelete)
    var taskId: Int? = null



    init{
        if(clickListener!=null){
            println("Init")
            itemView.setOnClickListener(this)
        }
        itemView.setOnClickListener(this)
    }
    fun bindClickBtnMark(data:Task){
        but1.text=data.id.toString()
        but1.setOnClickListener {
            println("clicked btn mark")
            val id = but1.text
            print("El xxx es:" + id.toString())
            postVerificateTask(data)
        }
    }

    fun bind2ClickBtnDelete(data: String){
        but2.text=data
        but2.setOnClickListener {
            println("clicked btn delete")
            DeleteTask()
        }
    }

    fun postVerificateTask(data: Task) {
        val id = data.id
        print("El id es:" + id.toString())
        val context = but1.context
        val token = getTokenFromStorage(context)
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
            val response = service.postVericateTask("api", data.id)
            if (response.isSuccessful) {
                val task = response.body()?.task
                if (task != null) {
                    val intent = Intent(context, PaginaPrincipal::class.java)
                    intent.putExtra("nombre", task.nombre)
                    intent.putExtra("description", task.descripcion)
                    intent.putExtra("monedas", task.monedas)
                    context.startActivity(intent)
                }
                println("Task complete")
                Log.e("Resultado", "La llamada ha sido exitosa" )
            } else {
                println("Error creating task: ${response.errorBody()?.string()}")
                Log.e("Resultado", "La llamada ha sido exitosa" )
            }
        }
    }
    fun DeleteTask() {
        val context = but2.context
        val token = getTokenFromStorage(context)
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
            val response = service.DeleteTask("api")
        }
    }
    override fun onClick(v: View?) {
        println("onclick TareaRVAdapter")
        if(v!=null){
            clickListener?.onItemClick(v,adapterPosition)
        }

    }

    private fun getTokenFromStorage(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("access_token", null)
    }
    fun printTerea(task: Task){
        tv_rv_nom.text = task.nombre
        tv_rv_descripcion.text = task.descripcion
        tv_rv_monedas.text = task.monedas.toString()
    }




}
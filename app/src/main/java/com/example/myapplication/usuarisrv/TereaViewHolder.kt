package com.example.myapplication.usuarisrv

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
    private val but1: Button = view.findViewById(R.id.btnMark)
    init{
        if(clickListener!=null){
            println("Init")
            itemView.setOnClickListener(this)
        }
        itemView.setOnClickListener(this)
    }
    fun bindClickBtnMark(data:String){

        but1.text=data
        but1.setOnClickListener {
            println("clicked btn mark")

        }
    }

    override fun onClick(v: View?) {
        println("onclick TareaRVAdapter")
        if(v!=null){
            clickListener?.onItemClick(v,adapterPosition)
        }

    }
    fun printTerea(task: Task){
        tv_rv_nom.text = task.nombre
        tv_rv_descripcion.text = task.descripcion
        tv_rv_monedas.text = task.monedas.toString()
    }




}
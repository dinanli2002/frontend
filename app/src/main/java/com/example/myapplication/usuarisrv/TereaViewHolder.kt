package com.example.myapplication.usuarisrv

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.estructuresDades.Task

class TereaViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val tv_rv_nom = view.findViewById<TextView>(R.id.tvNombre)
    val tv_rv_descripcion = view.findViewById<TextView>(R.id.tvDescripcion)
    val tv_rv_monedas = view.findViewById<TextView>(R.id.tvMonedas)
    fun printTerea(task: Task){
        tv_rv_nom.text = task.nombre
        tv_rv_descripcion.text = task.descripcion
        tv_rv_monedas.text = task.monedas.toString()



    }
}
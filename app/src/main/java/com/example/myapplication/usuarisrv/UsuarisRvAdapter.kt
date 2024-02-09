package com.example.myapplication.usuarisrv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.estructuresDades.Usuari

class UsuarisRvAdapter(private val usuaris:List<Usuari>):RecyclerView.Adapter<UsuarisViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarisViewHolder {
        val layoutInFlate = LayoutInflater.from(parent.context)
        return UsuarisViewHolder(layoutInFlate.inflate(R.layout.usuari_rv_layout,parent, false))

    }

    override fun getItemCount(): Int {
        return usuaris.size

    }

    override fun onBindViewHolder(holder: UsuarisViewHolder, position: Int) {
        holder.printUsuari(usuaris[position])

    }
}
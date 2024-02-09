package com.example.myapplication.usuarisrv

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.R
import com.example.myapplication.estructuresDades.Usuari
import com.example.myapplication.estructuresDades.UsuariProvider
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

class UsuarisViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val tv_rv_nom = view.findViewById<TextView>(R.id.tv_rv_nom)
    val tv_rv_cognom = view.findViewById<TextView>(R.id.tv_rv_cognom)
    val tv_rv_edat = view.findViewById<TextView>(R.id.tv_rv_edat)
    val iv_rv_usuari = view.findViewById<ImageView>(R.id.iv_rv_usuari)
    fun printUsuari(usuari: Usuari){
        tv_rv_nom.text = usuari.nom
        tv_rv_cognom.text = usuari.cognom
        tv_rv_edat.text = usuari.edat.toString()
        println(usuari.imatge)
        Glide.with(iv_rv_usuari.context).load(UsuariProvider.rutaImatges+usuari.imatge)
            .diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop()
            .into(iv_rv_usuari)
        }
    }
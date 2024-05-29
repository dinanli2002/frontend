package com.example.myapplication.usuarisrv

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class PaginaPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.paginaprincipal)
    }
    fun postCreateTerea(view: View){
        startActivity(Intent(this@PaginaPrincipal, TereaActivity::class.java))
    }
    fun putModifyTask(view: View){
        startActivity(Intent(this@PaginaPrincipal, ModifyActivity::class.java))
    }
    fun postCreateKid(view: View){
        startActivity(Intent(this@PaginaPrincipal, CreateKid::class.java))
    }
}
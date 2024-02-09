package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.estructuresDades.Usuari
import com.example.myapplication.estructuresDades.UsuariProvider
import com.example.myapplication.retrofit.APIservice
import com.example.myapplication.usuarisrv.UsuarisRvAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsuarisActivity : AppCompatActivity() {
    var llistaUsuaris : MutableList<Usuari> = UsuariProvider.usuaris
    private lateinit var usuarisRvAdapter:UsuarisRvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuaris)
        var lista_extras= intent.extras
        var nom = lista_extras?.get("NOM_PARAMETRE")
        println("Nom parametre"+nom)
        var testView = findViewById<TextView>(R.id.nom_usuari)
        testView.text=getString(R.string.benvinguda)+nom
        val rv_usuaris = findViewById<RecyclerView>(R.id.rv_usuaris)
        rv_usuaris.layoutManager = LinearLayoutManager(this)
        usuarisRvAdapter = UsuarisRvAdapter(llistaUsuaris)
        rv_usuaris.adapter = usuarisRvAdapter
        lifecycleScope.launch (Dispatchers.Default){
            val connexio = Retrofit.Builder().baseUrl("http://10.0.2.2/").addConverterFactory(
                GsonConverterFactory.create()).build()
            withContext(Dispatchers.IO) {
                var resposta =
                    connexio.create(APIservice::class.java).getUsuaris("M13/usuarisGET.php")
                withContext(Dispatchers.Main){
                    if(resposta.isSuccessful){
                        val nousUsuaris = resposta.body()?: emptyList()
                        llistaUsuaris.clear()
                        llistaUsuaris.addAll(nousUsuaris)
                        usuarisRvAdapter.notifyDataSetChanged()
                    }
                }
            }
              }
        }
        }
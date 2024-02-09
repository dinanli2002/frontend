package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.myapplication.estructuresDades.LoginUsuari
import com.example.myapplication.estructuresDades.Rutes
import com.example.myapplication.estructuresDades.Usuari
import com.example.myapplication.retrofit.APIservice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Button
import com.example.myapplication.usuarisrv.RegistroActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonClick = findViewById<Button>(R.id.bt_login)
        buttonClick.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        var usuaris:Array<String> = Array(4){
            ""+it.toString();
        }
        for (valor in usuaris){
            println(valor)
        }
    }

/*fun openUsuarisActivity(view: View) {
    val input = findViewById<EditText>(R.id.et_usuari)
    val nom_usuari = input.text
    if (nom_usuari.isNotEmpty()) {
        val intent = Intent(this, UsuarisActivity::class.java)
        intent.putExtra("NOM_PARAMETRE", nom_usuari)
        startActivity(intent)
    } else {
        Toast.makeText(this, "Indica un nom d'usuari", Toast.LENGTH_SHORT).show()
    }
}*/

fun postLoginUsuari(view: View){
    val inputLogin = findViewById<EditText>(R.id.et_login_usuari)
    val inputPass = findViewById<EditText>(R.id.et_login_pass)
    val nomLogin = inputLogin.text.toString()
    val passLogin = inputPass.text.toString()
    CoroutineScope(Dispatchers.IO).launch {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val con = Retrofit.Builder().baseUrl(Rutes.baseUrl).addConverterFactory(GsonConverterFactory.create()).client(client).build()
        var resposta = con.create(APIservice::class.java).postLogin("",LoginUsuari(nomLogin, passLogin))
        if(resposta.isSuccessful){
            println("la resposta!");
            var usuari = resposta.body()?: Usuari("","",-1,"")
            if(usuari.edat<0){
                println("Login incorrecte")
            }else{
                println("Login correcte")
            }
            println(resposta.body())
        } else{
            println(resposta.errorBody()?.string())
        }
    }
}
    fun postLoginUsuario(view: View){
        val inputName = findViewById<EditText>(R.id.et_registrar_nombre)
        val inputLogin = findViewById<EditText>(R.id.et_registrar_usuari)
        val inputPass = findViewById<EditText>(R.id.et_registrar_password)
        val inputPass1 = findViewById<EditText>(R.id.et_registrar_password1)
        val nomLogin = inputName.text.toString()
        val userLogin = inputLogin.text.toString()
        val passLogin = inputPass.text.toString()
        val pass1Login = inputPass1.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val con = Retrofit.Builder().baseUrl(Rutes.baseUrl).addConverterFactory(GsonConverterFactory.create()).client(client).build()
            var resposta = con.create(APIservice::class.java).postLogin("login",LoginUsuari(nomLogin, passLogin))
            if(resposta.isSuccessful){
                println("la resposta!");
                var usuari = resposta.body()?: Usuari("","",-1,"")
                if(usuari.edat<0){
                    println("Login incorrecte")
                }else{
                    println("Login correcte")
                }
                println(resposta.body())
            } else{
                println(resposta.errorBody()?.string())
            }
        }

    }
}
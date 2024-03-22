package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.estructuresDades.Rutes
import com.example.myapplication.estructuresDades.Usuari
import com.example.myapplication.retrofit.APIservice
import com.example.myapplication.usuarisrv.EditarActivity
import com.example.myapplication.usuarisrv.RegistroActivity
import com.example.myapplication.usuarisrv.SignActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonClick = findViewById<Button>(R.id.bt_login)
        buttonClick.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
        val buttonC = findViewById<Button>(R.id.bt_editar)
        buttonC.setOnClickListener {
            val intent = Intent(this, EditarActivity::class.java)
            startActivity(intent)
        }
        val buttonA = findViewById<Button>(R.id.button)
        buttonA.setOnClickListener {
            val intent = Intent(this, SignActivity::class.java)
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

/*fun postLoginUsuari(view: View) {
    val inputLogin = findViewById<EditText>(R.id.editTextTextPassword)
    val inputPass = findViewById<EditText>(R.id.editTextTextEmailAddress)
    val nomLogin = inputLogin.text.toString()
    val passLogin = inputPass.text.toString()
    CoroutineScope(Dispatchers.IO).launch {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val con = Retrofit.Builder().baseUrl(Rutes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
        val username: RequestBody =
            nomLogin.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val password: RequestBody =
            passLogin.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        var resposta = con.create(APIservice::class.java).postLogin(username, password)
        if (resposta.isSuccessful) {
            println("la resposta!");
            var usuari = resposta.body() ?: Usuari("", "", -1, "")
            if (usuari.edat < 0) {
                println("Login incorrecte")
            } else {
                println("Login correcte")
            }
            println(resposta.body())
        } else {
            println(resposta.errorBody()?.string())
        }
    }
}*/
}
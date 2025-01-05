package com.sriwahyuni.news_berita.screen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sriwahyuni.news_berita.R
import com.sriwahyuni.news_berita.model.RegisterResponse
import com.sriwahyuni.news_berita.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_screen)

        val etUsername: EditText = findViewById(R.id.etUsername)
        val etFullname: EditText = findViewById(R.id.etFullName)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btnRegister: Button = findViewById(R.id.btnRegister)


        //set button register ketika di klik
        btnRegister.setOnClickListener(){
            //get data ke widget
            val username = etUsername.text.toString()
            val fullname = etFullname.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            //handle error
            try {
                ApiClient.retrofit.register(
                    username,password,fullname,email
                ).enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        //cek register berhasil atau tidak
                        if(response.isSuccessful){
                            Toast.makeText(this@RegisterScreen, response.body()?.message,
                                Toast.LENGTH_SHORT).show()
                            //kelas bisa arahkan ketika dia berhasil register pindah ke login
                            //tambahkan intent
                            //mau pindah ke page lain

                        }else{
                            val errorMessage = response.errorBody()?.string() ?: "Unknown Error"
                            Log.e("Register Error", errorMessage)
                            Toast.makeText(this@RegisterScreen, "Register Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }catch (e: Exception){
                Toast.makeText(this@RegisterScreen, "Error Occured: ${e.message}", Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "Error occured : ${e.message}", e)
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
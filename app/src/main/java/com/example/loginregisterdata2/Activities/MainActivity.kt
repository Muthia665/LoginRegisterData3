package com.example.loginregisterdata2.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginregisterdata2.Models.DefaultResponse
import com.example.loginregisterdata2.R
import com.example.loginregisterdata2.network.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btn_register.setOnClickListener {
            val email = et_email_register.text.toString().trim()
            val password = et_password_register.text.toString().trim()
            val name = et_name_register.text.toString().trim()
            val school = et_school_register.text.toString().trim()

            if (email.isEmpty()){
                et_email_register.error = "Email Required"
                et_email_register.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                et_password_register.error = "Password Required"
                et_password_register.requestFocus()
                return@setOnClickListener
            }

            if (name.isEmpty()){
                et_name_register.error = "Name Required"
                et_name_register.requestFocus()
                return@setOnClickListener
            }

            if (school.isEmpty()){
                et_school_register.error = "School Required"
                et_school_register.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instances.createuser(email, name, password, school).enqueue(object : Callback<DefaultResponse>{
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(applicationContext,response.body()?.message, Toast.LENGTH_SHORT).show()
                }

            })

        }
    }
}

package com.example.loginregisterdata2.Activities

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginregisterdata2.Models.LoginResponse
import com.example.loginregisterdata2.R
import com.example.loginregisterdata2.network.RetrofitClient
import com.example.loginregisterdata2.storage.SharePrefManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.et_email_register
import kotlinx.android.synthetic.main.activity_main.et_password_register
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var sharedPrefenceManager : SharePrefManager
    var progressDialog : ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressDialog = ProgressDialog(this)
        sharedPrefenceManager = SharePrefManager(this)

        if (sharedPrefenceManager.spSigned!!){
            startActivity(Intent(this, HomeActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
            finish()
        }

        btn_login.setOnClickListener {
            val email = et_email_register.text.toString().trim()
            val password = et_password_register.text.toString().trim()

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

            RetrofitClient.instances.userlogin(email, password).enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (! response.body()?.error!!){
                        sharedPrefenceManager.saveBoolean(SharePrefManager.SP_SIGNED, true)
                        sharedPrefenceManager.saveId(SharePrefManager.SP_ID, 1)

                        val intent = Intent(applicationContext, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()

                    } else{
                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })

        }

    }
}
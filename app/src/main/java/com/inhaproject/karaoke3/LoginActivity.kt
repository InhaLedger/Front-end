package com.inhaproject.karaoke3

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.TextInputEditText_email)
        val passwordEditText = findViewById<EditText>(R.id.TextInputEditText_password)


        initLoginButton()

        initSignUpButton()
    }

    private fun initLoginButton() {
        val loginButton = findViewById<AppCompatButton>(R.id.LoginButton)
        loginButton.setOnClickListener {
           val email = getInputEmail()
           val password = getInputPassword()

           val intent = Intent(this , MainActivity::class.java)
            intent.putExtra("email",email)
            intent.putExtra("password",password)
            startActivity(intent)
        }
    }

    private fun initSignUpButton() {
        val signUpButton = findViewById<AppCompatButton>(R.id.GosignUpButton)
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getInputEmail() : String {
        return findViewById<EditText>(R.id.TextInputEditText_email).text.toString()
    }

    private fun getInputPassword() : String {
        return findViewById<EditText>(R.id.TextInputEditText_password).text.toString()
    }

}
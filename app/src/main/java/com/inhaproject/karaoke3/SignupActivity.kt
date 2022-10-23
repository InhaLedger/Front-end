package com.inhaproject.karaoke3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.inhaproject.karaoke3.databinding.ActivitySignupBinding
import com.inhaproject.karaoke3.retrofit.RegisterModel
import com.inhaproject.karaoke3.retrofit.RegisterResult
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SignUpButton.setOnClickListener {
            binding.apply {
                createAccount(InputId.text.toString(),InputPw.text.toString())
            }

        }
    }

    private fun createAccount(email: String, password: String) {
        if(email.isNotEmpty() && password.isNotEmpty()) {
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "계정 생성 성공",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else {
                        Toast.makeText(this,"입력되지 않은 정보가 있습니다.",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
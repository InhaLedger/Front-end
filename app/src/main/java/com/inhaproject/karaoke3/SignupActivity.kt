package com.inhaproject.karaoke3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.inhaproject.karaoke3.databinding.ActivitySignupBinding
import com.inhaproject.karaoke3.retrofit.RetroInterface.Companion.create
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var callbackManager: CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        callbackManager = CallbackManager.Factory.create()

        binding.SignUpButton.setOnClickListener {
            binding.let {
                val email = binding.InputId.text.toString()
                val password = binding.InputPw.text.toString()
                val intent = Intent(this,LoginActivity::class.java)

                if(email.isNotEmpty() && password.isNotEmpty()) {
                    create().register(email,password).enqueue(object:
                        Callback<Response<Void>>{
                        override fun onResponse(
                            call: Call<Response<Void>>,
                            response: Response<Response<Void>>

                        ) {
                            val code = response.code()

                            if(response.isSuccessful) {
                                Toast.makeText(
                                    this@SignupActivity,
                                    "회원가입에 성공하였습니다.$code",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(intent)
                                finish()
                            }
                            else {
                                Toast.makeText(
                                    this@SignupActivity,
                                    "이미 존재하는 아이디 입니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<Response<Void>>, t: Throwable) {

                            Toast.makeText(this@SignupActivity,"회원가입에 성공하였습니다" ,Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                            finish()
                        }

                    }
                    )
                }


            }

        }
    }

    private fun createAccount(email: String, password: String) {



    }
}
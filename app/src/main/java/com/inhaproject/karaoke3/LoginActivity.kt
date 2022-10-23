package com.inhaproject.karaoke3

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.addTextChangedListener
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.inhaproject.karaoke3.databinding.ActivityLoginBinding
import com.inhaproject.karaoke3.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_login.view.*


class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var callbackManager: CallbackManager

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val emailEditText = findViewById<EditText>(R.id.TextInputEditText_email)
        //val passwordEditText = findViewById<EditText>(R.id.TextInputEditText_password)

        callbackManager = CallbackManager.Factory.create()

        initLoginButton()
        initSignUpButton()
        //initFacebookLoginButton()
    }

    private fun initLoginButton() {
        val loginButton = findViewById<AppCompatButton>(R.id.LoginButton)
        loginButton.setOnClickListener {
            binding?.let {
                val email = binding.TextInputEditTextEmail.text.toString()
                val password = binding.TextInputEditTextPassword.text.toString()

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(baseContext, "입력되지 않은 정보가 있습니다.",Toast.LENGTH_SHORT).show()
                }
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this) {
                        if(it.isSuccessful){
                            Toast.makeText(baseContext, "로그인 되었습니다.",Toast.LENGTH_SHORT).show()
                            moveHomePage(auth?.currentUser)
                        }
                        else {
                            Toast.makeText(baseContext,"이메일 또는 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }

    }

    private fun initSignUpButton() {
        val signUpButton = findViewById<AppCompatButton>(R.id.GosignUpButton)
        signUpButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

   /* private fun initFacebookLoginButton() {
        val facebookLoginButton = findViewById<LoginButton>(R.id.facebook_login_button)

        facebookLoginButton.setPermissions("email", "public_profile")
        facebookLoginButton.registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                //로그인 성공
                val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this@LoginActivity) {
                        if(it.isSuccessful){
                            finish()
                        }  else {
                            Toast.makeText(this@LoginActivity,"페이스북 로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

            override fun onCancel() {}

            override fun onError(error: FacebookException?) {
                Toast.makeText(this@LoginActivity,"페이스북 로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }

        })


    }*/

    private fun getInputEmail() : String {
        return findViewById<EditText>(R.id.TextInputEditText_email).text.toString()
    }

    private fun getInputPassword() : String {
        return findViewById<EditText>(R.id.TextInputEditText_password).text.toString()
    }
    private fun moveHomePage(user: FirebaseUser?){
        if(user != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}

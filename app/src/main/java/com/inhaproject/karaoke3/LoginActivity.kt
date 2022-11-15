package com.inhaproject.karaoke3

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.inhaproject.karaoke3.databinding.ActivityLoginBinding
import com.inhaproject.karaoke3.databinding.ActivityMainBinding
import com.inhaproject.karaoke3.preference.App
import com.inhaproject.karaoke3.retrofit.LoginResult
import com.inhaproject.karaoke3.retrofit.RetroInterface.Companion.create
import kotlinx.android.synthetic.main.activity_login.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        callbackManager = CallbackManager.Factory.create()

        initLoginButton()
        initSignUpButton()
        //initFacebookLoginButton()
    }

    private fun initLoginButton() {
        val loginButton = findViewById<AppCompatButton>(R.id.LoginButton)
        loginButton.setOnClickListener {
            binding.let {
                val email = binding.TextInputEditTextEmail.text.toString()
                val password = binding.TextInputEditTextPassword.text.toString()
                val dialog = AlertDialog.Builder(this@LoginActivity)

                val intent = Intent(this, MainActivity::class.java)

                  create().login(email,password).enqueue(object:
                    Callback<LoginResult>{
                    override fun onResponse(
                        call: Call<LoginResult>,
                        response: Response<LoginResult>
                    ) {
                        val login = response.body()


                        if(login?.token == null){
                            dialog.setTitle("로그인 오류")
                            dialog.setMessage("로그인에 실패했습니다.")
                            dialog.show()
                        }
                        else {
                            App.prefs.token = login.token
                            Toast.makeText(this@LoginActivity,"로그인에 성공하였습니다." + App.prefs.token,Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                            finish()
                        }
                    }

                    override fun onFailure(
                        call: Call<LoginResult>,
                        t: Throwable
                    ) {
                        dialog.setTitle("실패 ㅠㅠㅠ")
                        dialog.setMessage("통신에 실패했습니다.")
                        dialog.show()
                    }

                })

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

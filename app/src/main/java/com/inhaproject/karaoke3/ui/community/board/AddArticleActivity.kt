package com.inhaproject.karaoke3.ui.community.board

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.inhaproject.karaoke3.DBkey.Companion.DB_ARTICLES
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.ActivityAddArticleBinding

class AddArticleActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddArticleBinding

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }
    private val storage: FirebaseStorage by lazy {
        Firebase.storage
    }

    private val articleDB: DatabaseReference by lazy {
        Firebase.database.reference.child(DB_ARTICLES)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_article)

        findViewById<Button>(R.id.submitButton).setOnClickListener {
            val title = findViewById<EditText>(R.id.titleEditText).text.toString()
            val price = findViewById<EditText>(R.id.priceEditText).text.toString()
            val content = findViewById<EditText>(R.id.contentEditText).text.toString()
            val userId = auth.currentUser?.uid.orEmpty()

            val model = ArticleModel(userId, title, System.currentTimeMillis(), "$price 원", content)
            articleDB.push().setValue(model)

            finish()

        }
    }

}
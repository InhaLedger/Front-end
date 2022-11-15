package com.inhaproject.karaoke3.ui.community.noteboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.databinding.ActivityNoteboardBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.CustomItemDecoration
import com.inhaproject.karaoke3.ui.community.board.AddArticleActivity
import com.inhaproject.karaoke3.ui.community.board.ArticleAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoteBoardActivity : AppCompatActivity() {
    private lateinit var articleAdapter: NoteArticleAdapter

    private lateinit var binding: ActivityNoteboardBinding

    var data : NoteArticleData? = null
    private var articleList = ArrayList<NoteArticleData>()

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        articleList.clear()

        api.noteBoardList().enqueue(object : Callback<ArrayList<NoteArticleData>>{
            override fun onResponse(
                call: Call<ArrayList<NoteArticleData>>,
                response: Response<ArrayList<NoteArticleData>>
            ) {
                if(response.body().toString().isNotEmpty()){
                    response.body().let {
                        if (response.code() == 200) {
                            articleList = response.body()!!
                            setNoteArticleAdapter(articleList)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<NoteArticleData>>, t: Throwable) {
                Toast.makeText(this@NoteBoardActivity,"게시판 서버 연결 실패 " ,Toast.LENGTH_SHORT).show()
                Log.d("음역대 게시판 오류", t.message.toString());
            }

        })

        binding.noteAddFloatingButton.setOnClickListener{
            this.let {
                val intent = Intent(it, NoteSearchActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun setNoteArticleAdapter(noteArticleList : ArrayList<NoteArticleData>){
        articleAdapter = NoteArticleAdapter(noteArticleList,this)

        binding.noteArticleRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.noteArticleRecyclerView.adapter = articleAdapter
        binding.noteArticleRecyclerView.addItemDecoration(DistanceItemDecorator(15))
        binding.noteArticleRecyclerView.addItemDecoration(CustomItemDecoration())
    }
}
package com.inhaproject.karaoke3.ui.community.newboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.databinding.ActivityNewboardBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.CustomItemDecoration
import com.inhaproject.karaoke3.ui.community.noteboard.NoteArticleAdapter
import com.inhaproject.karaoke3.ui.community.noteboard.NoteArticleData
import com.inhaproject.karaoke3.ui.community.noteboard.NoteSearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewBoardActivity : AppCompatActivity() {
    private lateinit var articleAdapter: NewArticleAdapter

    private lateinit var binding: ActivityNewboardBinding

    var data : NewArticleData? = null
    private var articleList = ArrayList<NewArticleData>()

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        articleList.clear()

        api.newBoardList().enqueue(object : Callback<ArrayList<NewArticleData>> {
            override fun onResponse(
                call: Call<ArrayList<NewArticleData>>,
                response: Response<ArrayList<NewArticleData>>
            ) {
                if(response.body().toString().isNotEmpty()){
                    response.body().let {
                        if (response.code() == 200) {
                            articleList = response.body()!!
                            setNewArticleAdapter(articleList)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<NewArticleData>>, t: Throwable) {
                Toast.makeText(this@NewBoardActivity,"게시판 서버 연결 실패 " , Toast.LENGTH_SHORT).show()
                Log.d("신곡 게시판 오류", t.message.toString());
            }

        })

        binding.newAddFloatingButton.setOnClickListener{
            this.let {
                val intent = Intent(this, NewAddArticleActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun setNewArticleAdapter(newArticleList : ArrayList<NewArticleData>){
        articleAdapter = NewArticleAdapter(newArticleList,this)

        binding.newArticleRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.newArticleRecyclerView.adapter = articleAdapter
        binding.newArticleRecyclerView.addItemDecoration(DistanceItemDecorator(15))
        binding.newArticleRecyclerView.addItemDecoration(CustomItemDecoration())
    }
}
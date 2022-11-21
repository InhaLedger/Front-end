package com.inhaproject.karaoke3.ui.community.fixboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.databinding.ActivityFixboardBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.CustomItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FixBoardActivity : AppCompatActivity() {
    private lateinit var articleAdapter: FixArticleAdapter

    private lateinit var binding: ActivityFixboardBinding

    var data : FixArticleData? = null
    private var articleList = ArrayList<FixArticleData>()

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFixboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        articleList.clear()

        api.fixBoardList().enqueue(object : Callback<ArrayList<FixArticleData>> {
            override fun onResponse(
                call: Call<ArrayList<FixArticleData>>,
                response: Response<ArrayList<FixArticleData>>
            ) {
                if(response.code() == 200){
                    articleList = response.body()!!
                    setFixArticleAdapter(articleList)
                }
            }

            override fun onFailure(call: Call<ArrayList<FixArticleData>>, t: Throwable) {
                Toast.makeText(this@FixBoardActivity,"게시판 서버 연결 실패 " , Toast.LENGTH_SHORT).show()
                Log.d("수정 게시판 오류", t.message.toString());
            }

        })

        binding.fixAddFloatingButton.setOnClickListener {
            this.let {
                val intent = Intent(this, FixSearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setFixArticleAdapter(fixArticleList : ArrayList<FixArticleData>) {
        articleAdapter = FixArticleAdapter(fixArticleList, this)

        binding.fixArticleRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.fixArticleRecyclerView.adapter = articleAdapter
        binding.fixArticleRecyclerView.addItemDecoration(DistanceItemDecorator(15))
        binding.fixArticleRecyclerView.addItemDecoration(CustomItemDecoration())
    }
}
package com.inhaproject.karaoke3.ui.community.board

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import com.inhaproject.karaoke3.databinding.FragmentBoardBinding
import com.inhaproject.karaoke3.preference.App
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.CustomItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardActivity : AppCompatActivity() {

    private lateinit var articleAdapter: ArticleAdapter

    var data : PackArticleData? = null
    private var articleList = ArrayList<PackArticleData>()

    lateinit var binding: FragmentBoardBinding

    private val api = RetroInterface.create();

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = FragmentBoardBinding.inflate(layoutInflater)

        articleList.clear()

        api.packBoardList().enqueue(object : Callback<ArrayList<PackArticleData>>{
            override fun onResponse(
                call: Call<ArrayList<PackArticleData>>,
                response: Response<ArrayList<PackArticleData>>
            ) {
                if(response.body().toString().isNotEmpty()){
                    response.body().let {
                        if (response.code() == 200) {
                            articleList = response.body()!!
                            setArticleAdapter(articleList)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<ArrayList<PackArticleData>>, t: Throwable) {
                Toast.makeText(this@BoardActivity,"게시판 서버 연결 실패 " ,Toast.LENGTH_SHORT).show()
                Log.d("BoardActivity", t.message.toString());
            }

        })

        binding.addFloatingButton.setOnClickListener{
            this.let {
                //로그인 기능 구현 후
                //if(auth.currentUser != null) {
                val intent = Intent(it, AddArticleActivity::class.java)
                startActivity(intent)
                //}
            }

        }

        setContentView(binding.root)
    }

    private fun setArticleAdapter(packArticleList: ArrayList<PackArticleData>) {
        articleAdapter = ArticleAdapter(packArticleList,this)

        binding.articleRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.articleRecyclerView.adapter = articleAdapter
        binding.articleRecyclerView.addItemDecoration(DistanceItemDecorator(15))
        binding.articleRecyclerView.addItemDecoration(CustomItemDecoration())
    }


}
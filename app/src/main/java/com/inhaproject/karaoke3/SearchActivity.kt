package com.inhaproject.karaoke3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.databinding.ActivitySearchBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.home.RankData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity: AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding
    private lateinit var searchAdapter : SearchAdapter

    var data : RankData? = null
    private var result = ArrayList<SearchData>()

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        result.clear()

        val searchView : SearchView = findViewById(R.id.search_song)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                api.searchSong("",query.toString(),"","","","","","").enqueue(object : Callback<
                        ArrayList<SearchData>> {
                    override fun onResponse(
                        call: Call<ArrayList<SearchData>>,
                        response: Response<ArrayList<SearchData>>
                    ) {
                        if (response.body().toString().isNotEmpty()) {
                            response.body().let {
                                result = response.body()!!
                                Toast.makeText(this@SearchActivity,"검색 서버 연결 성공"
                                        +response.code(),Toast.LENGTH_SHORT).show()
                                setSearchAdapter(result)

                            }
                        }

                    }

                    override fun onFailure(call: Call<ArrayList<SearchData>>, t: Throwable) {
                        Toast.makeText(this@SearchActivity,"검색 서버 연결 실패"
                            ,Toast.LENGTH_SHORT).show()
                        Log.d("검색 실패", t.message.toString())
                    }

                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setSearchAdapter(searchDataList: ArrayList<SearchData>){
        searchAdapter = SearchAdapter(searchDataList, this)

        binding.svResult.layoutManager = LinearLayoutManager(this)
        binding.svResult.adapter = searchAdapter
        binding.svResult.addItemDecoration(DistanceItemDecorator(15))
        binding.svResult.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
        searchAdapter.notifyDataSetChanged()
    }
}
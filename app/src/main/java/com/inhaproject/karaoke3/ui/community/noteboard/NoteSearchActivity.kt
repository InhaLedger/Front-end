package com.inhaproject.karaoke3.ui.community.noteboard

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.SearchAdapter
import com.inhaproject.karaoke3.SearchData
import com.inhaproject.karaoke3.databinding.ActivityNoteSearchBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.home.RankData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoteSearchActivity: AppCompatActivity() {
    private lateinit var binding : ActivityNoteSearchBinding
    private lateinit var noteSearchAdapter : NoteSearchAdapter

    var data : RankData? = null
    private var result = ArrayList<SearchData>()

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        result.clear()

        val searchView : SearchView = findViewById(R.id.note_search_song)

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
                                setSearchAdapter(result)

                            }
                        }

                    }

                    override fun onFailure(call: Call<ArrayList<SearchData>>, t: Throwable) {
                        Toast.makeText(this@NoteSearchActivity,"검색 서버 연결 실패"
                            , Toast.LENGTH_SHORT).show()
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
        noteSearchAdapter = NoteSearchAdapter(searchDataList, this)

        binding.noteSvResult.layoutManager = LinearLayoutManager(this)
        binding.noteSvResult.adapter = noteSearchAdapter
        binding.noteSvResult.addItemDecoration(DistanceItemDecorator(15))
        binding.noteSvResult.addItemDecoration(
            DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL)
        )
        noteSearchAdapter.notifyDataSetChanged()
    }
}
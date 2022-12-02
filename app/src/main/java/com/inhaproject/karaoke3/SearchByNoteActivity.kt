package com.inhaproject.karaoke3

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.databinding.ActivitySearchBinding
import com.inhaproject.karaoke3.databinding.ActivitySearchByNoteBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.home.RankData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchByNoteActivity: AppCompatActivity() {
    private lateinit var binding : ActivitySearchByNoteBinding
    private lateinit var searchByNoteAdapter : SearchByNoteAdapter

    private lateinit var searchAdapter : SearchAdapter

    var data : RankData? = null
    private var result = ArrayList<SearchData>()

    private val api = RetroInterface.create()

    var checkedIndex = 0
    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchByNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        result.clear()

        binding.highNoteButton.setOnClickListener {
            flag = 0
            radioMenu()
        }

        binding.lowNoteButton.setOnClickListener {
            flag = 1
            radioMenu()
        }

        binding.searchSubmitButton.setOnClickListener {
            api.searchSong("","","","","","",binding.highNoteButton.text.toString()
                ,binding.lowNoteButton.text.toString()).enqueue(object : Callback<
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
                    Toast.makeText(this@SearchByNoteActivity,"검색 서버 연결 실패"
                        ,Toast.LENGTH_SHORT).show()
                    Log.d("검색 실패", t.message.toString())
                }

            })
        }
    }

    private fun setSearchAdapter(searchDataList: ArrayList<SearchData>){
        searchAdapter = SearchAdapter(searchDataList, this)

        binding.noteSvResult.layoutManager = LinearLayoutManager(this)
        binding.noteSvResult.adapter = searchAdapter
        binding.noteSvResult.addItemDecoration(DistanceItemDecorator(15))
        binding.noteSvResult.addItemDecoration(
            DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL)
        )
        searchAdapter.notifyDataSetChanged()
    }

    private fun radioMenu() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("최고 음")
        val itemList = arrayOf("C0 (-2옥타브 도)","C2 (0옥타브 도)","D2 (0옥타브 레)","E2 (0옥타브 미)","F2 (0옥타브 파)"
            ,"G2 (0옥타브 솔)","A3 (0옥타브 라)","B3 (0옥타브 시)","C3 (1옥타브 도)","D3 (1옥타브 레)","E3 (1옥타브 미)","F3 (1옥타브 파)",
            "G3 (1옥타브 솔)","A4 (1옥타브 라)","B4 (1옥타브 시)","C4 (2옥타브 도)","D4 (2옥타브 레)","E4 (2옥타브 미)","F4 (2옥타브 파)",
            "G4 (2옥타브 솔)","A5 (2옥타브 라)","B5 (2옥타브 시)","C5 (3옥타브 도)","D5 (3옥타브 레)","E5 (3옥타브 미)","F5 (3옥타브 파)",
            "G5 (3옥타브 솔)","A6 (3옥타브 라)","B6 (3옥타브 시)","C6 (4옥타브 도)","B8 (5옥타브 시)")

        builder.setSingleChoiceItems(itemList,checkedIndex) {
                dialog, which -> checkedIndex = which
        }
        builder.setPositiveButton("확인") {dialog, which ->
            if (flag == 0){
                binding.highNoteButton.text = itemList[checkedIndex].substring(0 until 2)
            }
            else {
                binding.lowNoteButton.text = itemList[checkedIndex].substring(0 until 2)
            }
        }
        builder.show()
    }
}
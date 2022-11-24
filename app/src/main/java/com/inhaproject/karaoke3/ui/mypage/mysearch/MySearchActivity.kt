package com.inhaproject.karaoke3.ui.mypage.mysearch

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.SearchData
import com.inhaproject.karaoke3.databinding.ActivityMysearchBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MySearchActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMysearchBinding
    private lateinit var mySearchAdapter : MySearchAdapter

    var data : SearchData? = null
    private var mySearchList = ArrayList<SearchData>()

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMysearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mySearchList.clear()

        api.mySearch().enqueue(object : Callback<ArrayList<SearchData>> {
            override fun onResponse(
                call: Call<ArrayList<SearchData>>,
                response: Response<ArrayList<SearchData>>
            ) {
                if(response.code()==200){
                    mySearchList = response.body()!!
                    setMySearchAdapter(mySearchList)
                }
            }

            override fun onFailure(call: Call<ArrayList<SearchData>>, t: Throwable) {
                Toast.makeText(this@MySearchActivity,"서버 연결 실패"
                    , Toast.LENGTH_SHORT).show()
                Log.d("추천곡 로딩 실패", t.message.toString())
            }

        })
    }

    private fun setMySearchAdapter(mySongDataList : ArrayList<SearchData>) {
        mySearchAdapter = MySearchAdapter(mySongDataList, this)

        binding.mySearchRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mySearchRecyclerView.adapter = mySearchAdapter
        binding.mySearchRecyclerView.addItemDecoration(DistanceItemDecorator(15))
        binding.mySearchRecyclerView.addItemDecoration(
            DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL)
        )
        mySearchAdapter.notifyDataSetChanged()
    }
}
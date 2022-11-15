package com.inhaproject.karaoke3.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.MainActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.SearchActivity
import com.inhaproject.karaoke3.databinding.FragmentRankBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankFragment : Fragment() {

    lateinit var binding: FragmentRankBinding

    private lateinit var rankAdapter : RankAdapter

    var data : RankData? = null
    private var rankList = ArrayList<RankData>()

    private val api = RetroInterface.create()

    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)

        binding = FragmentRankBinding.inflate(inflater, container, false)

        rankList.clear()


        api.rankList().enqueue(object : Callback<ArrayList<RankData>> {
            override fun onResponse(
                call: Call<ArrayList<RankData>>,
                response: Response<ArrayList<RankData>>
            ) {
                if (response.body().toString().isNotEmpty()) {
                    response.body().let {
                        rankList = response.body()!!
                        Toast.makeText(mainActivity,"랭킹 서버 연결 성공 " +response.code()
                            ,Toast.LENGTH_SHORT).show()
                        setRankAdapter(rankList)
                    }
                }
            }
            override fun onFailure(call: Call<ArrayList<RankData>>, t: Throwable) {
                Toast.makeText(mainActivity,"랭킹 서버 연결 실패"
                    ,Toast.LENGTH_SHORT).show()
                Log.d("Rank 실패", t.message.toString())
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = getView()?.findViewById<ImageButton>(R.id.search_button)

            button?.setOnClickListener {
                val intent = Intent(mainActivity, SearchActivity::class.java)
                startActivity(intent)
            }

    }

    private fun setRankAdapter(rankDataList: ArrayList<RankData>){
        rankAdapter = RankAdapter(rankDataList, mainActivity)

        binding.rvRanking.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvRanking.adapter = rankAdapter
        binding.rvRanking.addItemDecoration(DistanceItemDecorator(15))
        binding.rvRanking.addItemDecoration(DividerItemDecoration(mainActivity,LinearLayoutManager.VERTICAL))
        rankAdapter.notifyDataSetChanged()
    }


}

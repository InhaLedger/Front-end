package com.inhaproject.karaoke3.ui.mypage

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.databinding.ActivityMyProposalBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.mypage.admin.ProposalAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProposalActivity: AppCompatActivity() {
    private lateinit var binding : ActivityMyProposalBinding
    private lateinit var proposalAdapter: ProposalAdapter

    private var proposalList = ArrayList<ProposalData>()

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProposalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        proposalList.clear()

        api.myProposal().enqueue(object : Callback<ArrayList<ProposalData>>{
            override fun onResponse(
                call: Call<ArrayList<ProposalData>>,
                response: Response<ArrayList<ProposalData>>
            ) {
                if(response.code() == 200){
                    proposalList = response.body()!!
                    setProposalAdapter(proposalList)
                }
            }

            override fun onFailure(call: Call<ArrayList<ProposalData>>, t: Throwable) {
                Toast.makeText(this@MyProposalActivity,"proposal 서버 연결 실패"
                    , Toast.LENGTH_SHORT).show()
                Log.d("my proposal 로딩 실패", t.message.toString())
            }

        })

    }

    private fun setProposalAdapter(proposalDataList : ArrayList<ProposalData>) {
        proposalAdapter = ProposalAdapter(proposalDataList,this)

        binding.myProposalRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.myProposalRecyclerView.adapter = proposalAdapter
        binding.myProposalRecyclerView.addItemDecoration(DistanceItemDecorator(15))
        binding.myProposalRecyclerView.addItemDecoration(
            DividerItemDecoration(
            this, LinearLayoutManager.VERTICAL)
        )
        proposalAdapter.notifyDataSetChanged()
    }
}
package com.inhaproject.karaoke3.ui.mypage.admin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.databinding.ActivityAdminProposalBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.mypage.ProposalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdminProposalActivity: AppCompatActivity() {
    private lateinit var binding : ActivityAdminProposalBinding
    private lateinit var proposalAdapter: ProposalAdapter

    private var proposalList = ArrayList<ProposalData>()

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProposalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        proposalList.clear()

        api.adminProposal().enqueue(object : Callback<ArrayList<ProposalData>>{
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
                Toast.makeText(this@AdminProposalActivity,"proposal 서버 연결 실패"
                    , Toast.LENGTH_SHORT).show()
                Log.d("admin proposal 로딩 실패", t.message.toString())
            }

        })
    }

    private fun setProposalAdapter(proposalDataList : ArrayList<ProposalData>) {
        proposalAdapter = ProposalAdapter(proposalDataList,this)

        binding.adminProposalRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.adminProposalRecyclerView.adapter = proposalAdapter
        binding.adminProposalRecyclerView.addItemDecoration(DistanceItemDecorator(15))
        binding.adminProposalRecyclerView.addItemDecoration(DividerItemDecoration(
            this,LinearLayoutManager.VERTICAL))

        proposalAdapter.notifyDataSetChanged()
    }

}
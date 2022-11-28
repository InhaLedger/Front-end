package com.inhaproject.karaoke3.ui.mypage.coin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.SearchData
import com.inhaproject.karaoke3.databinding.ActivityDepositBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.mypage.mysearch.MySearchAdapter
import java.util.*
import kotlin.collections.ArrayList

class DepositActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDepositBinding
    private lateinit var depositAdapter : DepositAdapter

    var data : DepositData? = null
    private var depositList = ArrayList<DepositData>()

    val a : DepositData = DepositData("2022-11-28","2022-12-11",
    "1000","게시글 추천")
    val b : DepositData = DepositData("2022-11-28","2022-12-11",
        "500","게시글 투표")

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepositBinding.inflate(layoutInflater)
        setContentView(binding.root)

        depositList.clear()
        depositList.add(0,a)
        depositList.add(1,b)

        setDepositAdapter(depositList)
    }

    private fun setDepositAdapter(depositDataList : ArrayList<DepositData>) {
        depositAdapter = DepositAdapter(depositDataList, this)

        binding.depositRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.depositRecyclerView.adapter = depositAdapter
        binding.depositRecyclerView.addItemDecoration(DistanceItemDecorator(15))
        binding.depositRecyclerView.addItemDecoration(DividerItemDecoration
            (this, LinearLayoutManager.VERTICAL))

        depositAdapter.notifyDataSetChanged()
    }

}
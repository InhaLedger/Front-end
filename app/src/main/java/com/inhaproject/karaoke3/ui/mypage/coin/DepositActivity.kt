package com.inhaproject.karaoke3.ui.mypage.coin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.databinding.ActivityDepositBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class DepositActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDepositBinding
    private lateinit var depositAdapter : DepositAdapter

    var data : BalanceData? = null
    var mapList : Map<Long,DepositData> = mutableMapOf()

    var depositList = ArrayList<DepositData>()

    private val api = RetroInterface.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDepositBinding.inflate(layoutInflater)
        setContentView(binding.root)

        api.myCoin().enqueue(object : Callback<BalanceData>{
            override fun onResponse(call: Call<BalanceData>, response: Response<BalanceData>) {
                if(response.code() == 200){
                    data = response.body()
                    mapList = data!!.stakeList

                    val iterData = mapList.iterator()

                    while (iterData.hasNext()){
                        val result = iterData.next()
                        depositList.add(
                            DepositData(result.value.amount,result.value.completeTimestamp,
                        result.value.simpleClassName,result.value.timestamp,result.value.userId))
                    }
                    setDepositAdapter(depositList)
                }
            }

            override fun onFailure(call: Call<BalanceData>, t: Throwable) {
                Toast.makeText(this@DepositActivity,"deposit 서버 연결 실패"
                    , Toast.LENGTH_SHORT).show()
                Log.d("deposit 로딩 실패", t.message.toString())
            }

        })
    }

    private fun setDepositAdapter(depositDataList: ArrayList<DepositData>) {
        depositAdapter = DepositAdapter(depositDataList, this)

        binding.depositRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.depositRecyclerView.adapter = depositAdapter
        binding.depositRecyclerView.addItemDecoration(DistanceItemDecorator(15))
        binding.depositRecyclerView.addItemDecoration(DividerItemDecoration
            (this, LinearLayoutManager.VERTICAL))

        depositAdapter.notifyDataSetChanged()
    }

}
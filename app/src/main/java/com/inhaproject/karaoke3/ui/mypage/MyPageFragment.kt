package com.inhaproject.karaoke3.ui.mypage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.LoginActivity
import com.inhaproject.karaoke3.MainActivity
import com.inhaproject.karaoke3.databinding.FragmentMypageBinding
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.FragmentCommunityBinding
import com.inhaproject.karaoke3.preference.App
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import com.inhaproject.karaoke3.ui.community.CommunityAdapter
import com.inhaproject.karaoke3.ui.community.CommunityData
import com.inhaproject.karaoke3.ui.mypage.coin.BalanceData
import com.inhaproject.karaoke3.ui.mypage.coin.CoinActivity
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageFragment : Fragment() {

    private lateinit var myPageAdapter: MyPageAdapter
    private val data = mutableListOf<MyPageData>()

    private val api = RetroInterface.create()

    lateinit var binding: FragmentMypageBinding

    lateinit var mainActivity: MainActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMypageBinding.inflate(inflater, container, false)


        initRecycler()

        api.myCoin().enqueue(object : Callback<BalanceData>{
            override fun onResponse(call: Call<BalanceData>, response: Response<BalanceData>) {
                if(response.code() == 200){
                    binding.holdingCoin.text =
                        (response.body()?.availableBalance?.plus(response.body()?.stakedBalance!!)).toString()
                }
            }

            override fun onFailure(call: Call<BalanceData>, t: Throwable) {
                Toast.makeText(
                    mainActivity, "?????? ?????? ??????", Toast.LENGTH_SHORT
                ).show()
                Log.d("?????? ?????? ??????",t.message.toString())
            }

        })

        binding.userNameTextView.text = App.prefs.id



        binding.moveCoinPageButton.setOnClickListener {
            val intent = Intent(mainActivity,CoinActivity::class.java)
            startActivity(intent)
        }

        val context = binding.root.context


        binding.logoutButton.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("????????????")
            dialog.setMessage("???????????? ???????????????????")

            dialog.setPositiveButton("??????"){dialog,_->
                Toast.makeText(mainActivity, "???????????? ???????????????.", Toast.LENGTH_SHORT).show()
                val intent = Intent(mainActivity,LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                mainActivity.finish()
            }
            dialog.show()

        }
        return binding.root
    }

    private fun initRecycler() {
        myPageAdapter = MyPageAdapter(this)
        binding.myPageRecyclerView.adapter = myPageAdapter

        data.apply {
            add(MyPageData("????????? ?????? / ??????", R.drawable.ic_baseline_mic_24))
            add(MyPageData("?????? ?????????",R.drawable.music_note))
            add(MyPageData("?????? ?????? ??????",R.drawable.microphone_icon))
            add(MyPageData("??? ?????? ??????",R.drawable.vote_icon))

            if (App.prefs.id == "admin"){
                add(MyPageData("????????? ??????",R.drawable.admin_icon))
            }

            binding.myPageRecyclerView.addItemDecoration(DistanceItemDecorator(15))
            binding.myPageRecyclerView.addItemDecoration(
                DividerItemDecoration(context,
                    LinearLayoutManager.VERTICAL)
            )
            myPageAdapter.data = data
            myPageAdapter.notifyDataSetChanged()

        }
    }

}
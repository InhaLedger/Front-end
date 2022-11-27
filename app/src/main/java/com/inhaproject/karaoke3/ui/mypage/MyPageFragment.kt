package com.inhaproject.karaoke3.ui.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.MainActivity
import com.inhaproject.karaoke3.databinding.FragmentMypageBinding
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.FragmentCommunityBinding
import com.inhaproject.karaoke3.preference.App
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.ui.community.CommunityAdapter
import com.inhaproject.karaoke3.ui.community.CommunityData
import com.inhaproject.karaoke3.ui.mypage.coin.CoinActivity
import kotlinx.android.synthetic.main.toolbar.*

class MyPageFragment : Fragment() {

    private lateinit var myPageAdapter: MyPageAdapter
    private val data = mutableListOf<MyPageData>()

    lateinit var binding: FragmentMypageBinding

    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        initRecycler()

        binding.userNameTextView.text = App.prefs.id

        binding.moveCoinPageButton.setOnClickListener {
            val intent = Intent(mainActivity,CoinActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun initRecycler() {
        myPageAdapter = MyPageAdapter(this)
        binding.myPageRecyclerView.adapter = myPageAdapter

        data.apply {
            add(MyPageData("목소리 녹음 / 분석", R.drawable.ic_baseline_mic_24))
            add(MyPageData("나의 음역대",R.drawable.music_note))
            add(MyPageData("맞춤 노래 추천",R.drawable.microphone_icon))

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
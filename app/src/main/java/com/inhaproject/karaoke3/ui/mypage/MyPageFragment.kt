package com.inhaproject.karaoke3.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.databinding.FragmentMypageBinding
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.FragmentCommunityBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.ui.community.CommunityAdapter
import com.inhaproject.karaoke3.ui.community.CommunityData

class MyPageFragment : Fragment() {

    private lateinit var myPageAdapter: MyPageAdapter
    private val data = mutableListOf<MyPageData>()

    lateinit var binding: FragmentMypageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        initRecycler()

        return binding.root
    }

    private fun initRecycler() {
        myPageAdapter = MyPageAdapter(this)
        binding.myPageRecyclerView.adapter = myPageAdapter

        data.apply {
            add(MyPageData("목소리 녹음 / 분석", R.drawable.ic_baseline_mic_24))
            add(MyPageData("나의 음역대",R.drawable.music_note))

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
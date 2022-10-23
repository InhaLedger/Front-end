package com.inhaproject.karaoke3.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.FragmentCommunityBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.recycler.RankAdapter
import kotlinx.android.synthetic.main.fragment_community.*

class CommunityFragment: Fragment() {

    lateinit var binding : FragmentCommunityBinding
    private lateinit var communityAdapter: CommunityAdapter
    private val data = mutableListOf<CommunityData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)

        initRecycler()

        return binding.root
    }
    private fun initRecycler() {
        communityAdapter = CommunityAdapter(this)
        binding.communityRecyclerView.adapter = communityAdapter

        data.apply {
            add(CommunityData(img = R.drawable.giftbox, name = "코인노래방 패키지 추천 게시판", sub = "유저들이 직접 추천해주는 코인노래방 패키지 게시판 입니다. 자신만의 패키지를 공유해주세요!"))
            add(CommunityData(img = R.drawable.music_note, name = "음역대 정보 게시판", sub = "데이터에 없는 음역대 정보가 있나요? 음역대 정보를 게시하고 보상을 받아보세요!"))
            add(CommunityData(img = R.drawable.group, name = "자유 게시판", sub = "자유롭게 얘기할 수 있는 게시판입니다."))

            binding.communityRecyclerView.addItemDecoration(DistanceItemDecorator(15))
            binding.communityRecyclerView.addItemDecoration(
                DividerItemDecoration(context,
                    LinearLayoutManager.VERTICAL)
            )
            communityAdapter.datas = data
            communityAdapter.notifyDataSetChanged()

        }
    }

}
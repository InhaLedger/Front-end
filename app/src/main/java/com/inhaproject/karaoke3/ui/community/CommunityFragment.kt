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
            add(CommunityData(img = R.drawable.noteboard_icon, name = "신곡 게시판", sub = "신곡 정보를 공유하는 게시판입니다."))
            add(CommunityData(img = R.drawable.repair, name = "수정 게시판", sub = "기존 곡 정보를 수정하는 게시판입니다. 정확하지 않은 데이터를 알려주세요!"))

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
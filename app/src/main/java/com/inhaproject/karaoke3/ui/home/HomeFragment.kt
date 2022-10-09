package com.inhaproject.karaoke3.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.FragmentHomeBinding
import com.inhaproject.karaoke3.recycler.MusicData
import com.inhaproject.karaoke3.recycler.RankAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private lateinit var rankAdapter : RankAdapter
    private val data = mutableListOf<MusicData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initRecycler()
        return binding.root
        }


    private fun initRecycler() {
        rankAdapter = RankAdapter(context)
        rv_ranking.adapter = rankAdapter

        data.apply {
            add(MusicData(title = "좋은 날", singer = "IU", img = R.drawable.mic_icon))
            add(MusicData(title = "괴물", singer = "YOASOBI", img = R.drawable.mic_icon))
            add(MusicData(title = "antifreeze", singer = "백예린", img = R.drawable.mic_icon))
            add(MusicData(title = "가끔", singer = "CRUSH", img = R.drawable.mic_icon))
            add(MusicData(title = "고백", singer = "정준일", img = R.drawable.mic_icon))
        }
        rankAdapter.data = data
        rankAdapter.notifyDataSetChanged()

    }
    }

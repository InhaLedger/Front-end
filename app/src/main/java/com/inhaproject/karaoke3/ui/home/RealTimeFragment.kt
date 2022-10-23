package com.inhaproject.karaoke3.ui.home

import android.graphics.Rect
import android.graphics.drawable.ClipDrawable.VERTICAL
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.FragmentHomeBinding
import com.inhaproject.karaoke3.databinding.FragmentRealTimeBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.recycler.MusicData
import com.inhaproject.karaoke3.recycler.RankAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class RealTimeFragment : Fragment() {

    lateinit var binding: FragmentRealTimeBinding

    private lateinit var rankAdapter : RankAdapter
    private val data = mutableListOf<MusicData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRealTimeBinding.inflate(inflater, container, false)

        initRecycler()
        return binding.root
    }



    private fun initRecycler() {
        rankAdapter = RankAdapter(context)
        binding.rvRanking.adapter = rankAdapter

        data.apply {
            add(MusicData(num = "1", title = "좋은 날", singer = "IU", img = R.drawable.good_day_resize,false))
            add(MusicData(num = "2", title = "괴물", singer = "YOASOBI", img = R.drawable.yoasobi,false))
            add(
                MusicData(
                    num = "3",
                    title = "antifreeze",
                    singer = "백예린",
                    img = R.drawable.antifreeze,
                    isChecked = false
                )
            )
            add(MusicData(num = "4", title = "가끔", singer = "CRUSH", img = R.drawable.crush,true))
            add(MusicData(num = "5", title = "고백", singer = "정준일", img = R.drawable.joonil,false))
            add(MusicData(num = "6", title = "광화문에서", singer = "규현", img = R.drawable.gwanghwa,false))
            add(MusicData(num = "7", title = "Tomorrow", singer = "릴보이", img = R.drawable.showme9,true))
            add(MusicData(num = "8", title = "좋니", singer = "윤종신", img = R.drawable.like,false))
        }
        binding.rvRanking.addItemDecoration(DistanceItemDecorator(15))
        binding.rvRanking.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        rankAdapter.data = data
        rankAdapter.notifyDataSetChanged()

    }
}

package com.inhaproject.karaoke3.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.inhaproject.karaoke3.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
            binding = FragmentHomeBinding.inflate(inflater, container, false)

            initViewPager()
            return binding.root
    }

    private fun initViewPager() {
        //ViewPager2 Adapter 셋팅
        var viewPager2Adatper = ViewPager2Adapter(this)
        viewPager2Adatper.addFragment(RealTimeFragment())
        viewPager2Adatper.addFragment(NewFragment())
        viewPager2Adatper.addFragment(VotingFragment())

        //Adapter 연결
        binding.mainPager.apply {
            adapter = viewPager2Adatper

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            })
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.mainTabs, binding.mainPager) { tab, position ->
            Log.e("YMC", "ViewPager position: ${position}")
            when (position) {
                0 -> tab.text = "실시간 랭킹"
                1 -> tab.text = "신곡 목록"
                2 -> tab.text = "진행중인 투표"
            }
        }.attach()
    }
}
package com.inhaproject.karaoke3.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.inhaproject.karaoke3.MainActivity
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.SearchActivity
import com.inhaproject.karaoke3.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
            binding = FragmentHomeBinding.inflate(inflater, container, false)

            initViewPager()
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = getView()?.findViewById<ImageButton>(R.id.search_button)

        button?.setOnClickListener {
            val intent = Intent(mainActivity, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initViewPager() {
        //ViewPager2 Adapter 셋팅
        var viewPager2Adatper = ViewPager2Adapter(this)
        viewPager2Adatper.addFragment(RankFragment())
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
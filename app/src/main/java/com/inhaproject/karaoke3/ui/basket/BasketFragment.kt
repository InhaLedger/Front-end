package com.inhaproject.karaoke3.ui.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.databinding.FragmentBasketBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.ui.community.CommunityAdapter
import com.inhaproject.karaoke3.ui.community.CommunityData

class BasketFragment : Fragment() {

    lateinit var binding: FragmentBasketBinding
    private lateinit var basketAdapter: BasketAdapter
    private val data = mutableListOf<BasketData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasketBinding.inflate(inflater, container, false)

        initRecycler()

        return binding.root
    }

    private fun initRecycler() {
        basketAdapter = BasketAdapter(this)
        binding.basketRecyclerView.adapter = basketAdapter

        data.apply {
            add(BasketData("Tomorrow", "릴보이", R.drawable.showme9))
            add(BasketData("좋은 날", "IU", R.drawable.good_day_resize))

            binding.basketRecyclerView.addItemDecoration(DistanceItemDecorator(15))
            binding.basketRecyclerView.addItemDecoration(
                DividerItemDecoration(context,
                    LinearLayoutManager.VERTICAL)
            )
            basketAdapter.datas = data
            basketAdapter.notifyDataSetChanged()

        }
    }

}
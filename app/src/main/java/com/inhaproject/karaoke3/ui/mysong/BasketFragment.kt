package com.inhaproject.karaoke3.ui.mysong

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.inhaproject.karaoke3.MainActivity
import com.inhaproject.karaoke3.SearchData
import com.inhaproject.karaoke3.databinding.FragmentBasketBinding
import com.inhaproject.karaoke3.recycler.DistanceItemDecorator
import com.inhaproject.karaoke3.retrofit.RetroInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasketFragment : Fragment() {

    lateinit var binding: FragmentBasketBinding
    private lateinit var basketAdapter: BasketAdapter

    var data : SearchData? = null
    private var mySongList = ArrayList<BasketData>()

    private val api = RetroInterface.create()

    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasketBinding.inflate(inflater, container, false)

        mySongList.clear()

        api.mySongList().enqueue(object : Callback<ArrayList<BasketData>> {
            override fun onResponse(
                call: Call<ArrayList<BasketData>>,
                response: Response<ArrayList<BasketData>>
            ) {
                if(response.code()==200){
                    mySongList = response.body()!!
                    setMySongAdapter(mySongList)
                }
            }

            override fun onFailure(call: Call<ArrayList<BasketData>>, t: Throwable) {
                Toast.makeText(mainActivity,"즐겨찾기 서버 연결 실패"
                    , Toast.LENGTH_SHORT).show()
                Log.d("즐겨찾기 로딩 실패", t.message.toString())
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setMySongAdapter(mySongDataList : ArrayList<BasketData>) {
        basketAdapter = BasketAdapter(mySongDataList, mainActivity)

        val itemTouchHelperCallback = ItemTouchHelperCallback(basketAdapter)
        val helper = ItemTouchHelper(itemTouchHelperCallback)

        helper.attachToRecyclerView(binding.basketRecyclerView)
        binding.basketRecyclerView.layoutManager = LinearLayoutManager(mainActivity)
        binding.basketRecyclerView.adapter = basketAdapter
        binding.basketRecyclerView.addItemDecoration(DistanceItemDecorator(15))
        binding.basketRecyclerView.addItemDecoration(DividerItemDecoration(mainActivity,LinearLayoutManager.VERTICAL))
        basketAdapter.notifyDataSetChanged()
    }


}
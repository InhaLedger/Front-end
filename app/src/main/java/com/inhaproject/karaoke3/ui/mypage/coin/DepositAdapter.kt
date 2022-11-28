package com.inhaproject.karaoke3.ui.mypage.coin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inhaproject.karaoke3.R
import com.inhaproject.karaoke3.SearchData
import com.inhaproject.karaoke3.databinding.ItemDepositBinding
import com.inhaproject.karaoke3.databinding.ItemMysearchBinding
import com.inhaproject.karaoke3.ui.mypage.mysearch.MySearchAdapter

class DepositAdapter (private val depositList : ArrayList<DepositData>, val context : Context)
    : RecyclerView.Adapter<DepositAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemDepositBinding) : RecyclerView.ViewHolder(binding.root) {

        private val dDate : TextView = itemView.findViewById(R.id.depositDateTextView)
        private val eDate : TextView = itemView.findViewById(R.id.expireDateTextView)
        private val content : TextView = itemView.findViewById(R.id.depositContentTextView)
        private val coin : TextView = itemView.findViewById(R.id.depositCoinTextView)

        private val context = binding.root.context

        fun bind(item: DepositData) {
            dDate.text = item.depositDate
            eDate.text = item.expireDate
            content.text = item.content
            coin.text = item.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDepositBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(depositList[position])
    }

    override fun getItemCount(): Int {
        return depositList.size
    }


}